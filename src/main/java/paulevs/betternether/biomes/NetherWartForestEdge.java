package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

public class NetherWartForestEdge extends NetherWartForest
{
	public NetherWartForestEdge(String name)
	{
		super(name);
	}
	
	@Override
	public void genFloorObjects(Chunk chunk, BlockPos pos, Random random)
	{
		if (random.nextFloat() <= plantDensity && chunk.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
		{
			if (BNWorldGenerator.hasWartTreeGen && random.nextInt(35) == 0)
			{
				BNWorldGenerator.wartTreeGen.generate(chunk, pos, random);
			}
			else if (random.nextInt(3) == 0 && chunk.getBlockState(pos).getBlock() == Blocks.SOUL_SAND && chunk.getBlockState(pos.up()).getBlock() == Blocks.AIR)
				chunk.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(random.nextInt(4))));
			else if (BlocksRegister.BLOCK_BLACK_BUSH != Blocks.AIR && random.nextInt(3) == 0)
				chunk.setBlockState(pos.up(), BlocksRegister.BLOCK_BLACK_BUSH.getDefaultState());
		}
	}
}
