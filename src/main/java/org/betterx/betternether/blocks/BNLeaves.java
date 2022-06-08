package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.BaseLeavesBlock;
import org.betterx.betternether.mixin.common.BlockBehaviourPropertiesAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;

import java.util.function.Consumer;

public class BNLeaves extends BaseLeavesBlock {
    public final static Material NETHER_LEAVES = new FabricMaterialBuilder(MaterialColor.PLANT).lightPassesThrough()
                                                                                               .destroyedByPiston()
                                                                                               .build();

    public BNLeaves(Block sapling, MaterialColor color) {
        super(sapling, color, (settings) -> {
            BlockBehaviourPropertiesAccessor accessor = (BlockBehaviourPropertiesAccessor) settings;
            accessor.setMaterial(NETHER_LEAVES);
        });
    }

    public BNLeaves(Block sapling, MaterialColor color, Consumer<FabricBlockSettings> customizeProperties) {
        super(sapling, color, (settings) -> {
            customizeProperties.accept(settings);
            BlockBehaviourPropertiesAccessor accessor = (BlockBehaviourPropertiesAccessor) settings;
            accessor.setMaterial(NETHER_LEAVES);
        });
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return false;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
    }
}
