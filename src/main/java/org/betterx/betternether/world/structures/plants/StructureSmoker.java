package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockProperties;
import org.betterx.betternether.blocks.BlockSmoker;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureSmoker implements IStructure {
    private boolean canPlaceAt(ServerLevelAccessor world, BlockPos pos) {
        return BlocksHelper.isNetherGround(world.getBlockState(pos.below()));
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        final float scale_factor = MAX_HEIGHT / 128.0f;
        final int RANDOM_BOUND = (int) (6 * scale_factor);

        if (canPlaceAt(world, pos)) {
            BlockState top = NetherBlocks.SMOKER.defaultBlockState();
            BlockState middle = NetherBlocks.SMOKER.defaultBlockState()
                                                   .setValue(BlockSmoker.SHAPE, BlockProperties.TripleShape.MIDDLE);
            BlockState bottom = NetherBlocks.SMOKER.defaultBlockState()
                                                   .setValue(BlockSmoker.SHAPE, BlockProperties.TripleShape.BOTTOM);
            for (int i = 0; i < 8; i++) {
                int x = pos.getX() + (int) (random.nextGaussian() * 2);
                int z = pos.getZ() + (int) (random.nextGaussian() * 2);
                int y = pos.getY() + random.nextInt(RANDOM_BOUND);
                for (int j = 0; j < RANDOM_BOUND; j++) {
                    context.POS.set(x, y - j, z);
                    if (world.isEmptyBlock(context.POS) && canPlaceAt(world, context.POS)) {
                        int h = random.nextInt(5);
                        BlocksHelper.setWithoutUpdate(world, context.POS, bottom);
                        for (int n = 1; n < h; n++) {
                            BlockPos up = context.POS.above(n);
                            if (world.isEmptyBlock(up.above()))
                                BlocksHelper.setWithoutUpdate(world, up, middle);
                            else {
                                BlocksHelper.setWithoutUpdate(world, up, top);
                                return;
                            }
                        }
                        BlocksHelper.setWithoutUpdate(world, context.POS.above(h), top);
                        break;
                    }
                }
            }
        }
    }
}
