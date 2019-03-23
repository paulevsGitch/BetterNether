package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.structures.StructureReeds;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherPoorGrasslands extends NetherGrasslands
{
	public NetherPoorGrasslands(String name)
	{
		super(name);
	}
	
	@Override
	public void genFloorObjects(Chunk chunk, BlockPos pos, Random random)
	{
		Block ground = chunk.getBlockState(pos).getBlock();
		if (random.nextFloat() <= plantDensity && ground instanceof BlockNetherrack || ground == Blocks.SOUL_SAND)
		{
			boolean reeds = false;
			if (BlocksRegister.BLOCK_NETHER_REED != Blocks.AIR && random.nextInt(4) == 0)
				reeds = StructureReeds.generate(chunk, pos, random);
			if (!reeds && random.nextFloat() < 0.4F)
			{
				if (BNWorldGenerator.hasWartTreeGen && ground == Blocks.SOUL_SAND && random.nextInt(2) == 0)
					chunk.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
				else if (BNWorldGenerator.hasMagmaFlowerGen && pos.getY() < 37 && pos.getY() > 23 && random.nextInt(32) == 0)
					BNWorldGenerator.magmaFlowerGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasSmokerGen && random.nextInt(16) == 0)
					BNWorldGenerator.smokerGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasInkBushGen && random.nextInt(16) == 0)
					BNWorldGenerator.inkBushGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasEggPlantGen && random.nextInt(16) == 0)
					BNWorldGenerator.eggPlantGen.generate(chunk, pos, random);
				else if (BNWorldGenerator.hasBlackAppleGen && random.nextInt(16) == 0)
					BNWorldGenerator.blackAppleGen.generate(chunk, pos, random);
				else if (BlocksRegister.BLOCK_BLACK_BUSH != Blocks.AIR && random.nextInt(6) == 0 && getFeatureNoise(pos, chunk.x, chunk.x) > 0.3)
					chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_BLACK_BUSH.getDefaultState());
				else if (BlocksRegister.BLOCK_WART_SEED != Blocks.AIR && random.nextInt(5) == 0)
					chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_WART_SEED.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.UP));
				else if (BlocksRegister.BLOCK_NETHER_GRASS != Blocks.AIR && random.nextInt(4) != 0)
					chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_NETHER_GRASS.getDefaultState());
			}
		}
	}
}
