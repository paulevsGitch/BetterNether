package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlockLucisMushroom;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureLucis implements IStructure
{
	@Override
	public void generate(Chunk chunk, BlockPos pos, Random random)
	{
		IBlockState center = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CENTER);
		IBlockState side = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.SIDE);
		IBlockState corner = BlocksRegister.BLOCK_LUCIS_MUSHROOM.getDefaultState().withProperty(BlockLucisMushroom.SHAPE, BlockLucisMushroom.EnumShape.CORNER);
		World world = chunk.getWorld();
		if (random.nextInt(3) == 0)
		{
			if (chunk.getBlockState(pos).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos, center);
			if (chunk.getBlockState(pos.north()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.north(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
			if (chunk.getBlockState(pos.south()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (chunk.getBlockState(pos.east()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.east(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (chunk.getBlockState(pos.west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.west(), side.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));

			if (chunk.getBlockState(pos.north().east()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.north().east(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (chunk.getBlockState(pos.north().west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.north().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (chunk.getBlockState(pos.south().east()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south().east(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (chunk.getBlockState(pos.south().west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
		}
		else
		{
			/*if (chunk.getBlockState(pos).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos, corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (chunk.getBlockState(pos.west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (chunk.getBlockState(pos.south()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (chunk.getBlockState(pos.south().west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));*/
			boolean offset = false;
			if (chunk.getBlockState(pos.north()).isFullBlock())
			{
				pos = pos.north();
				offset = true;
			}
			else if (chunk.getBlockState(pos.east()).isFullBlock())
			{
				pos = pos.east();
				offset = true;
			}
			if (!offset)
				chunk.setBlockState(pos, corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.SOUTH));
			if (chunk.getBlockState(pos.west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.EAST));
			if (chunk.getBlockState(pos.south()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.WEST));
			if (chunk.getBlockState(pos.south().west()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.south().west(), corner.withProperty(BlockLucisMushroom.FACING, BlockLucisMushroom.EnumDir.NORTH));
		}
	}
}
