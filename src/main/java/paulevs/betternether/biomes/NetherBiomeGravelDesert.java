package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlockNetherCactus;
import paulevs.betternether.blocks.BlocksRegister;

public class NetherBiomeGravelDesert extends NetherBiome
{
	public NetherBiomeGravelDesert(String name)
	{
		super(name);
	}

	@Override
	public void genSurfColumn(Chunk chunk, BlockPos pos, Random random)
	{
		for (int i = 1; i < 1 + random.nextInt(3); i++)
		{
			BlockPos p2 = pos.down(i);
			if (p2.getY() > -1 && chunk.getBlockState(p2).getBlock() == Blocks.NETHERRACK)
				if (chunk.getBlockState(p2.down()).getBlock() == Blocks.AIR)
					chunk.setBlockState(p2, Blocks.NETHERRACK.getDefaultState());
				else
					chunk.setBlockState(p2, Blocks.GRAVEL.getDefaultState());
		}
		if (chunk.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
			chunk.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
	}

	@Override
	public void genFloorObjects(Chunk chunk, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && chunk.getBlockState(pos).getBlock() == Blocks.GRAVEL && ((random.nextInt(16) == 0 || getFeatureNoise(pos, chunk.x, chunk.z) > 0.3)))
		{
			if (BlocksRegister.BLOCK_NETHER_CACTUS != Blocks.AIR && random.nextInt(8) == 0)
			{
				int h = 1 + random.nextInt(3);
				for (int i = 1; i < h; i++)
					if (chunk.getBlockState(pos.up(i)).getBlock() != Blocks.AIR)
					{
						h = i;
						break;
					}
				for (int i = 1; i < h; i++)
					chunk.setBlockState(pos.up(i), BlocksRegister.BLOCK_NETHER_CACTUS.getDefaultState().withProperty(BlockNetherCactus.SHAPE, BlockNetherCactus.EnumShape.SIDE));
				chunk.setBlockState(pos.up(h), BlocksRegister.BLOCK_NETHER_CACTUS.getDefaultState());
			}
			else if (random.nextInt(7) == 0)
			{
				if (BlocksRegister.BLOCK_BARREL_CACTUS != Blocks.AIR && random.nextBoolean())
					chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_BARREL_CACTUS.getDefaultState());
				else if (BlocksRegister.BLOCK_AGAVE != Blocks.AIR)
					chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_AGAVE.getDefaultState());
			}
		}
	}
}
