package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;

public class StructureWartCap implements IStructure
{
	private static final BlockState INSIDE = Blocks
			.RED_MUSHROOM_BLOCK
			.getDefaultState()
			.with(MushroomBlock.NORTH, false)
			.with(MushroomBlock.SOUTH, false)
			.with(MushroomBlock.EAST, false)
			.with(MushroomBlock.WEST, false)
			.with(MushroomBlock.UP, false)
			.with(MushroomBlock.DOWN, false);
	private static final BlockState SKIN = Blocks
			.NETHER_WART_BLOCK
			.getDefaultState();
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		int radius = 3 + random.nextInt(3);
		int r2 = radius * radius;
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
	
	private void place(IWorld world, BlockPos pos)
	{
		if (world.isAir(pos))
		{
			BlockState placed = INSIDE;
			
			if (world.isAir(pos.up()))
				placed = SKIN;
			if (world.isAir(pos.north()))
				placed = SKIN;
			if (world.isAir(pos.south()))
				placed = SKIN;
			if (world.isAir(pos.east()))
				placed = SKIN;
			if (world.isAir(pos.west()))
				placed = SKIN;
			
			BlocksHelper.setWithoutUpdate(world, pos, placed);
		}
	}
}
