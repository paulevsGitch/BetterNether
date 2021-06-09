package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.blocks.BlockLucisSpore;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureLucis implements IStructure {
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (canGenerate(world, pos)) {
			BlockState center = BlocksRegistry.LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CENTER);
			BlockState side = BlocksRegistry.LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.SIDE);
			BlockState corner = BlocksRegistry.LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CORNER);

			if (random.nextInt(3) == 0) {
				if (canReplace(world.getBlockState(pos)))
					BlocksHelper.setWithUpdate(world, pos, center);
				if (canReplace(world.getBlockState(pos.north())))
					BlocksHelper.setWithUpdate(world, pos.north(), side.with(BlockLucisMushroom.FACING, Direction.NORTH));
				if (canReplace(world.getBlockState(pos.south())))
					BlocksHelper.setWithUpdate(world, pos.south(), side.with(BlockLucisMushroom.FACING, Direction.SOUTH));
				if (canReplace(world.getBlockState(pos.east())))
					BlocksHelper.setWithUpdate(world, pos.east(), side.with(BlockLucisMushroom.FACING, Direction.EAST));
				if (canReplace(world.getBlockState(pos.west())))
					BlocksHelper.setWithUpdate(world, pos.west(), side.with(BlockLucisMushroom.FACING, Direction.WEST));

				if (canReplace(world.getBlockState(pos.north().east())))
					BlocksHelper.setWithUpdate(world, pos.north().east(), corner.with(BlockLucisMushroom.FACING, Direction.SOUTH));
				if (canReplace(world.getBlockState(pos.north().west())))
					BlocksHelper.setWithUpdate(world, pos.north().west(), corner.with(BlockLucisMushroom.FACING, Direction.EAST));
				if (canReplace(world.getBlockState(pos.south().east())))
					BlocksHelper.setWithUpdate(world, pos.south().east(), corner.with(BlockLucisMushroom.FACING, Direction.WEST));
				if (canReplace(world.getBlockState(pos.south().west())))
					BlocksHelper.setWithUpdate(world, pos.south().west(), corner.with(BlockLucisMushroom.FACING, Direction.NORTH));
			}
			else {
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() == BlocksRegistry.LUCIS_SPORE) {
					if (state.get(BlockLucisSpore.FACING) == Direction.SOUTH) pos = pos.north();
					else if (state.get(BlockLucisSpore.FACING) == Direction.WEST) pos = pos.east();
				}
				else {
					if (!world.getBlockState(pos.north()).isAir()) {
						pos = pos.north();
					}
					else if (!world.getBlockState(pos.east()).isAir()) {
						pos = pos.east();
					}
				}

				if (canReplace(world.getBlockState(pos))) BlocksHelper.setWithUpdate(world, pos, corner.with(BlockLucisMushroom.FACING, Direction.SOUTH));
				if (canReplace(world.getBlockState(pos.west()))) BlocksHelper.setWithUpdate(world, pos.west(), corner.with(BlockLucisMushroom.FACING, Direction.EAST));
				if (canReplace(world.getBlockState(pos.south()))) BlocksHelper.setWithUpdate(world, pos.south(), corner.with(BlockLucisMushroom.FACING, Direction.WEST));
				if (canReplace(world.getBlockState(pos.south().west()))) BlocksHelper.setWithUpdate(world, pos.south().west(), corner.with(BlockLucisMushroom.FACING, Direction.NORTH));
			}
		}
	}

	private boolean canReplace(BlockState state) {
		return state.getBlock() == BlocksRegistry.LUCIS_SPORE || state.getMaterial().isReplaceable();
	}

	private boolean canGenerate(ServerWorldAccess world, BlockPos pos) {
		BlockState state;
		for (Direction dir : HorizontalFacingBlock.FACING.getValues())
			if (BlocksHelper.isNetherrack(state = world.getBlockState(pos.offset(dir))) || BlocksRegistry.ANCHOR_TREE.isTreeLog(state.getBlock()))
				return true;
		return false;
	}
}
