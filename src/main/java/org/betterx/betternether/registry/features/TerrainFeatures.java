package org.betterx.betternether.registry.features;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v2.levelgen.features.FastFeatures;
import org.betterx.bclib.api.v2.levelgen.features.blockpredicates.IsFullShape;
import org.betterx.bclib.api.v2.levelgen.features.config.ConditionFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.placement.IsBasin;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;

import java.util.List;

public class TerrainFeatures {
    public static final BCLFeature MAGMA_BLOBS = BCLFeatureBuilder
            .start(BetterNether.makeID("magma_blobs"), Feature.SIMPLE_BLOCK)
            .decoration(GenerationStep.Decoration.LAKES)
            .countRange(1, 2)
            .spreadHorizontal(ClampedNormalInt.of(0, 2, -4, -4))
            .stencil()
            .onlyInBiome()
            .onEveryLayer()
            .offset(Direction.DOWN)
            .is(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .onlyInBiome()
            .extendDown(0, 3)
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MAGMA_BLOCK)));
    public static final BCLFeature LAVA_PITS = BCLFeatureBuilder
            .start(BetterNether.makeID("lava_pits"), Feature.SIMPLE_BLOCK)
            .decoration(GenerationStep.Decoration.LAKES)
            .onEveryLayer()
            .stencil()
            .onceEvery(4)
            .findSolidFloor(3)
            .offset(Direction.DOWN)
            .onlyInBiome()
            .inBasinOf(BlockPredicate.anyOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.LAVA)
            ))
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LAVA)));
    public static final BCLFeature LAVA_PITS_SPARE = BCLFeatureBuilder
            .start(BetterNether.makeID("lava_pits_spare"), Feature.SIMPLE_BLOCK)
            .decoration(GenerationStep.Decoration.LAKES)
            .onEveryLayer()
            .stencil()
            .onceEvery(6)
            .findSolidFloor(3)
            .offset(Direction.DOWN)
            .onlyInBiome()
            .inBasinOf(BlockPredicate.anyOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.LAVA)
            ))
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LAVA)));
    public static final SimpleBlockConfiguration LAWA_SWAMP_CONFIG = new SimpleBlockConfiguration(new NoiseProvider(
            2345L,
            new NormalNoise.NoiseParameters(0, 1.0, new double[0]),
            0.021741f,
            List.of(
                    Blocks.LAVA.defaultBlockState(),
                    NetherBlocks.SWAMPLAND_GRASS.defaultBlockState(),
                    Blocks.LAVA.defaultBlockState(),
                    Blocks.SOUL_SOIL.defaultBlockState(),
                    Blocks.LAVA.defaultBlockState(),
                    Blocks.SOUL_SAND.defaultBlockState()
            )));

    public static final BCLFeature LAVA_SWAMP = BCLFeatureBuilder
            .start(BetterNether.makeID("lava_swamp"), Feature.SIMPLE_BLOCK)
            .decoration(GenerationStep.Decoration.LAKES)
            .all()
            .onEveryLayer()
            .onlyInBiome()
            .offset(Direction.DOWN)
            .inBasinOf(BlockPredicate.anyOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.LAVA)
            ))
            .offset(new Vec3i(0, -2, 0))
            .inBasinOf(BlockPredicate.anyOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.LAVA)
            ))
            .offset(new Vec3i(0, 2, 0))
            .extendDown(1, 2)
            .buildAndRegister(LAWA_SWAMP_CONFIG);

    public static final BCLFeature LAVA_TERRACE = BCLFeatureBuilder
            .start(BetterNether.makeID("lava_terrace"), Blocks.LAVA)
            .decoration(GenerationStep.Decoration.LAKES)
            .all()
            .onEveryLayer()
            .onlyInBiome()
            .offset(Direction.DOWN)
            .inBasinOf(BlockPredicate.anyOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.LAVA)
            ))
            .buildAndRegister();

    public static final BCLFeature NO_SURFACE_SANDSTONE = BCLFeatureBuilder
            .start(BetterNether.makeID("no_surface_sandstone"), Blocks.SOUL_SAND)
            .decoration(GenerationStep.Decoration.LOCAL_MODIFICATIONS)
            .all()
            .onEveryLayer()
            .onlyInBiome()
            .offset(Direction.DOWN)
            .is(BlockPredicate.matchesBlocks(NetherBlocks.SOUL_SANDSTONE))
            .buildAndRegister();

    public static final BCLFeature MARK = BCLFeatureBuilder
            .start(BetterNether.makeID("mark"), BCLFeature.MARK_POSTPROCESSING)
            .is(BlockPredicate.matchesBlocks(Blocks.LAVA))
            .buildAndRegister();

    public static final BCLFeature LAVA = FastFeatures.single(BetterNether.makeID("lava"), Blocks.LAVA);
    public static final BCLFeature BASALT_OR_AIR = BCLFeatureBuilder
            .start(BetterNether.makeID("basalt_or_air"), new WeightedStateProvider(SimpleWeightedRandomList
                    .<BlockState>builder()
                    .add(Blocks.BASALT.defaultBlockState(), 15)
                    .add(Blocks.AIR.defaultBlockState(), 15)
            )).buildAndRegister();

    public static final BCLFeature EXTEND_BASALT = BCLFeatureBuilder
            .start(BetterNether.makeID("extend_basalt"), Blocks.BASALT)
            .offset(Direction.DOWN)
            .extendDown(1, 3)
            .buildAndRegister();

    public static final BCLFeature FLOODED_LAVA_PIT_SURFACE = BCLFeatureBuilder
            .start(BetterNether.makeID("flooded_lava_pit_surface"), BCLFeature.CONDITION)
            .buildAndRegister(new ConditionFeatureConfig(
                            IsBasin.simple(
                                    BlockPredicate.anyOf(
                                            BlockPredicate.matchesBlocks(Blocks.LAVA),
                                            IsFullShape.HERE
                                    )
                            ),
                            LAVA,
                            BASALT_OR_AIR
                    )
            );


    public static final BCLFeature FLOODED_LAVA_PIT = BCLFeatureBuilder
            .start(BetterNether.makeID("flooded_lava_pit"), BCLFeature.SEQUENCE)
            .decoration(GenerationStep.Decoration.LAKES)
            .all()
            .onEveryLayer()
            .offset(Direction.DOWN)
            .onlyInBiome()
            .buildAndRegister(
                    SequenceFeatureConfig.create(List.of(EXTEND_BASALT, FLOODED_LAVA_PIT_SURFACE, MARK))
            );

    public static void ensureStaticInitialization() {
    }
}
