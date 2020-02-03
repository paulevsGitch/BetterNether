package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureLucis extends Object implements IStructure
{
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		if (canGenerate(world, pos))
		{
			BlockState center = BlocksRegister.LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CENTER);
			BlockState side = BlocksRegister.LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.SIDE);
			BlockState corner = BlocksRegister.LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CORNER);

			if (random.nextInt(3) == 0)
			{
				if (canReplace(world.getBlockState(pos)))
					BlocksHelper.setWithoutUpdate(world, pos, center);
				if (canReplace(world.getBlockState(pos.north())))
					BlocksHelper.setWithoutUpdate(world, pos.north(), side.with(BlockLucisMushroom.FACING, Direction.NORTH));
				if (canReplace(world.getBlockState(pos.south())))
					BlocksHelper.setWithoutUpdate(world, pos.south(), side.with(BlockLucisMushroom.FACING, Direction.SOUTH));
				if (canReplace(world.getBlockState(pos.east())))
					BlocksHelper.setWithoutUpdate(world, pos.east(), side.with(BlockLucisMushroom.FACING, Direction.EAST));
				if (canReplace(world.getBlockState(pos.west())))
					BlocksHelper.setWithoutUpdate(world, pos.west(), side.with(BlockLucisMushroom.FACING, Direction.WEST));

				if (canReplace(world.getBlockState(pos.north().east())))
					BlocksHelper.setWithoutUpdate(world, pos.north().east(), corner.with(BlockLucisMushroom.FACING, Direction.SOUTH));
				if (canReplace(world.getBlockState(pos.north().west())))
					BlocksHelper.setWithoutUpdate(world, pos.north().west(), corner.with(BlockLucisMushroom.FACING, Direction.EAST));
				if (canReplace(world.getBlockState(pos.south().east())))
					BlocksHelper.setWithoutUpdate(world, pos.south().east(), corner.with(BlockLucisMushroom.FACING, Direction.WEST));
				if (canReplace(world.getBlockState(pos.south().west())))
					BlocksHelper.setWithoutUpdate(world, pos.south().west(), corner.with(BlockLucisMushroom.FACING, Direction.NORTH));
			}
			else
			{
				boolean offset = false;
				if (BlocksHelper.isNetherrack(world.getBlockState(pos.north())))
				{
					pos = pos.north();
					offset = true;
				}
				else if (BlocksHelper.isNetherrack(world.getBlockState(pos.east())))
				{
					pos = pos.east();
					offset = true;
				}
				if (!offset)
					BlocksHelper.setWithoutUpdate(world, pos, corner.with(BlockLucisMushroom.FACING, Direction.SOUTH));
				if (canReplace(world.getBlockState(pos.west())))
					BlocksHelper.setWithoutUpdate(world, pos.west(), corner.with(BlockLucisMushroom.FACING, Direction.EAST));
				if (canReplace(world.getBlockState(pos.south())))
					BlocksHelper.setWithoutUpdate(world, pos.south(), corner.with(BlockLucisMushroom.FACING, Direction.WEST));
				if (canReplace(world.getBlockState(pos.south().west())))
					BlocksHelper.setWithoutUpdate(world, pos.south().west(), corner.with(BlockLucisMushroom.FACING, Direction.NORTH));
			}
		}
	}

	private boolean canReplace(BlockState state)
	{
		Block b = state.getBlock();
		return b == Blocks.AIR || b == BlocksRegister.LUCIS_SPORE;
	}

	private boolean canGenerate(IWorld world, BlockPos pos)
	{
		for (Direction dir: HorizontalFacingBlock.FACING.getValues())
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.offset(dir))))
				return true;
		return false;
	}
}
