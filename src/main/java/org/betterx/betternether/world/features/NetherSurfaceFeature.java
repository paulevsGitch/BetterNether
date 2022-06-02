package org.betterx.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import org.betterx.bclib.api.features.SurfaceFeature;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public abstract class NetherSurfaceFeature extends SurfaceFeature.DefaultConfiguration {
    @Override
    protected boolean isValidSurface(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    @Override
    protected void generate(BlockPos centerPos, FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        generate(ctx.level(),
                centerPos,
                ctx.random(),
                ctx.chunkGenerator().getGenDepth(),
                NetherChunkPopulatorFeature.generatorForThread().context);
    }

    protected abstract void generate(ServerLevelAccessor world,
                                     BlockPos pos,
                                     RandomSource random,
                                     final int MAX_HEIGHT,
                                     StructureGeneratorThreadContext context);
}
