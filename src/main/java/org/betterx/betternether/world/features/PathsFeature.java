package org.betterx.betternether.world.features;

import org.betterx.bclib.api.v2.levelgen.features.features.DefaultFeature;
import org.betterx.betternether.world.structures.StructurePath;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PathsFeature extends DefaultFeature {
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        final RandomSource random = featurePlaceContext.random();
        final BlockPos worldPos = featurePlaceContext.origin();
        final WorldGenLevel level = featurePlaceContext.level();
        final int sx = (worldPos.getX() >> 4) << 4;
        final int sz = (worldPos.getZ() >> 4) << 4;

        paths.generate(
                level,
                new BlockPos(sx, 0, sz),
                random,
                featurePlaceContext.chunkGenerator().getGenDepth(),
                NetherThreadDataStorage.generatorForThread().context
        );
        return true;
    }

    private static StructurePath paths;

    public static void onLoad(long seed) {
        paths = new StructurePath(seed + 1);
    }
}
