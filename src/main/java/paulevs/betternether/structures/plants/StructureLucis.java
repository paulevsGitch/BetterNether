package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.structures.IStructure;

public class StructureLucis implements IStructure
{
	@Override
	public void generate(World world, BlockPos pos, Random random)
	{
		IBlockState center = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CENTER);
		IBlockState side = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.SIDE);
		IBlockState corner = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CORNER);
		if (random.nextInt(3) == 0)
		{
			if (world.getBlockState(pos).getBlock() == Blocks.AIR)
				world.setBlockState(pos, center);
			if (world.getBlockState(pos.north()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.north(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
			if (world.getBlockState(pos.south()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.south(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (world.getBlockState(pos.east()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.east(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (world.getBlockState(pos.west()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.west(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));

			if (world.getBlockState(pos.north().east()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.north().east(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (world.getBlockState(pos.north().west()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.north().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (world.getBlockState(pos.south().east()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.south().east(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (world.getBlockState(pos.south().west()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
		}
		else
		{
			boolean offset = false;
			if (world.getBlockState(pos.north()).isFullBlock())
			{
				pos = pos.north();
				offset = true;
			}
			else if (world.getBlockState(pos.east()).isFullBlock())
			{
				pos = pos.east();
				offset = true;
			}
			if (!offset)
				world.setBlockState(pos, corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (world.getBlockState(pos.west()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (world.getBlockState(pos.south()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.south(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (world.getBlockState(pos.south().west()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
		}
	}
}
