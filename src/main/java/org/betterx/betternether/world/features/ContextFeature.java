package org.betterx.betternether.world.features;

import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class ContextFeature<FC extends FeatureConfiguration> extends Feature<FC> {
    public ContextFeature(Codec<FC> codec) {
        super(codec);
    }

    @Override
    public final boolean place(FeaturePlaceContext<FC> ctx) {
        return place(
                ctx.level(),
                ctx.origin(),
                ctx.random(),
                ctx.config(),
                ctx.chunkGenerator().getGenDepth(),
                NetherChunkPopulatorFeature.generatorForThread().context
        );
    }

    protected abstract boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            FC config,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    );
}
