package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class TerrainFeatures {
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

    public static final SimpleBlockConfiguration LAWA_SWAMP_CONFIG = new SimpleBlockConfiguration(new NoiseProvider(
            2345L,
            new NormalNoise.NoiseParameters(0, 1.0),
            0.021741f,
            List.of(
                    Blocks.LAVA.defaultBlockState(),
                    NetherBlocks.SWAMPLAND_GRASS.defaultBlockState(),
                    Blocks.LAVA.defaultBlockState(),
                    Blocks.SOUL_SOIL.defaultBlockState(),
                    Blocks.LAVA.defaultBlockState(),
                    Blocks.SOUL_SAND.defaultBlockState()
            )
    ));

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


    public static void ensureStaticInitialization() {
    }
}
