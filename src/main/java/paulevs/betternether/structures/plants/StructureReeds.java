package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.blocks.BlocksRegister;

public class StructureReeds
{
	public static boolean generate(World world, BlockPos pos, Random random)
	{
		if (checkLava(world, pos))
		{
			int h = 1 + random.nextInt(4);
			for (int i = 1; i < h; i++)
				world.setBlockState(pos.up(i), BlocksRegister.BLOCK_NETHER_REED.getDefaultState());
			return true;
		}
		return false;
	}

	private static boolean checkLava(World world, BlockPos pos)
	{
		boolean b = world.getBlockState(pos.north()).getBlock() == Blocks.LAVA;
		if (b)
			return true;
		b = world.getBlockState(pos.south()).getBlock() == Blocks.LAVA;
		if (b)
			return true;
		b = world.getBlockState(pos.east()).getBlock() == Blocks.LAVA;
		if (b)
			return true;
		b = world.getBlockState(pos.west()).getBlock() == Blocks.LAVA;
		if (b)
			return true;
		return false;
	}
}
