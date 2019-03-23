package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlockRedLargeMushroom;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureMedRedMushroom implements IStructure
{
	@Override
	public void generate(Chunk chunk, BlockPos pos, Random random)
	{
		//if (pos.getX() > 3 && pos.getZ() > 3 && pos.getX() < 13 && pos.getZ() < 13)
		{
			Block under = chunk.getBlockState(pos).getBlock();
			if (under instanceof BlockNetherrack || under == Blocks.SOUL_SAND)
			{
				for (int i = 0; i < 10; i++)
				{
					int x = pos.getX() + (int) (random.nextGaussian() * 2);
					int z = pos.getZ() + (int) (random.nextGaussian() * 2);
					int y = pos.getY() + random.nextInt(6);
					if (x >= 0 && x < 16 && z >= 0 && z < 16)
						for (int j = 0; j < 6; j++)
						{
							BlockPos npos = new BlockPos(x, y - j, z);
							if (npos.getY() > 31)
							{
								under = chunk.getBlockState(npos.down()).getBlock();
								if (under == BlocksRegister.BLOCK_NETHER_MYCELIUM)
								{
									grow(chunk, npos.down(), random);
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
		if (size > 2)
		{
			IBlockState middle = BlocksRegister.BLOCK_RED_LARGE_MUSHROOM.getDefaultState().withProperty(BlockRedLargeMushroom.SHAPE, BlockRedLargeMushroom.EnumShape.MIDDLE);
			for (int y = 2; y < size; y++)
				chunk.setBlockState(pos.up(y), middle);
			chunk.setBlockState(pos.up(size), BlocksRegister.BLOCK_RED_LARGE_MUSHROOM.getDefaultState().withProperty(BlockRedLargeMushroom.SHAPE, BlockRedLargeMushroom.EnumShape.TOP));
			chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_RED_LARGE_MUSHROOM.getDefaultState());
		}
	}

}
