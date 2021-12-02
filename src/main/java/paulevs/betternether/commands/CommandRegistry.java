package paulevs.betternether.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.math.Vector3d;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.registry.NetherBiomes;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.world.biomes.BCLBiome;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandRegistry {
    private static final DynamicCommandExceptionType ERROR_BIOME_NOT_FOUND = new DynamicCommandExceptionType(
            (object) -> {
                return new TextComponent("The next biome ("+object+") was not found.");
            });
    private static final int MAX_SEARCH_RADIUS = 6400*2;
    private static final int SEARCH_STEP = 8;

    public static void register(){
        CommandRegistrationCallback.EVENT.register(CommandRegistry::register);
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher, boolean dedicated) {
        dispatcher.register(
            Commands.literal("bn")
                    .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS) )
                    .then(Commands.literal("tpnext")
                            .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS) )
                            .executes(ctx -> teleportToNextBiome(ctx))
                    )
                    .then(Commands.literal("placeall")
                            .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS) )
                            .executes(ctx -> placeAllBlocks(ctx))
                    )
                    .then(Commands.literal("placematching")
                            .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS) )
                            .then(Commands.argument("type", StringArgumentType.string())
                                .executes(ctx -> placeMatchingBlocks(ctx, StringArgumentType.getString(ctx, "type"))))
                    )
        );
    }

    private static int biomeIndex = 0;
    private static int teleportToNextBiome(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        List<NetherBiome> biomes = NetherBiomes.ALL_BN_BIOMES;
       
        if (biomeIndex<0 || biomeIndex>=biomes.size()){
            source.sendFailure(new TextComponent("Failed to find the next Biome...").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            return 0;
        }
        final BCLBiome biome = biomes.get(biomeIndex);
        source.sendSuccess(new TextComponent("Locating Biome " + biome).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN)), false);
        biomeIndex = (biomeIndex+1) % biomes.size();

        final BlockPos currentPosition = new BlockPos(source.getPosition());
        final BlockPos biomePosition = source.getLevel().findNearestBiome(biome.getActualBiome(), currentPosition, MAX_SEARCH_RADIUS, SEARCH_STEP);
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
                if (yPos <= player.level.getMinBuildHeight()+1) {
                    if (didWrap) break;
                    yPos = 127;
                    didWrap = true;
                }
            } while (!state.isAir() && yPos > player.level.getMinBuildHeight() && yPos < player.level.getMaxBuildHeight());
            Vector3d targetPlayerPos = new Vector3d(target.getX() + 0.5,target.getY()-1, target.getZ() + 0.5);

            player.connection.teleport(targetPlayerPos.x, targetPlayerPos.y, targetPlayerPos.z, 0, 0, Collections.EMPTY_SET);
            return LocateCommand.showLocateResult(source, biomeName, currentPosition, biomePosition,
                    "commands.locatebiome.success");
        }
    }

    private static int placeMatchingBlocks(CommandContext<CommandSourceStack> ctx, String type) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();

        List<Block> blocks = new LinkedList<>();
        for (Block block :  NetherBlocks.getModBlocks().stream().sorted(CommandRegistry::compareBlockNames).collect(Collectors.toList())) {
            final String name = Registry.BLOCK.getKey(block).getPath();
            if (name.indexOf(type)>=0){
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
    
    private static int placeAllBlocks(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        final ServerPlayer player = source.getPlayerOrException();
        Vec3 pos = source.getPosition();

        List<Block> pickaxes = new LinkedList<>();
        List<Block> axes = new LinkedList<>();
        List<Block> hoes = new LinkedList<>();
        List<Block> shovels = new LinkedList<>();
        List<Block> other = new LinkedList<>();

        for (Block block : NetherBlocks.getModBlocks().stream().sorted(CommandRegistry::compareBlockNames).collect(Collectors.toList())) {
            BlockBehaviour.Properties properties = ((AbstractBlockAccessor) block).getSettings();
            Material material = ((AbstractBlockSettingsAccessor) properties).getMaterial();

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

    private static void placeBlockRow(ServerPlayer player, Vec3 pos, List<Block> blocklist, int offset, boolean square) {
        int i=0;
        int j=0;
        int rowLen = (int)Math.ceil(Math.sqrt(blocklist.size()));
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
            if (i>=rowLen) {
                i=0;
                j++;
            }
        }
    }

    private static void place(ServerPlayer player, Block bl, BlockState state, BlockPos blockPos) {

        if (bl instanceof LadderBlock) {

        }
        BlocksHelper.setWithoutUpdate(player.getLevel(), blockPos, state);
        if (bl instanceof DoorBlock){
            BlocksHelper.setWithoutUpdate(player.getLevel(), blockPos.above(), (BlockState) state.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER));
        }
    }
}
