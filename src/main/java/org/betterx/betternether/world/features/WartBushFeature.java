package org.betterx.betternether.world.features;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockWartSeed;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WartBushFeature extends ContextFeature<NoneFeatureConfiguration> {

    private static final Direction[] DIRS = new Direction[]{
            Direction.UP,
            Direction.NORTH,
            Direction.SOUTH,
            Direction.EAST,
            Direction.WEST
    };

    public WartBushFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    protected boolean place(
            ServerLevelAccessor world,
            BlockPos pos,
            RandomSource random,
            NoneFeatureConfiguration config,
            final int MAX_HEIGHT,
            StructureGeneratorThreadContext context
    ) {
        if (world.isEmptyBlock(pos)) {
            BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
            for (Direction dir : DIRS)
                setSeed(world, pos, dir);
            return true;
        }
        return false;
    }

    private void setSeed(ServerLevelAccessor world, BlockPos pos, Direction dir) {
        BlockPos p = pos.relative(dir);
        if (world.isEmptyBlock(p))
            BlocksHelper.setWithoutUpdate(world, p, NetherBlocks.MAT_WART.getSeed().defaultBlockState().setValue(
                    BlockWartSeed.FACING, dir));
    }
}
