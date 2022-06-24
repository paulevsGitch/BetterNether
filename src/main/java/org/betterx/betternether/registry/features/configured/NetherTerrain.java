package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v2.levelgen.features.features.SequenceFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.blockpredicates.IsFullShape;
import org.betterx.bclib.api.v2.levelgen.features.config.ConditionFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.placement.IsBasin;
import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.betternether.BN;
import org.betterx.betternether.BetterNether;

import net.minecraft.core.Direction;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.List;

public class NetherTerrain {
    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> MAGMA_BLOBS = BCLFeatureBuilder
            .start(BetterNether.makeID("magma_block"), Blocks.MAGMA_BLOCK)
            .buildAndRegister();
    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> LAVA_PITS = BCLFeatureBuilder
            .start(BN.id("lava_pit"), Blocks.LAVA)
            .buildAndRegister();

    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> BASALT_OR_AIR = BCLFeatureBuilder
            .startWeighted(BN.id("basalt_or_air"))

                    .add(Blocks.BASALT, 15)
                    .add(Blocks.AIR, 15)
            .buildAndRegister();

    public static final BCLFeature MARK = BCLFeatureBuilder
            .start(BN.id("mark"), BCLFeature.MARK_POSTPROCESSING)
            .build()
            .place()
            .is(BlockPredicate.matchesBlocks(Blocks.LAVA))
            .buildAndRegister();

    public static final BCLFeature EXTEND_BASALT = BCLFeatureBuilder
            .start(BN.id("extend_basalt"), Blocks.BASALT)
            .build()
            .place()
            .offset(Direction.DOWN)
            .extendDown(1, 3)
            .buildAndRegister();

    public static final BCLFeature FLOODED_LAVA_PIT_SURFACE = BCLFeatureBuilder
            .start(BN.id("flooded_lava_pit_surface"), BCLFeature.CONDITION)
            .configuration(new ConditionFeatureConfig(
                    IsBasin.simple(
                            BlockPredicate.anyOf(
                                    BlockPredicate.matchesBlocks(Blocks.LAVA),
                                    IsFullShape.HERE
                            )
                    ),
                    LAVA_PITS.place().build(),
                    BASALT_OR_AIR.place().build()
            ))
            .build()
            .place()
            .buildAndRegister();

    public static final BCLConfigureFeature<SequenceFeature, SequenceFeatureConfig> FLOODED_LAVA_PIT = BCLFeatureBuilder
            .start(BN.id("flooded_lava_pit"), BCLFeature.SEQUENCE)
            .configuration(SequenceFeatureConfig.createSequence(List.of(EXTEND_BASALT, FLOODED_LAVA_PIT_SURFACE, MARK)))
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
