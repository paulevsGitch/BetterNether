package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.BlockMagma;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.noise.WorleyNoise;

public class StructureMagmaFlower implements IStructure
{
	private static WorleyNoise offsetNoise;
	
	@Override
	public void generate(Chunk chunk, BlockPos pos, Random random)
	{
		if (pos.getX() > 3 && pos.getZ() > 3 && pos.getX() < 12 && pos.getZ() < 12)
		{
			if (chunk.getBlockState(pos).getBlock() instanceof BlockNetherrack)
			{
				IBlockState state = BlocksRegister.BLOCK_MAGMA_FLOWER.getDefaultState();
				offsetNoise = new WorleyNoise(((long) pos.getX() << 32L) | (long) pos.getZ());
				for (int y = -3; y < 4; y++)
					for (int x = -3; x < 4; x++)
					{
						int oz = x + (int) (offsetNoise.GetValue(x, y) * 3);
						for (int z = -3; z < 4; z++)
						{
							int ox = y + (int) (offsetNoise.GetValue(y, z) * 3);
							int oy = z + (int) (offsetNoise.GetValue(x, z) * 3);
							if (ox * ox + oy * oy + oz * oz <= 9)
							{
								BlockPos npos = pos.add(ox, oy, oz);
								if (chunk.getBlockState(npos).isFullBlock())
									chunk.setBlockState(npos, Blocks.MAGMA.getDefaultState());
								else if (chunk.getBlockState(npos).getBlock() == Blocks.AIR && chunk.getBlockState(npos.down()).getBlock() instanceof BlockMagma)
									chunk.setBlockState(npos, state);
							}
						}
					}
			}
		}
	}
}
