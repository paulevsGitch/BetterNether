package com.paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BNTaburet extends BNChair {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 10, 14);

	public BNTaburet(Block block) {
		super(block, 9);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return SHAPE;
	}
}
