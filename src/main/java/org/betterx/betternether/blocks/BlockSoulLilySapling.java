package org.betterx.betternether.blocks;

import org.betterx.betternether.interfaces.SurvivesOnSoulGroundOrFarmLand;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

public class BlockSoulLilySapling extends BaseBlockCommonSapling implements SurvivesOnSoulGroundOrFarmLand {
    public BlockSoulLilySapling() {
        super(NetherBlocks.SOUL_LILY, MaterialColor.COLOR_ORANGE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnTop(world, pos);
    }
}
