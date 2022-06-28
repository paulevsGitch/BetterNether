package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.FeatureHangingSaplingBlock;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;
import org.betterx.betternether.registry.features.configured.NetherTrees;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockAnchorTreeSapling extends FeatureHangingSaplingBlock implements BonemealableBlock, SurvivesOnNetherrack {
    public BlockAnchorTreeSapling() {
        super((BlockState state) -> NetherTrees.ANCHOR_TREE_BRANCH);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }
}
