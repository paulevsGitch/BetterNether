package org.betterx.betternether.world.features;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.datafixers.util.Function11;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockStalactite;

import java.util.Optional;

public abstract class ScatterFeatureConfig implements FeatureConfiguration {
    public final BlockState clusterBlock;
    public final Optional<BlockState> baseState;
    public final float baseReplaceChance;
    public final float chanceOfDirectionalSpread;
    public final float chanceOfSpreadRadius2;
    public final float chanceOfSpreadRadius3;
    public final int minHeight;
    public final int maxHeight;
    public final float maxSpread;
    public final float sizeVariation;
    public final float floorChance;

    public ScatterFeatureConfig(BlockState clusterBlock,
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
        this.clusterBlock = clusterBlock;
        this.baseState = baseState;
        this.baseReplaceChance = baseReplaceChance;
        this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
        this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
        this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.maxSpread = maxSpread;
        this.sizeVariation = sizeVariation;
        this.floorChance = floorChance;
    }


    public boolean isFloor(RandomSource random) {
        return random.nextFloat() < floorChance;
    }

    public abstract boolean isValidBase(BlockState state);

    public abstract BlockState createBlock(int height);

    public static <T extends ScatterFeatureConfig> Codec<T> buildCodec(Function11<BlockState, Optional<BlockState>, Float, Float, Float, Float, Integer, Integer, Float, Float, Float, T> instancer) {
        return RecordCodecBuilder.create((instance) -> instance
                .group(BlockState.CODEC
                                .fieldOf("cluster_block")
                                .forGetter((T cfg) -> cfg.clusterBlock),
                        BlockState.CODEC
                                .optionalFieldOf("base_state")
                                .forGetter((T cfg) -> cfg.baseState),
                        Codec
                                .floatRange(0.0F, 1.0F)
                                .fieldOf("baseReplaceChance")
                                .orElse(1.0F)
                                .forGetter((T cfg) -> cfg.baseReplaceChance),
                        Codec
                                .floatRange(0.0F, 1.0F)
                                .fieldOf("chance_of_directional_spread")
                                .orElse(0.7F)
                                .forGetter((T cfg) -> cfg.chanceOfDirectionalSpread),
                        Codec
                                .floatRange(0.0F, 1.0F)
                                .fieldOf("chance_of_spread_radius2")
                                .orElse(0.5F)
                                .forGetter((T cfg) -> cfg.chanceOfSpreadRadius2),
                        Codec
                                .floatRange(0.0F, 1.0F)
                                .fieldOf("chance_of_spread_radius3")
                                .orElse(0.5F)
                                .forGetter((T cfg) -> cfg.chanceOfSpreadRadius3),
                        Codec
                                .intRange(1, 20)
                                .fieldOf("min_height")
                                .orElse(2)
                                .forGetter((T cfg) -> cfg.minHeight),
                        Codec
                                .intRange(1, 20)
                                .fieldOf("max_height")
                                .orElse(7)
                                .forGetter((T cfg) -> cfg.maxHeight),
                        Codec
                                .floatRange(0, 10)
                                .fieldOf("max_spread")
                                .orElse(2f)
                                .forGetter((T cfg) -> cfg.maxSpread),
                        Codec
                                .floatRange(0, 1)
                                .fieldOf("size_variation")
                                .orElse(0.7f)
                                .forGetter((T cfg) -> cfg.sizeVariation),
                        Codec
                                .floatRange(0, 1)
                                .fieldOf("floor_chance")
                                .orElse(0.5f)
                                .forGetter((T cfg) -> cfg.floorChance)
                )
                .apply(instance, instancer)
        );
    }

    public static class WithSize extends ScatterFeatureConfig {
        public static final Codec<WithSize> CODEC = buildCodec(WithSize::new);

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
