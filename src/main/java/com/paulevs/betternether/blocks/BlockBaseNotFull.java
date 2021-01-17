package com.paulevs.betternether.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockBaseNotFull extends BlockBase {

	public BlockBaseNotFull(AbstractBlock.Properties settings) {
		super(settings);
	}

	public boolean canSuffocate(BlockState state, IBlockReader view, BlockPos pos) {
		return false;
	}

	public boolean isSimpleFullBlock(BlockState state, IBlockReader view, BlockPos pos) {
		return false;
	}

	public boolean allowsSpawning(BlockState state, IBlockReader view, BlockPos pos, EntityType<?> type) {
		return false;
	}
}