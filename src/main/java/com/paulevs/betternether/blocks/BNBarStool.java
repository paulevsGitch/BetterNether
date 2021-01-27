package com.paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BNBarStool extends BNChair {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);

	public BNBarStool(Block block) {
		super(block, 15);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return SHAPE;
	}
}