package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherPoorGrasslands extends NetherGrasslands
{
	public NetherPoorGrasslands(String name)
	{
		super(name);
	}
	
	@Override
	public void genFloorObjects(World world, BlockPos pos, Random random)
	{
		Block ground = world.getBlockState(pos).getBlock();
		if (random.nextFloat() <= plantDensity && ground instanceof BlockNetherrack || ground == Blocks.SOUL_SAND)
		{
			boolean reeds = false;
			if (BlocksRegister.BLOCK_NETHER_REED != Blocks.AIR && random.nextInt(4) == 0)
				reeds = StructureReeds.generate(world, pos, random);
			if (!reeds && random.nextFloat() < 0.4F)
			{
				if (BNWorldGenerator.hasWartsGen && ground == Blocks.SOUL_SAND && random.nextInt(2) == 0)
					world.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
				else if (BNWorldGenerator.hasMagmaFlowerGen && pos.getY() < 37 && pos.getY() > 23 && random.nextInt(32) == 0)
					BNWorldGenerator.magmaFlowerGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasSmokerGen && random.nextInt(16) == 0)
					BNWorldGenerator.smokerGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasInkBushGen && random.nextInt(16) == 0)
					BNWorldGenerator.inkBushGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasEggPlantGen && random.nextInt(16) == 0)
					BNWorldGenerator.eggPlantGen.generate(world, pos, random);
				else if (BNWorldGenerator.hasBlackAppleGen && random.nextInt(16) == 0)
					BNWorldGenerator.blackAppleGen.generate(world, pos, random);
				else if (BlocksRegister.BLOCK_BLACK_BUSH != Blocks.AIR && random.nextInt(6) == 0 && getFeatureNoise(pos) > 0.3)
					world.setBlockState(pos.up(), BlocksRegister.BLOCK_BLACK_BUSH.getDefaultState());
				else if (BlocksRegister.BLOCK_WART_SEED != Blocks.AIR && random.nextInt(5) == 0)
					world.setBlockState(pos.up(), BlocksRegister.BLOCK_WART_SEED.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.UP));
				else if (BlocksRegister.BLOCK_NETHER_GRASS != Blocks.AIR && random.nextInt(4) != 0)
					world.setBlockState(pos.up(), BlocksRegister.BLOCK_NETHER_GRASS.getDefaultState());
			}
		}
	}
}
