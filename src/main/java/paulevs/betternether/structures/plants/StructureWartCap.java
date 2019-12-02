package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.structures.IStructure;

public class StructureWartCap implements IStructure
{
	private static IBlockState inside = Blocks
			.RED_MUSHROOM_BLOCK
			.getDefaultState()
			.withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.ALL_INSIDE);
	private static IBlockState skin = Blocks
			.NETHER_WART_BLOCK
			.getDefaultState();
	
	@Override
	public void generate(World world, BlockPos pos, Random random)
	{
		int radius = 3 + random.nextInt(3);
		int r2 = radius * radius;
		int r3 = r2 >> 1;
		for (int y = 0; y <= radius >> 1; y++)
			for (int x = -radius; x <= radius; x++)
				for (int z = -radius; z <= radius; z++)
				{
					int d = x * x + y * y * 6 + z * z;
					if (d <= r2)
					{
						place(world, pos.add(x, y, z));
					}
				}
	}
	
	private void place(World world, BlockPos pos)
	{
		if (world.getBlockState(pos).getBlock() == Blocks.AIR || world.getBlockState(pos) == skin)
		{
			world.setBlockState(pos, inside);
			if (world.getBlockState(pos.up()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.up(), skin);
			if (world.getBlockState(pos.north()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.north(), skin);
			if (world.getBlockState(pos.south()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.south(), skin);
			if (world.getBlockState(pos.east()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.east(), skin);
			if (world.getBlockState(pos.west()).getBlock() == Blocks.AIR)
				world.setBlockState(pos.west(), skin);
		}
	}
}
