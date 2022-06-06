package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BNBlockProperties;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureVine implements IStructure {
    private final Block block;

    public StructureVine(Block block) {
        this.block = block;
    }

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        int h = BlocksHelper.downRay(world, pos, 25);
        if (h < 2)
            return;
        h = random.nextInt(h) + 1;

        BlockState bottom = block.defaultBlockState().setValue(BNBlockProperties.BOTTOM, true);
        BlockState middle = block.defaultBlockState().setValue(BNBlockProperties.BOTTOM, false);

        context.POS.set(pos);
        for (int y = 0; y < h; y++) {
            context.POS.setY(pos.getY() - y);
            if (world.isEmptyBlock(context.POS.below()))
                BlocksHelper.setWithoutUpdate(world, context.POS, middle);
            else {
                BlocksHelper.setWithoutUpdate(world, context.POS, bottom);
                return;
            }
        }
        BlocksHelper.setWithoutUpdate(world, context.POS.below(), bottom);
    }
}