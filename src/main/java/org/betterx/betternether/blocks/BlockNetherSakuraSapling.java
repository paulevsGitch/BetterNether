package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.FeatureHangingSaplingBlock;
import org.betterx.betternether.interfaces.SurvivesOnNetherrack;
import org.betterx.betternether.registry.features.configured.NetherTrees;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockNetherSakuraSapling extends FeatureHangingSaplingBlock implements BonemealableBlock, SurvivesOnNetherrack {
    public BlockNetherSakuraSapling() {
        super((BlockState state) -> NetherTrees.SAKURA_TREE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }
//
//	@Override
//	protected Feature<?> getFeature() {
//		return FEATURE;
//	}
}
