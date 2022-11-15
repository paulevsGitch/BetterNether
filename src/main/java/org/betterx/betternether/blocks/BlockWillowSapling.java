package org.betterx.betternether.blocks;

import org.betterx.bclib.blocks.FeatureSaplingBlock;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.interfaces.SurvivesOnNetherGround;
import org.betterx.betternether.registry.features.configured.NetherTrees;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;


public class BlockWillowSapling extends FeatureSaplingBlock implements BonemealableBlock, SurvivesOnNetherGround {
    public BlockWillowSapling() {
//        super((BlockState state) -> MHelper.RANDOM.nextInt(32) == 0
//                ? NetherTrees.OLD_WILLOW_TREE
//                : NetherTrees.WILLOW_TREE);
        super((BlockState state) -> NetherTrees.WILLOW_TREE);
    }

    @Override
    public boolean isBonemealSuccess(
            Level world,
            @NotNull RandomSource random,
            BlockPos pos,
            @NotNull BlockState state
    ) {
        return (BlocksHelper.isFertile(world.getBlockState(pos.below()))
                ? (random.nextInt(8) == 0)
                : (random.nextInt(16) == 0));
    }

    @Override
    protected boolean mayPlaceOn(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos
    ) {
        return isSurvivable(blockState);
    }
}