package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherWartForest extends NetherBiome
{
	public NetherWartForest(String name)
	{
		super(name);
	}

	@Override
	public void genFloorObjects(World world, BlockPos pos, Random random)
	{
			if (random.nextFloat() <= plantDensity && world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
			{
				if (BNWorldGenerator.hasWartTreeGen && random.nextInt(15) == 0)
				{
					BNWorldGenerator.wartTreeGen.generate(world, pos, random);
				}
				else if (BNWorldGenerator.hasWartsGen && random.nextInt(3) == 0 && world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND && world.getBlockState(pos.up()).getBlock() == Blocks.AIR)
					world.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
		}
	}
	
	@Override
	public void genSurfColumn(World world, BlockPos pos, Random random)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
		{
			switch(random.nextInt(3))
			{
			case 0:
			case 1:
				world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
				break;
			case 2:
				if (BlocksRegister.BLOCK_NETHERRACK_MOSS != Blocks.AIR)
					world.setBlockState(pos, BlocksRegister.BLOCK_NETHERRACK_MOSS.getDefaultState());
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
