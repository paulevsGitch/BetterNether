package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.blocks.BlockWhisperingGourdVine;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureWhisperingGourd implements IStructure {
    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        if (pos.getY() < (MAX_HEIGHT - 38) || !BlocksHelper.isNetherrack(world.getBlockState(pos.above()))) return;

        int h = BlocksHelper.downRay(world, pos, 4);
        if (h < 1)
            return;
        h = MHelper.randRange(1, h, random);

        BlockState bottom = NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                              .setValue(BlockWhisperingGourdVine.SHAPE,
                                                                      TripleShape.BOTTOM);
        BlockState middle = NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                              .setValue(BlockWhisperingGourdVine.SHAPE,
                                                                      TripleShape.MIDDLE);
        BlockState top = NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                           .setValue(BlockWhisperingGourdVine.SHAPE,
                                                                   TripleShape.TOP);

        context.POS.set(pos);
        for (int y = 0; y < h - 1; y++) {
            context.POS.setY(pos.getY() - y);
            BlocksHelper.setWithUpdate(world, context.POS, random.nextBoolean() ? top : middle);
        }
        BlocksHelper.setWithUpdate(world, context.POS.below(), bottom);
    }
}