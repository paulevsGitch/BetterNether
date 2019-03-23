package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureReeds
{
	public static boolean generate(Chunk chunk, BlockPos pos, Random random)
	{
		if (checkLava(chunk, pos))
		{
			int h = 1 + random.nextInt(4);
			for (int i = 1; i < h; i++)
				chunk.setBlockState(pos.up(i), BlocksRegister.BLOCK_NETHER_REED.getDefaultState());
			return true;
		}
		return false;
	}

	private static boolean checkLava(Chunk chunk, BlockPos pos)
	{
		if (pos.getX() > 0 && pos.getZ() > 0 && pos.getX() < 15 && pos.getZ() < 15)
		{
			boolean b = chunk.getBlockState(pos.north()).getBlock() == Blocks.LAVA;
			if (b)
				return true;
			b = chunk.getBlockState(pos.south()).getBlock() == Blocks.LAVA;
			if (b)
				return true;
			b = chunk.getBlockState(pos.east()).getBlock() == Blocks.LAVA;
			if (b)
				return true;
			b = chunk.getBlockState(pos.west()).getBlock() == Blocks.LAVA;
			if (b)
				return true;
		}
		return false;
	}
}
