package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockNetherReed;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureReeds implements IStructure {
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        if (world.isEmptyBlock(pos) && NetherBlocks.MAT_REED.getStem()
                                                            .canSurvive(world.getBlockState(pos), world, pos)) {
            BlockState med = NetherBlocks.MAT_REED.getStem().defaultBlockState().setValue(BlockNetherReed.TOP, false);
            int h = random.nextInt(3);
            for (int i = 0; i < h; i++) {
                BlockPos posN = pos.above(i);
                BlockPos up = posN.above();
                if (world.isEmptyBlock(posN)) {
                    if (world.isEmptyBlock(up))
                        BlocksHelper.setWithUpdate(world, posN, med);
                    else {
                        BlocksHelper.setWithUpdate(world, posN, NetherBlocks.MAT_REED.getStem().defaultBlockState());
                        return;
                    }
                }
            }
            BlocksHelper.setWithUpdate(world, pos.above(h), NetherBlocks.MAT_REED.getStem().defaultBlockState());
        }
    }
}