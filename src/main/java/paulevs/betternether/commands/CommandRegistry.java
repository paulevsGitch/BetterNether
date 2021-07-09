package paulevs.betternether.commands;

import java.util.Collections;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.math.Vector3d;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.registry.BiomesRegistry;

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
                    .then(Commands.literal("tpnext")
                            .requires(source -> source.hasPermission(Commands.LEVEL_OWNERS) )
                            .executes(ctx -> teleportToNextBiome(ctx))
                    )
        );
    }

    private static int biomeIndex = 0;
    private static int teleportToNextBiome(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final CommandSourceStack source = ctx.getSource();
        if (biomeIndex<0 || biomeIndex>=BiomesRegistry.getAllBiomes().size()){
            source.sendFailure(new TextComponent("Failed to find the next biome...").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            return 0;
        }
        final NetherBiome biome = BiomesRegistry.getAllBiomes().get(biomeIndex);
        source.sendSuccess(new TextComponent("Locating Biome " + biome).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN)), false);
        biomeIndex = (biomeIndex+1) % BiomesRegistry.getAllBiomes().size();



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
            do {
                target = new BlockPos(biomePosition.getX(), yPos, biomePosition.getZ());
                state = player.level.getBlockState(target);
                yPos++;
                if (yPos >= player.level.getMaxBuildHeight()-1) {yPos =  player.level.getMinBuildHeight()+1;}
            } while (!state.isAir() && yPos > player.level.getMinBuildHeight() && yPos < player.level.getMaxBuildHeight());
            Vector3d targetPlayerPos = new Vector3d(target.getX() + 0.5,target.getY()-1, target.getZ() + 0.5);

            player.connection.teleport(targetPlayerPos.x, targetPlayerPos.y, targetPlayerPos.z, 0, 0, Collections.EMPTY_SET);
            return LocateCommand.showLocateResult(source, biomeName, currentPosition, biomePosition,
                    "commands.locatebiome.success");
        }
    }
}
