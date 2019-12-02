package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherBiomeJungle extends NetherBiome
{
	public NetherBiomeJungle(String name)
	{
		super(name);
	}

	@Override
	public void genFloorObjects(World world, BlockPos pos, Random random)
	{
		Block ground = world.getBlockState(pos).getBlock();
		if (ground instanceof BlockNetherrack || ground == Blocks.SOUL_SAND)
		{
			boolean reeds = false;
			if (BlocksRegister.BLOCK_NETHER_REED != Blocks.AIR && random.nextInt(4) == 0)
				reeds = StructureReeds.generate(world, pos, random);
			if (!reeds)
			{
				if (BNWorldGenerator.hasEyeGen && random.nextInt(8) == 0)
					BNWorldGenerator.stalagnateGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasMagmaFlowerGen && pos.getY() < 37 && pos.getY() > 23 && random.nextInt(32) == 0)
					BNWorldGenerator.magmaFlowerGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasEggPlantGen && random.nextInt(16) == 0)
					BNWorldGenerator.eggPlantGen.generate(world, pos, random);
				else if (BlocksRegister.BLOCK_NETHER_GRASS != Blocks.AIR && random.nextInt(3) != 0)
					world.setBlockState(pos.up(), BlocksRegister.BLOCK_NETHER_GRASS.getDefaultState());
			}
		}
	}

	@Override
	public void genWallObjects(World world, BlockPos origin, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && BNWorldGenerator.hasLucisGen && random.nextInt(4) == 0 && world.getBlockState(origin).getBlock() == Blocks.NETHERRACK)
			BNWorldGenerator.lucisGen.generate(world, pos, random);
	}

	@Override
	public void genCeilObjects(World world, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && BNWorldGenerator.hasEyeGen && random.nextInt(8) == 0 && random.nextDouble() * 4D + 0.5 < getFeatureNoise(pos))
			BNWorldGenerator.eyeGen.generate(world, pos.down(), random);
	}

	@Override
	public void genSurfColumn(World world, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && world.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
			if (BlocksRegister.BLOCK_NETHERRACK_MOSS != Blocks.AIR)
			{
				switch(random.nextInt(3))
				{
				case 0:
					world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
					break;
				case 1:
					world.setBlockState(pos, BlocksRegister.BLOCK_NETHERRACK_MOSS.getDefaultState());
					break;
				}
			}
			else
			{
				if(random.nextInt(3) == 0)
					world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
			}
	}
}
