package org.betterx.betternether.commands;

import org.betterx.bclib.BCLib;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Optional;

public class PlaceCommand {
    private static final DynamicCommandExceptionType ERROR_STRUCTURE_INVALID = new DynamicCommandExceptionType((object) -> {
        return Component.translatable("commands.locate.structure.invalid", new Object[]{object});
    });

    public static LiteralArgumentBuilder<CommandSourceStack> register(LiteralArgumentBuilder<CommandSourceStack> bnContext) {
        return bnContext.then(Commands
                .literal("place")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(Commands
                        .literal("structure")
                        .then(Commands.argument(
                                              "structure",
                                              ResourceOrTagLocationArgument.resourceOrTag(Registry.STRUCTURE_REGISTRY)
                                      )
                                      .executes(
                                              commandContext -> placeStructure(
                                                      commandContext.getSource(),
                                                      ResourceOrTagLocationArgument.getRegistryType(
                                                              commandContext,
                                                              "structure",
                                                              Registry.STRUCTURE_REGISTRY,
                                                              ERROR_STRUCTURE_INVALID
                                                      )
                                              ))))
        );
    }

    private static final DynamicCommandExceptionType ERROR_NBT_STRUCTURE_NOT_FOUND = new DynamicCommandExceptionType(
            (object) -> {
                return Component.literal("The nbt-structure (" + object + ") was not found.");
            });

    public static int placeStructure(
            CommandSourceStack stack,
            ResourceOrTagLocationArgument.Result<Structure> structureResult
    ) throws CommandSyntaxException {
        Registry<Structure> registry = stack.getLevel()
                                            .registryAccess()
                                            .registryOrThrow(Registry.STRUCTURE_REGISTRY);
        HolderSet<Structure> holderSet = getHolders(structureResult, registry)
                .orElseThrow(() -> ERROR_STRUCTURE_INVALID.create(structureResult.asPrintable()));

        BlockPos blockPos = new BlockPos(stack.getPosition());
        ServerLevel serverLevel = stack.getLevel();
        if (holderSet.size() == 0) throw ERROR_STRUCTURE_INVALID.create(structureResult.asPrintable());

        Structure s = holderSet.get(0).value();
        //serverLevel.getStructureManager().getOrCreate(null);
        try {
            //s.findGenerationPoint()
        } catch (Throwable t) {
            BCLib.LOGGER.error(t.toString());
            //throw ERROR_NBT_STRUCTURE_NOT_FOUND.create(type);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static Optional<? extends HolderSet.ListBacked<Structure>> getHolders(
            ResourceOrTagLocationArgument.Result<Structure> result,
            Registry<Structure> registry
    ) {
        return result.unwrap().map(
                (resourceKey) -> registry.getHolder(resourceKey)
                                         .map((holder) -> HolderSet.direct(new Holder[]{holder})),
                registry::getTag
        );
    }
}
