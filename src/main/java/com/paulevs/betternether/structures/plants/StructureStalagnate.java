package com.paulevs.betternether.structures.plants;

import java.util.Random;

import com.paulevs.betternether.BlocksHelper;
import com.paulevs.betternether.blocks.BlockStalagnate;
import com.paulevs.betternether.blocks.shapes.TripleShape;
import com.paulevs.betternether.registry.RegistryHandler;
import com.paulevs.betternether.structures.IStructure;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;


public class StructureStalagnate implements IStructure {
	public static final int MAX_LENGTH = 25; // 27
	public static final int MIN_LENGTH = 3; // 5

	@Override
	public void generate(IServerWorld world, BlockPos pos, Random random) {
		int length = BlocksHelper.upRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.up(length + 1)))) {
			BlockState bottom = RegistryHandler.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = RegistryHandler.STALAGNATE.getDefaultState();
			BlockState top = RegistryHandler.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.TOP);

			BlocksHelper.setWithUpdate(world, pos, bottom);
			BlocksHelper.setWithUpdate(world, pos.up(length), top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithUpdate(world, pos.up(y), middle);
		}
	}

	public void generateDown(IServerWorld world, BlockPos pos, Random random) {
		int length = BlocksHelper.downRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.down(length + 1)))) {
			BlockState bottom = RegistryHandler.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = RegistryHandler.STALAGNATE.getDefaultState();
			BlockState top = RegistryHandler.STALAGNATE.getDefaultState().with(BlockStalagnate.SHAPE, TripleShape.TOP);

			BlocksHelper.setWithUpdate(world, pos.down(length), bottom);
			BlocksHelper.setWithUpdate(world, pos, top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithUpdate(world, pos.down(y), middle);
		}
	}
}
