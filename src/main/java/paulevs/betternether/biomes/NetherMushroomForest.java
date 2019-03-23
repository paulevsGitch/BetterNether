package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlockRedLargeMushroom;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherMushroomForest extends NetherBiome
{
	public NetherMushroomForest(String name)
	{
		super(name);
	}

	@Override
	public void genSurfColumn(Chunk chunk, BlockPos pos, Random random)
	{
		if (chunk.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
		{
			switch(random.nextInt(10))
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

	@Override
	public void genFloorObjects(Chunk chunk, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && chunk.getBlockState(pos).getBlock() == BlocksRegister.BLOCK_NETHER_MYCELIUM || chunk.getBlockState(pos).getBlock() == Blocks.MYCELIUM)
		{
			if (BNWorldGenerator.hasRedMushroomGen && random.nextInt(7) == 0)
				BNWorldGenerator.redMushroomGen.generate(chunk, pos, random);
			else if (BNWorldGenerator.hasBrownMushroomGen && random.nextInt(6) == 0)
				BNWorldGenerator.brownMushroomGen.generate(chunk, pos, random);
			else if (BNWorldGenerator.hasOrangeMushroomGen && random.nextInt(20) == 0)
				BNWorldGenerator.orangeMushroomGen.generate(chunk, pos, random);
			else if (BNWorldGenerator.hasRedMoldGen && random.nextInt(12) == 0)
				BNWorldGenerator.redMoldGen.generate(chunk, pos, random);
			else if (BNWorldGenerator.hasGrayMoldGen && random.nextInt(10) == 0)
				BNWorldGenerator.grayMoldGen.generate(chunk, pos, random);
			else if (random.nextInt(3) == 0)
				if (random.nextBoolean())
					chunk.setBlockState(pos.up(), Blocks.RED_MUSHROOM.getDefaultState());
				else
					chunk.setBlockState(pos.up(), Blocks.BROWN_MUSHROOM.getDefaultState());
		}
	}
	
	@Override
	public void genWallObjects(Chunk chunk, BlockPos origin, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && BNWorldGenerator.hasLucisGen && random.nextInt(4) == 0 && chunk.getBlockState(origin).getBlock() == Blocks.NETHERRACK)
			BNWorldGenerator.lucisGen.generate(chunk, pos, random);
	}

	private void growRedMushroom(Chunk chunk, BlockPos pos, Random random)
	{
		int size = 2 + random.nextInt(3);
		for (int y = 1; y <= size; y++)
			if (chunk.getBlockState(pos.up(y)).getBlock() != Blocks.AIR)
			{
				size = y;
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
