package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureLucis extends Object implements IStructure
{
	@Override
	public void generate(World world, BlockPos pos, Random random)
	{
		BlockState center = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CENTER);
		BlockState side = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.SIDE);
		BlockState corner = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().with(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CORNER);
		
		if (random.nextInt(3) == 0)
		{
			if (canReplace(world.getBlockState(pos)))
				world.setBlockState(pos, center);
			if (canReplace(world.getBlockState(pos.north())))
				world.setBlockState(pos.north(), side.with(BlockLucisMushroom.FACING, Direction.NORTH));
			if (canReplace(world.getBlockState(pos.south())))
				world.setBlockState(pos.south(), side.with(BlockLucisMushroom.FACING, Direction.SOUTH));
			if (canReplace(world.getBlockState(pos.east())))
				world.setBlockState(pos.east(), side.with(BlockLucisMushroom.FACING, Direction.EAST));
			if (canReplace(world.getBlockState(pos.west())))
				world.setBlockState(pos.west(), side.with(BlockLucisMushroom.FACING, Direction.WEST));

			if (canReplace(world.getBlockState(pos.north().east())))
				world.setBlockState(pos.north().east(), corner.with(BlockLucisMushroom.FACING, Direction.SOUTH));
			if (canReplace(world.getBlockState(pos.north().west())))
				world.setBlockState(pos.north().west(), corner.with(BlockLucisMushroom.FACING, Direction.EAST));
			if (canReplace(world.getBlockState(pos.south().east())))
				world.setBlockState(pos.south().east(), corner.with(BlockLucisMushroom.FACING, Direction.WEST));
			if (canReplace(world.getBlockState(pos.south().west())))
				world.setBlockState(pos.south().west(), corner.with(BlockLucisMushroom.FACING, Direction.NORTH));
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
				world.setBlockState(pos, corner.with(BlockLucisMushroom.FACING, Direction.SOUTH));
			if (canReplace(world.getBlockState(pos.west())))
				world.setBlockState(pos.west(), corner.with(BlockLucisMushroom.FACING, Direction.EAST));
			if (canReplace(world.getBlockState(pos.south())))
				world.setBlockState(pos.south(), corner.with(BlockLucisMushroom.FACING, Direction.WEST));
			if (canReplace(world.getBlockState(pos.south().west())))
				world.setBlockState(pos.south().west(), corner.with(BlockLucisMushroom.FACING, Direction.NORTH));
		}
	}
	
	private boolean canReplace(BlockState state)
	{
		Block b = state.getBlock();
		return b == Blocks.AIR || b == BlocksRegister.BLOCK_LUCIS_SPORE;
	}
}
