package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlockBrownLargeMushroom;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureMedBrownMushroom implements IStructure
{
	@Override
	public void generate(Chunk chunk, BlockPos pos, Random random)
	{
		//if (pos.getX() > 5 && pos.getZ() > 5 && pos.getX() < 11 && pos.getZ() < 11)
		{
			Block under = chunk.getBlockState(pos).getBlock();
			if (under instanceof BlockNetherrack || under == Blocks.SOUL_SAND)
			{
				for (int i = 0; i < 10; i++)
				{
					int x = pos.getX() + (int) (random.nextGaussian() * 2);
					int z = pos.getZ() + (int) (random.nextGaussian() * 2);
					int y = pos.getY() + random.nextInt(6);
					if (x > 0 && x < 15 && z > 0 && z < 15)
						for (int j = 0; j < 6; j++)
						{
							BlockPos npos = new BlockPos(x, y - j, z);
							if (npos.getY() > 31)
							{
								under = chunk.getBlockState(npos.down()).getBlock();
								if (under == BlocksRegister.BLOCK_NETHER_MYCELIUM)
								{
									grow(chunk, npos, random);
								}
							}
							else
								break;
						}
				}
			}
		}
	}
	
	private void grow(Chunk chunk, BlockPos pos, Random random)
	{
		int size = 2 + random.nextInt(3);
		for (int y = 1; y <= size; y++)
			if (chunk.getBlockState(pos.up(y)).getBlock() != Blocks.AIR)
			{
				size = y - 1;
				break;
			}
		boolean hasAir = true;
		if (size > 2)
			for (int x = -1; x < 2; x++)
				for (int z = -1; z < 2; z++)
					hasAir = hasAir && chunk.getBlockState(pos.up(size).add(x, 0, z)).getBlock() == Blocks.AIR;
		if (hasAir && size > 2)
		{
			IBlockState middle = BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.MIDDLE);
			chunk.setBlockState(pos, BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState());
			for (int y = 1; y < size; y++)
				chunk.setBlockState(pos.up(y), middle);
			pos = pos.up(size);
			chunk.setBlockState(pos, BlocksRegister
					.BLOCK_BROWN_LARGE_MUSHROOM
					.getDefaultState()
					.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.TOP));
				chunk.setBlockState(pos.north(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.SIDE_N));
				chunk.setBlockState(pos.south(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.SIDE_S));
				chunk.setBlockState(pos.east(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.SIDE_E));
				chunk.setBlockState(pos.west(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.SIDE_W));
				chunk.setBlockState(pos.north().east(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.CORNER_N));
				chunk.setBlockState(pos.north().west(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.CORNER_W));
				chunk.setBlockState(pos.south().east(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.CORNER_E));
				chunk.setBlockState(pos.south().west(), BlocksRegister
						.BLOCK_BROWN_LARGE_MUSHROOM
						.getDefaultState()
						.withProperty(BlockBrownLargeMushroom.SHAPE, BlockBrownLargeMushroom.EnumShape.CORNER_S));
		}
	}

}
