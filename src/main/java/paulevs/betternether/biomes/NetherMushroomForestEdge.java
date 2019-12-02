package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherMushroomForestEdge extends NetherMushroomForest
{
	public NetherMushroomForestEdge(String name)
	{
		super(name);
	}
	
	@Override
	public void genFloorObjects(World world, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity)
		{
			if (world.getBlockState(pos).getBlock() == BlocksRegister.BLOCK_NETHER_MYCELIUM || world.getBlockState(pos).getBlock() == Blocks.MYCELIUM)
			{
				if (BNWorldGenerator.hasRedMushroomGen && random.nextInt(27) == 0)
					BNWorldGenerator.redMushroomGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasBrownMushroomGen && random.nextInt(26) == 0)
					BNWorldGenerator.brownMushroomGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasOrangeMushroomGen && random.nextInt(40) == 0)
					BNWorldGenerator.orangeMushroomGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasRedMoldGen && random.nextInt(32) == 0)
					BNWorldGenerator.redMoldGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasGrayMoldGen && random.nextInt(30) == 0)
					BNWorldGenerator.grayMoldGen.generate(world, pos, random);
				else if (random.nextInt(8) == 0)
					if (random.nextBoolean())
						world.setBlockState(pos.up(), Blocks.RED_MUSHROOM.getDefaultState());
					else
						world.setBlockState(pos.up(), Blocks.BROWN_MUSHROOM.getDefaultState());
			}
			else if (world.getBlockState(pos).getBlock() == Blocks.NETHERRACK && random.nextBoolean() && BlocksRegister.BLOCK_NETHER_GRASS != Blocks.AIR)
			{
				world.setBlockState(pos.up(), BlocksRegister.BLOCK_NETHER_GRASS.getDefaultState());
			}
		}
	}
	
	@Override
	public void genSurfColumn(World world, BlockPos pos, Random random)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
		{
			switch(random.nextInt(5))
			{
			case 0:
				world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
				world.setBlockState(pos.up(), Blocks.NETHER_WART
						.getDefaultState()
						.withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
				break;
			default:
				if (BlocksRegister.BLOCK_NETHER_MYCELIUM != Blocks.AIR)
					world.setBlockState(pos, BlocksRegister.BLOCK_NETHER_MYCELIUM.getDefaultState());
				else
					world.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
				break;
			}
			for (int i = 1; i < 1 + random.nextInt(3); i++)
			{
				BlockPos p2 = pos.down(i);
				if (p2.getY() > -1 && random.nextInt(3) == 0 && world.getBlockState(p2).getBlock() == Blocks.NETHERRACK)
					world.setBlockState(p2, Blocks.SOUL_SAND.getDefaultState());
			}
		}
	}
}
