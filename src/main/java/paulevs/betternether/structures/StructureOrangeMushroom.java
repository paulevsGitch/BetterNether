package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlockNetherMycelium;
import paulevs.betternether.blocks.BlockOrangeMushroom;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureOrangeMushroom implements IStructure
{
	@Override
	public void generate(Chunk chunk, BlockPos pos, Random random)
	{
		Block under = chunk.getBlockState(pos).getBlock();
		if (under instanceof BlockNetherMycelium)
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
							if (under == BlocksRegister.BLOCK_NETHER_MYCELIUM && chunk.getBlockState(npos).getBlock() == Blocks.AIR)
							{
								chunk.setBlockState(npos, BlocksRegister
										.BLOCK_ORANGE_MUSHROOM
										.getDefaultState()
										.withProperty(BlockOrangeMushroom.SIZE, random.nextInt(4)));
							}
						}
						else
							break;
					}
			}
		}
	}
}
