package org.betterx.betternether.world.features;

import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockStalactite;

import java.util.Optional;

public class ScatterFeatureConfigs {

    public static class WithPlantAge extends ScatterFeatureConfig.OnSolid {
        public static final Codec<WithPlantAge> CODEC = buildCodec(WithPlantAge::new);

        public WithPlantAge(BlockState clusterBlock,
                            BlockState tipBlock,
                            BlockState bottomBlock,
                            Optional<BlockState> baseState,
                            float baseReplaceChance,
                            float chanceOfDirectionalSpread,
                            float chanceOfSpreadRadius2,
                            float chanceOfSpreadRadius3,
                            int minHeight,
                            int maxHeight,
                            float maxSpread,
                            float sizeVariation,
                            float floorChance,
                            boolean growWhileFree,
                            IntProvider spreadCount) {
            super(clusterBlock,
                    tipBlock,
                    bottomBlock,
                    baseState,
                    baseReplaceChance,
                    chanceOfDirectionalSpread,
                    chanceOfSpreadRadius2,
                    chanceOfSpreadRadius3,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance,
                    growWhileFree,
                    spreadCount);
        }


        public static Builder<WithPlantAge> startWithPlantAge() {
            return Builder.start(WithPlantAge::new);
        }


        @Override
        public boolean isValidBase(BlockState state) {
            if (baseState.isPresent() && state.is(baseState.get().getBlock())) return true;
            return BlocksHelper.isNetherGroundMagma(state);
        }

        @Override
        public BlockState createBlock(int height, int maxHeight, RandomSource random) {
            return super
                    .createBlock(height, maxHeight, random)
                    .setValue(BlockStateProperties.AGE_3, random.nextInt(4));
        }
    }

    public static class WithSize extends ScatterFeatureConfig.OnSolid {
        public static final Codec<WithSize> CODEC = buildCodec(WithSize::new);

        public WithSize(BlockState clusterBlock,
                        BlockState tipBlock,
                        BlockState bottomBlock,
                        Optional<BlockState> baseState,
                        float baseReplaceChance,
                        float chanceOfDirectionalSpread,
                        float chanceOfSpreadRadius2,
                        float chanceOfSpreadRadius3,
                        int minHeight,
                        int maxHeight,
                        float maxSpread,
                        float sizeVariation,
                        float floorChance,
                        boolean growWhileFree,
                        IntProvider spreadCount) {
            super(clusterBlock,
                    tipBlock,
                    bottomBlock,
                    baseState,
                    baseReplaceChance,
                    chanceOfDirectionalSpread,
                    chanceOfSpreadRadius2,
                    chanceOfSpreadRadius3,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance,
                    growWhileFree,
                    spreadCount);
        }


        public static Builder<WithSize> startWithSize() {
            return Builder.start(WithSize::new);
        }


        @Override
        public boolean isValidBase(BlockState state) {
            if (baseState.isPresent() && state.is(baseState.get().getBlock())) return true;
            return BlocksHelper.isNetherGroundMagma(state);
        }

        @Override
        public BlockState createBlock(int height, int maxHeight, RandomSource random) {

            return super
                    .createBlock(height, maxHeight, random)
                    .setValue(BlockStalactite.SIZE, Math.max(0, Math.min(7, maxHeight - height)));
        }
    }

    public static class WithSizeOnBase extends WithSize {
        public static final Codec<WithSizeOnBase> CODEC = buildCodec(WithSizeOnBase::new);

        public WithSizeOnBase(BlockState clusterBlock,
                              BlockState tipBlock,
                              BlockState bottomBlock,
                              Optional<BlockState> baseState,
                              float baseReplaceChance,
                              float chanceOfDirectionalSpread,
                              float chanceOfSpreadRadius2,
                              float chanceOfSpreadRadius3,
                              int minHeight,
                              int maxHeight,
                              float maxSpread,
                              float sizeVariation,
                              float floorChance,
                              boolean growWhileFree,
                              IntProvider spreadCount) {
            super(clusterBlock,
                    tipBlock,
                    bottomBlock,
                    baseState,
                    baseReplaceChance,
                    chanceOfDirectionalSpread,
                    chanceOfSpreadRadius2,
                    chanceOfSpreadRadius3,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance,
                    growWhileFree,
                    spreadCount);
        }


        public static Builder<WithSizeOnBase> startWithSizeOnBase() {
            return Builder.start(WithSizeOnBase::new);
        }


        @Override
        public boolean isValidBase(BlockState state) {
            if (baseState.isPresent() && state.is(baseState.get().getBlock())) return true;
            return false;
        }

    }
}
