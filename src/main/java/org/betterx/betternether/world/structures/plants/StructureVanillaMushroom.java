package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureVanillaMushroom implements IStructure {
    private boolean canPlaceAt(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM;
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        final float scale_factor = MAX_HEIGHT / 128.0f;
        final int RANDOM_BOUND = (int) (8 * scale_factor);

        if (canPlaceAt(world, pos)) {
            BlockState state = random.nextBoolean()
                    ? Blocks.RED_MUSHROOM.defaultBlockState()
                    : Blocks.BROWN_MUSHROOM.defaultBlockState();
            for (int i = 0; i < 16; i++) {
                int x = pos.getX() + (int) (random.nextGaussian() * 4);
                int z = pos.getZ() + (int) (random.nextGaussian() * 4);
                int y = pos.getY() + random.nextInt(RANDOM_BOUND);
                for (int j = 0; j < RANDOM_BOUND; j++) {
                    context.POS.set(x, y - j, z);
                    if (world.isEmptyBlock(context.POS) && canPlaceAt(world, context.POS)) {
                        BlocksHelper.setWithoutUpdate(world, context.POS, state);
                    }
                }
            }
        }
    }
}
