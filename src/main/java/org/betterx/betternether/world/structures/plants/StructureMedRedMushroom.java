package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockProperties;
import org.betterx.betternether.blocks.BlockRedLargeMushroom;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureMedRedMushroom implements IStructure {
    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        final float scale_factor = MAX_HEIGHT / 128.0f;
        final int RANDOM_BOUND = (int) (6 * scale_factor);

        Block under;
        if (world.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM) {
            for (int i = 0; i < 10; i++) {
                int x = pos.getX() + (int) (random.nextGaussian() * 2);
                int z = pos.getZ() + (int) (random.nextGaussian() * 2);
                if (((x + z) & 1) == 0) {
                    if (random.nextBoolean()) {
                        x += random.nextBoolean() ? 1 : -1;
                    } else {
                        z += random.nextBoolean() ? 1 : -1;
                    }
                }
                int y = pos.getY() + random.nextInt(RANDOM_BOUND);
                for (int j = 0; j < 2 * RANDOM_BOUND; j++) {
                    context.POS.set(x, y - j, z);
                    under = world.getBlockState(context.POS.below()).getBlock();
                    if (under == NetherBlocks.NETHER_MYCELIUM) {
                        grow(world, context.POS, random);
                    }
                }
            }
        }
    }

    public void grow(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        int size = 1 + random.nextInt(4);
        for (int y = 1; y <= size; y++)
            if (!world.isEmptyBlock(pos.above(y))) {
                if (y == 1)
                    return;
                size = y - 1;
                break;
            }
        BlockState middle = NetherBlocks.RED_LARGE_MUSHROOM.defaultBlockState()
                                                           .setValue(BlockRedLargeMushroom.SHAPE,
                                                                     BlockProperties.TripleShape.MIDDLE);
        for (int y = 1; y < size; y++)
            BlocksHelper.setWithoutUpdate(world, pos.above(y), middle);
        BlocksHelper.setWithoutUpdate(world,
                                      pos.above(size),
                                      NetherBlocks.RED_LARGE_MUSHROOM.defaultBlockState()
                                                                     .setValue(BlockRedLargeMushroom.SHAPE,
                                                                               BlockProperties.TripleShape.TOP));
        BlocksHelper.setWithUpdate(world,
                                   pos,
                                   NetherBlocks.RED_LARGE_MUSHROOM.defaultBlockState()
                                                                  .setValue(BlockRedLargeMushroom.SHAPE,
                                                                            BlockProperties.TripleShape.BOTTOM));
    }
}
