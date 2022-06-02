package org.betterx.betternether.world.features;

import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.Codec;
import org.betterx.bclib.world.features.ScatterFeatureConfig;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockStalactite;

import java.util.Optional;

public class ScatterFeatureConfigs {
    public static class WithSize extends ScatterFeatureConfig {
        public static final Codec<WithSize> CODEC = buildCodec(
                WithSize::new);

        public WithSize(BlockState clusterBlock,
                        int minHeight,
                        int maxHeight,
                        float maxSpread,
                        float sizeVariation,
                        float floorChance) {
            this(clusterBlock,
                    Optional.empty(),
                    0,
                    0,
                    0,
                    0,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance);
        }

        public WithSize(BlockState clusterBlock,
                        BlockState baseState,
                        float baseReplaceChance,
                        float chanceOfDirectionalSpread,
                        float chanceOfSpreadRadius2,
                        float chanceOfSpreadRadius3,
                        int minHeight,
                        int maxHeight,
                        float maxSpread,
                        float sizeVariation,
                        float floorChance) {
            this(clusterBlock,
                    Optional.of(baseState),
                    baseReplaceChance,
                    chanceOfDirectionalSpread,
                    chanceOfSpreadRadius2,
                    chanceOfSpreadRadius3,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance);
        }

        protected WithSize(BlockState clusterBlock,
                           Optional<BlockState> baseState,
                           float baseReplaceChance,
                           float chanceOfDirectionalSpread,
                           float chanceOfSpreadRadius2,
                           float chanceOfSpreadRadius3,
                           int minHeight,
                           int maxHeight,
                           float maxSpread,
                           float sizeVariation,
                           float floorChance) {
            super(clusterBlock,
                    baseState,
                    baseReplaceChance,
                    chanceOfDirectionalSpread,
                    chanceOfSpreadRadius2,
                    chanceOfSpreadRadius3,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance);
        }


        @Override
        public boolean isValidBase(BlockState state) {
            if (baseState.isPresent() && state.is(baseState.get().getBlock())) return true;
            return BlocksHelper.isNetherGround(state);
        }

        @Override
        public BlockState createBlock(int height) {
            return this.clusterBlock.setValue(BlockStalactite.SIZE, Math.max(0, Math.min(7, height)));
        }
    }

    public static class WithSizeOnBase extends WithSize {
        public static final Codec<WithSizeOnBase> CODEC = buildCodec(WithSizeOnBase::new);

        public WithSizeOnBase(BlockState clusterBlock,
                              BlockState baseState,
                              int minHeight,
                              int maxHeight,
                              float maxSpread,
                              float sizeVariation,
                              float floorChance) {
            this(clusterBlock,
                    Optional.of(baseState),
                    0,
                    0,
                    0,
                    0,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance);
        }

        protected WithSizeOnBase(BlockState clusterBlock,
                                 Optional<BlockState> baseState,
                                 float baseReplaceChance,
                                 float chanceOfDirectionalSpread,
                                 float chanceOfSpreadRadius2,
                                 float chanceOfSpreadRadius3,
                                 int minHeight,
                                 int maxHeight,
                                 float maxSpread,
                                 float sizeVariation,
                                 float floorChance) {
            super(clusterBlock,
                    baseState,
                    baseReplaceChance,
                    chanceOfDirectionalSpread,
                    chanceOfSpreadRadius2,
                    chanceOfSpreadRadius3,
                    minHeight,
                    maxHeight,
                    maxSpread,
                    sizeVariation,
                    floorChance);
        }

        @Override
        public boolean isValidBase(BlockState state) {
            if (baseState.isPresent() && state.is(baseState.get().getBlock())) return true;
            return false;
        }

    }
}
