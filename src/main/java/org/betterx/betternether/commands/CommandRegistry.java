package org.betterx.betternether.commands;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3d;
import org.betterx.bclib.BCLib;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiome;
import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.mixin.common.BlockBehaviourAccessor;
import org.betterx.betternether.mixin.common.BlockBehaviourPropertiesAccessor;
import org.betterx.betternether.registry.NetherBiomes;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.features.NetherChunkPopulatorFeature;
import org.betterx.betternether.world.structures.NetherStructureWorld;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class CommandRegistry {
    private static final DynamicCommandExceptionType ERROR_BIOME_NOT_FOUND = new DynamicCommandExceptionType(
            (object) -> {
                return Component.literal("The next biome (" + object + ") was not found.");
            });
    private static final DynamicCommandExceptionType ERROR_NBT_STRUCTURE_NOT_FOUND = new DynamicCommandExceptionType(
            (object) -> {
                return Component.literal("The nbt-structure (" + object + ") was not found.");
            });

    private static final int MAX_SEARCH_RADIUS = 6400 * 2;
    private static final int SAMPLE_RESOLUTION_HORIZONTAL = 32;
    private static final int SAMPLE_RESOLUTION_VERTICAL = 64;

    public static void register() {
        CommandRegistrationCallback.EVENT.register(CommandRegistry::register);
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher,
                                 CommandBuildContext commandBuildContext,
                                 Commands.CommandSelection commandSelection) {
        dispatcher.register(
                Commands.literal("bn")
                        .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                        .then(Commands.literal("test_place")
                                      .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                                      .executes(ctx -> testPlace(ctx))
                        )
                        .then(Commands.literal("find_surface")
                                      .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                                      .executes(ctx -> findSurface(ctx))
                        )
                        .then(Commands.literal("tpnext")
                                      .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                                      .executes(ctx -> teleportToNextBiome(ctx))
                        )
                        .then(Commands.literal("place_all")
                                      .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                                      .executes(ctx -> placeAllBlocks(ctx))
                        )
                        .then(Commands.literal("place_matching")
                                      .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                                      .then(Commands.argument("type", StringArgumentType.string())
                                                    .executes(ctx -> placeMatchingBlocks(ctx,
                                                            StringArgumentType.getString(
                                                                    ctx,
                                                                    "type"))))
                        )
                        .then(Commands.literal("place_nbt")
                                      .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS))
                                      .then(Commands.argument("name", StringArgumentType.string())
                                                    .executes(ctx -> placeNbt(ctx,
                                                            StringArgumentType.getString(ctx,
                                                                    "name"))))
                        )
        );
    }

    private static int biomeIndex = 0;

    private static int teleportToNextBiome(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        List<NetherBiome> biomes = NetherBiomes.ALL_BN_BIOMES;

        if (biomeIndex < 0 || biomeIndex >= biomes.size()) {
            source.sendFailure(Component.literal("Failed to find the next Biome...")
                                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            return 0;
        }
        final BCLBiome biome = biomes.get(biomeIndex);
        source.sendSuccess(Component.literal("Locating Biome " + biome)
                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN)), false);
        biomeIndex = (biomeIndex + 1) % biomes.size();

        final BlockPos currentPosition = new BlockPos(source.getPosition());
        final BlockPos biomePosition = source.getLevel()
                                             .findClosestBiome3d(b -> b.equals(biome.getBiome()),
                                                     currentPosition,
                                                     MAX_SEARCH_RADIUS,
                                                     SAMPLE_RESOLUTION_HORIZONTAL,
                                                     SAMPLE_RESOLUTION_VERTICAL)
                                             .getFirst();
        final String biomeName = biome.toString();

        if (biomePosition == null) {
            throw ERROR_BIOME_NOT_FOUND.create(biomeName);
        } else {
            final ServerPlayer player = source.getPlayerOrException();
            BlockState state;
            BlockPos target;
            double yPos = source.getPosition().y();
            boolean didWrap = false;
            do {
                target = new BlockPos(biomePosition.getX(), yPos, biomePosition.getZ());
                state = player.level.getBlockState(target);
                yPos--;
                if (yPos <= player.level.getMinBuildHeight() + 1) {
                    if (didWrap) break;
                    yPos = 127;
                    didWrap = true;
                }
            } while (!state.isAir() && yPos > player.level.getMinBuildHeight() && yPos < player.level.getMaxBuildHeight());
            Vector3d targetPlayerPos = new Vector3d(target.getX() + 0.5, target.getY() - 1, target.getZ() + 0.5);

            player.connection.teleport(targetPlayerPos.x,
                    targetPlayerPos.y,
                    targetPlayerPos.z,
                    0,
                    0,
                    Collections.EMPTY_SET);
            ResourceOrTagLocationArgument.Result result = new ResourceOrTagLocationArgument.Result() {
                @Override
                public Either<ResourceKey, TagKey> unwrap() {
                    return Either.left(BiomeAPI.getBiomeKey(biome.getBiome()));
                }

                @Override
                public Optional<ResourceOrTagLocationArgument.Result> cast(ResourceKey resourceKey) {
                    return Optional.empty();
                }

                @Override
                public String asPrintable() {
                    return biomeName;
                }

                @Override
                public boolean test(Object o) {
                    return false;
                }
            };
            ResourceKey<Biome> a = BiomeAPI.getBiomeKey(biome.getBiome());
            Holder<Biome> h = BuiltinRegistries.BIOME.getHolder(a).orElseThrow();
            return LocateCommand.showLocateResult(source,
                    result,
                    currentPosition,
                    new Pair<>(biomePosition, h),
                    "commands.locatebiome.success",
                    false);
        }
    }

    private static int placeMatchingBlocks(CommandContext<CommandSourceStack> ctx,
                                           String type) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();

        List<Block> blocks = new LinkedList<>();
        for (Block block : NetherBlocks.getModBlocks()
                                       .stream()
                                       .sorted(CommandRegistry::compareBlockNames)
                                       .collect(Collectors.toList())) {
            final String name = Registry.BLOCK.getKey(block).getPath();
            if (name.indexOf(type) >= 0) {
                blocks.add(block);
            }
        }

        placeBlockRow(player, pos, blocks, 1, true);
        return Command.SINGLE_SUCCESS;
    }

    @NotNull
    private static int compareBlockNames(Block a, Block b) {

        final String as = Registry.BLOCK.getKey(a)
                                        .getPath();
        final String bs = Registry.BLOCK.getKey(b)
                                        .getPath();
        return as.compareTo(bs);
    }

    private static int placeNbt(CommandContext<CommandSourceStack> ctx, String type) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();
        final ServerLevel level = source.getLevel();
        try {
            NetherStructureWorld structure = new NetherStructureWorld(type, 0, StructurePlacementType.FLOOR);
            if (structure == null) {
                throw ERROR_NBT_STRUCTURE_NOT_FOUND.create(type);
            }
            structure.generate(level,
                    new BlockPos(pos),
                    MHelper.RANDOM,
                    128,
                    NetherChunkPopulatorFeature.generatorForThread().context);
        } catch (Throwable t) {
            BCLib.LOGGER.error("Error loading from nbt: " + type);
            BCLib.LOGGER.error(t.toString());
            throw ERROR_NBT_STRUCTURE_NOT_FOUND.create(type);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int placeMapIdx = 0;
    private static final BlockState[] states = {
            Blocks.RED_STAINED_GLASS.defaultBlockState(),
            Blocks.BLUE_STAINED_GLASS.defaultBlockState(),
            Blocks.YELLOW_STAINED_GLASS.defaultBlockState(),
            Blocks.LIME_STAINED_GLASS.defaultBlockState(),
            Blocks.PINK_STAINED_GLASS.defaultBlockState(),
            Blocks.GREEN_STAINED_GLASS.defaultBlockState(),
            Blocks.WHITE_STAINED_GLASS.defaultBlockState(),
            Blocks.BLACK_STAINED_GLASS.defaultBlockState(),
            Blocks.ORANGE_STAINED_GLASS.defaultBlockState(),
            Blocks.LIGHT_BLUE_STAINED_GLASS.defaultBlockState()
    };
    private static final BlockState[] states2 = {
            Blocks.RED_CONCRETE.defaultBlockState(),
            Blocks.BLUE_CONCRETE.defaultBlockState(),
            Blocks.YELLOW_CONCRETE.defaultBlockState(),
            Blocks.LIME_CONCRETE.defaultBlockState(),
            Blocks.PINK_CONCRETE.defaultBlockState(),
            Blocks.GREEN_CONCRETE.defaultBlockState(),
            Blocks.WHITE_CONCRETE.defaultBlockState(),
            Blocks.BLACK_CONCRETE.defaultBlockState(),
            Blocks.ORANGE_CONCRETE.defaultBlockState(),
            Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState()
    };

    private static int findSurface(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();
        final ServerLevel level = source.getLevel();
        MutableBlockPos mPos = new BlockPos(pos).mutable();
        System.out.println("Staring at: " + mPos + " -> " + level.getBlockState(mPos));
        boolean found = org.betterx.bclib.util.BlocksHelper.findSurroundingSurface(level,
                mPos,
                Direction.DOWN,
                12,
                state -> BlocksHelper.isNetherGroundMagma(
                        state));
        System.out.println("Ending at: " + mPos + " -> " + level.getBlockState(mPos) + " = " + found);
        org.betterx.bclib.util.BlocksHelper.setWithoutUpdate(level, new BlockPos(pos), Blocks.YELLOW_CONCRETE);
        org.betterx.bclib.util.BlocksHelper.setWithoutUpdate(level, mPos, Blocks.LIGHT_BLUE_CONCRETE);
        return Command.SINGLE_SUCCESS;
    }

    private static int testPlace(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {

        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();
        final ServerLevel level = source.getLevel();
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int x = -16; x <= 16; x++) {
            for (int y = -16; y <= 16; y++) {
                double v = Biome.BIOME_INFO_NOISE.getValue((x + pos.x) / 200.0,
                        (y + pos.z) / 200.0,
                        false);
                if (v < min) min = v;
                if (v > max) max = v;
            }
        }
        System.out.println("Noise: " + min + " - " + max);


        BCLFeature<?, ?> feature = BiomeFeatures.OLD_SWAMPLAND_SCULK;
        PlacedFeature pFeature = level
                .registryAccess()
                .registryOrThrow(Registry.PLACED_FEATURE_REGISTRY)
                .getHolder(feature.getPlacedFeature().unwrapKey().get())
                .get()
                .value();
        var placements = pFeature.placement();
        PlacementContext pctx = new PlacementContext(level,
                level.getChunkSource().getGenerator(),
                Optional.of(pFeature));
        Stream<BlockPos> s = Stream.of(new BlockPos(pos));
        RandomSource rnd = new LegacyRandomSource(121212);
        placeMapIdx = 0;
        List<Pair<BlockPos, BlockState>> posStates = new LinkedList<>();
        for (PlacementModifier p : placements) {
            s = s.flatMap(bp -> p.getPositions(pctx, rnd, bp));
            var list = s.toList();
            placeMapIdx = (placeMapIdx + 1) % states.length;
            BlockState state1 = states[placeMapIdx];
            System.out.println(p.getClass().getSimpleName() + " -> " + list.size() + ", " + state1);

            list.forEach(bp -> {
                BlockState state = states[placeMapIdx];
                if (org.betterx.bclib.util.BlocksHelper.isTerrain(level.getBlockState(bp)))
                    state = states2[placeMapIdx];
                posStates.add(new Pair<>(bp, state));
                //
                //BlocksHelper.setWithoutUpdate(level, bp, state);
            });
            s = list.stream();
        }

        posStates.forEach(p -> {
            //System.out.println("    " + p.getFirst() + " -> " + level.getBlockState(p.getFirst()));
            BlocksHelper.setWithoutUpdate(level, p.getFirst(), p.getSecond());

        });
        return Command.SINGLE_SUCCESS;
    }

    private static int placeAllBlocks(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();

        List<Block> pickaxes = new LinkedList<>();
        List<Block> axes = new LinkedList<>();
        List<Block> hoes = new LinkedList<>();
        List<Block> shovels = new LinkedList<>();
        List<Block> other = new LinkedList<>();

        for (Block block : NetherBlocks.getModBlocks()
                                       .stream()
                                       .sorted(CommandRegistry::compareBlockNames)
                                       .collect(Collectors.toList())) {
            BlockBehaviour.Properties properties = ((BlockBehaviourAccessor) block).getProperties();
            Material material = ((BlockBehaviourPropertiesAccessor) properties).getMaterial();

            if (material.equals(Material.STONE) || material.equals(Material.METAL)) {
                pickaxes.add(block);
            } else if (material.equals(Material.WOOD) || material.equals(Material.NETHER_WOOD)) {
                axes.add(block);
            } else if (material.equals(Material.LEAVES) || material.equals(Material.PLANT) || material.equals(Material.WATER_PLANT)) {
                hoes.add(block);
            } else if (material.equals(Material.SAND)) {
                shovels.add(block);
            } else {
                other.add(block);
            }
        }

        placeBlockRow(player, pos, pickaxes, 1, false);
        placeBlockRow(player, pos, axes, 2, false);
        placeBlockRow(player, pos, hoes, 3, false);
        placeBlockRow(player, pos, shovels, 4, false);
        placeBlockRow(player, pos, other, 5, false);

        return Command.SINGLE_SUCCESS;
    }

    private static void placeBlockRow(ServerPlayer player,
                                      Vec3 pos,
                                      List<Block> blocklist,
                                      int offset,
                                      boolean square) {
        int i = 0;
        int j = 0;
        int rowLen = (int) Math.ceil(Math.sqrt(blocklist.size()));
        blocklist.sort((a, b) -> {
            if ((a instanceof DoorBlock) && !(b instanceof DoorBlock))
                return 1;
            if (!(a instanceof DoorBlock) && (b instanceof DoorBlock))
                return -1;

            return 0;
        });
        for (Block bl : blocklist) {
            BlockState state = bl.defaultBlockState();
            BlockPos blockPos = new BlockPos((int) pos.x + i, (int) pos.y + j, (int) pos.z + offset);
            place(player, bl, state, blockPos);
            i++;
            if (i >= rowLen) {
                i = 0;
                j++;
            }
        }
    }

    private static void place(ServerPlayer player, Block bl, BlockState state, BlockPos blockPos) {

        if (bl instanceof LadderBlock) {

        }
        BlocksHelper.setWithoutUpdate(player.getLevel(), blockPos, state);
        if (bl instanceof DoorBlock) {
            BlocksHelper.setWithoutUpdate(player.getLevel(),
                    blockPos.above(),
                    state.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER));
        }
    }
}
