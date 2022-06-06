package org.betterx.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.blocks.BlockStalagnate;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;
import org.betterx.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureStalagnate implements IStructure {
    public static final int MAX_LENGTH = 25; // 27
    public static final int MIN_LENGTH = 3; // 5

    @Override
    public void generate(ServerLevelAccessor world,
                         BlockPos pos,
                         RandomSource random,
                         final int MAX_HEIGHT,
                         StructureGeneratorThreadContext context) {
        int length = BlocksHelper.upRay(world, pos, MAX_LENGTH);
        Block main = NetherBlocks.MAT_STALAGNATE.getTrunk();
        if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.above(length + 1)))) {
            BlockState bottom = main.defaultBlockState()
                                    .setValue(BlockStalagnate.SHAPE, BlockProperties.TripleShape.BOTTOM);
            BlockState middle = main.defaultBlockState();
            BlockState top = main
                    .defaultBlockState()
                    .setValue(BlockStalagnate.SHAPE, BlockProperties.TripleShape.TOP);

            BlocksHelper.setWithUpdate(world, pos, bottom);
            BlocksHelper.setWithUpdate(world, pos.above(length), top);
            for (int y = 1; y < length; y++)
                BlocksHelper.setWithUpdate(world, pos.above(y), middle);
        }
    }

    public void generateDown(ServerLevelAccessor world, BlockPos pos, RandomSource random) {
        int length = BlocksHelper.downRay(world, pos, MAX_LENGTH);
        Block main = NetherBlocks.MAT_STALAGNATE.getTrunk();
        if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.below(length + 1)))) {
            BlockState bottom = main.defaultBlockState()
                                    .setValue(BlockStalagnate.SHAPE, BlockProperties.TripleShape.BOTTOM);
            BlockState middle = main.defaultBlockState();
            BlockState top = main
                    .defaultBlockState()
                    .setValue(BlockStalagnate.SHAPE, BlockProperties.TripleShape.TOP);

            BlocksHelper.setWithUpdate(world, pos.below(length), bottom);
            BlocksHelper.setWithUpdate(world, pos, top);
            for (int y = 1; y < length; y++)
                BlocksHelper.setWithUpdate(world, pos.below(y), middle);
        }
    }
}
