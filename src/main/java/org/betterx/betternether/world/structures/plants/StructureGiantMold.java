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
import org.betterx.betternether.world.structures.IGrowableStructure;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureGiantMold implements IStructure, IGrowableStructure {


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
                int y = pos.getY() + random.nextInt(RANDOM_BOUND);
                for (int j = 0; j < 16; j++) {
                    context.POS.set(x, y - j, z);
                    under = world.getBlockState(context.POS.below()).getBlock();
                    if (under == NetherBlocks.NETHER_MYCELIUM) {
                        grow(world, context.POS, random);
                    }
                }
            }
        }
    }

    @Override
    public void grow(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        int size = 2 + random.nextInt(6);
        for (int y = 1; y <= size; y++)
            if (!world.isEmptyBlock(pos.above(y))) {
                if (y == 1)
                    return;
                size = y - 1;
                break;
            }
        BlockState middle = NetherBlocks.GIANT_MOLD.defaultBlockState()
                                                   .setValue(BlockRedLargeMushroom.SHAPE,
                                                             BlockProperties.TripleShape.MIDDLE);
        for (int y = 1; y < size; y++)
            BlocksHelper.setWithoutUpdate(world, pos.above(y), middle);
        BlocksHelper.setWithoutUpdate(world,
                                      pos.above(size),
                                      NetherBlocks.GIANT_MOLD.defaultBlockState()
                                                             .setValue(BlockRedLargeMushroom.SHAPE,
                                                                       BlockProperties.TripleShape.TOP));
        BlocksHelper.setWithUpdate(world,
                                   pos,
                                   NetherBlocks.GIANT_MOLD.defaultBlockState()
                                                          .setValue(BlockRedLargeMushroom.SHAPE,
                                                                    BlockProperties.TripleShape.BOTTOM));
    }
}
