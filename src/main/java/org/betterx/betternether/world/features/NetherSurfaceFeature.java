package org.betterx.betternether.world.features;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Optional;

public abstract class NetherSurfaceFeature extends Feature<NoneFeatureConfiguration> {
    public NetherSurfaceFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    protected boolean isValidSurface(BlockState state) {
        return BlocksHelper.isNetherGround(state);
    }

    protected void generate(BlockPos centerPos, FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        generate(
                ctx.level(),
                centerPos,
                ctx.random(),
                ctx.chunkGenerator().getGenDepth(),
                NetherChunkPopulatorFeature.generatorForThread().context
        );
    }

    protected abstract void generate(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    );

    protected int minHeight(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        return ctx.chunkGenerator().getSeaLevel();
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        Optional<BlockPos> pos = org.betterx.bclib.util.BlocksHelper.findSurfaceBelow(
                ctx.level(),
                ctx.origin(),
                minHeight(ctx),
                this::isValidSurface
        );
        if (pos.isPresent()) {
            generate(pos.get(), ctx);
            return true;
        }


        return false;
    }
}
