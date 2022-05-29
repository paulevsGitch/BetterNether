package org.betterx.betternether.world.features;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record ScatterFeatureConfig(BlockState clusterBlock, Optional<BlockState> baseState,
                                   float chanceOfDirectionalSpread,
                                   float chanceOfSpreadRadius2,
                                   float chanceOfSpreadRadius3,
                                   int minHeight,
                                   int maxHeight,
                                   float floorChance) implements FeatureConfiguration {

    public boolean isFloor(RandomSource random) {
        return random.nextFloat() < floorChance;
    }

    public static final Codec<ScatterFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance
            .group(
                    BlockState.CODEC.fieldOf("cluster_block").forGetter(cfg -> cfg.clusterBlock),
                    BlockState.CODEC
                            .optionalFieldOf("base_state")
                            .forGetter(cfg -> cfg.baseState),
                    Codec
                            .floatRange(0.0F, 1.0F)
                            .fieldOf("chance_of_directional_spread")
                            .orElse(0.7F)
                            .forGetter((cfg) -> cfg.chanceOfDirectionalSpread),
                    Codec
                            .floatRange(0.0F, 1.0F)
                            .fieldOf("chance_of_spread_radius2")
                            .orElse(0.5F)
                            .forGetter((cfg) -> cfg.chanceOfSpreadRadius2),
                    Codec
                            .floatRange(0.0F, 1.0F)
                            .fieldOf("chance_of_spread_radius3")
                            .orElse(0.5F)
                            .forGetter((cfg) -> cfg.chanceOfSpreadRadius3),
                    Codec
                            .intRange(1, 20)
                            .fieldOf("min_height")
                            .orElse(2)
                            .forGetter((cfg) -> cfg.minHeight),
                    Codec
                            .intRange(1, 20)
                            .fieldOf("max_height")
                            .orElse(7)
                            .forGetter((cfg) -> cfg.maxHeight),
                    Codec
                            .floatRange(0, 1)
                            .fieldOf("floor_chance")
                            .orElse(0.5f)
                            .forGetter((cfg) -> cfg.floorChance)
            )
            .apply(instance, ScatterFeatureConfig::new));
}
