package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherMushroomForestEdge extends NetherMushroomForest
{
	public NetherMushroomForestEdge(String name)
	{
		super(name);
	}
	
	@Override
	public void genFloorObjects(Chunk chunk, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity)
		{
			if (chunk.getBlockState(pos).getBlock() == BlocksRegister.BLOCK_NETHER_MYCELIUM || chunk.getBlockState(pos).getBlock() == Blocks.MYCELIUM)
			{
				if (BNWorldGenerator.hasRedMushroomGen && random.nextInt(27) == 0)
					BNWorldGenerator.redMushroomGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasBrownMushroomGen && random.nextInt(26) == 0)
					BNWorldGenerator.brownMushroomGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasOrangeMushroomGen && random.nextInt(40) == 0)
					BNWorldGenerator.orangeMushroomGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasRedMoldGen && random.nextInt(32) == 0)
					BNWorldGenerator.redMoldGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasGrayMoldGen && random.nextInt(30) == 0)
					BNWorldGenerator.grayMoldGen.generate(chunk, pos, random);
				else if (random.nextInt(8) == 0)
					if (random.nextBoolean())
						chunk.setBlockState(pos.up(), Blocks.RED_MUSHROOM.getDefaultState());
					else
						chunk.setBlockState(pos.up(), Blocks.BROWN_MUSHROOM.getDefaultState());
			}
			else if (chunk.getBlockState(pos).getBlock() == Blocks.NETHERRACK && random.nextBoolean() && BlocksRegister.BLOCK_NETHER_GRASS != Blocks.AIR)
			{
				chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_NETHER_GRASS.getDefaultState());
			}
		}
	}
	
	@Override
	public void genSurfColumn(Chunk chunk, BlockPos pos, Random random)
	{
		if (chunk.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
		{
			switch(random.nextInt(5))
			{
			case 0:
				chunk.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
				chunk.setBlockState(pos.up(), Blocks.NETHER_WART
						.getDefaultState()
						.withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
				break;
			default:
				if (BlocksRegister.BLOCK_NETHER_MYCELIUM != Blocks.AIR)
					chunk.setBlockState(pos, BlocksRegister.BLOCK_NETHER_MYCELIUM.getDefaultState());
				else
					chunk.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
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
