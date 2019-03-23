package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherWartForest extends NetherBiome
{
	public NetherWartForest(String name)
	{
		super(name);
	}

	@Override
	public void genFloorObjects(Chunk chunk, BlockPos pos, Random random)
	{
		//if (pos.getX() > 3 && pos.getZ() > 3 && pos.getX() < 13 && pos.getZ() < 13)
			if (random.nextFloat() <= plantDensity && chunk.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
			{
				if (BNWorldGenerator.hasWartTreeGen && random.nextInt(15) == 0)
				{
					BNWorldGenerator.wartTreeGen.generate(chunk, pos, random);
				}
				else if (random.nextInt(3) == 0 && chunk.getBlockState(pos).getBlock() == Blocks.SOUL_SAND && chunk.getBlockState(pos.up()).getBlock() == Blocks.AIR)
					chunk.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
		}
	}
	
	@Override
	public void genSurfColumn(Chunk chunk, BlockPos pos, Random random)
	{
		if (chunk.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
		{
			switch(random.nextInt(3))
			{
			case 0:
			case 1:
				chunk.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
				break;
			case 2:
				if (BlocksRegister.BLOCK_NETHERRACK_MOSS != Blocks.AIR)
					chunk.setBlockState(pos, BlocksRegister.BLOCK_NETHERRACK_MOSS.getDefaultState());
				break;
			}
			for (int i = 1; i < 1 + random.nextInt(3); i++)
			{
				BlockPos p2 = pos.down(i);
				if (p2.getY() > -1 && random.nextInt(3) == 0 && chunk.getBlockState(p2).getBlock() == Blocks.NETHERRACK)
					chunk.setBlockState(p2, Blocks.SOUL_SAND.getDefaultState());
			}
		}
	}
}
