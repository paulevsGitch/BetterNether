package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;

public class StructureTwistedVines implements IStructure
{
	private Mutable npos = new Mutable();
	
	private boolean canPlaceAt(WorldAccess world, BlockPos pos)
	{
		Block block = world.getBlockState(pos.down()).getBlock();
		return block == Blocks.WARPED_NYLIUM || block == Blocks.TWISTING_VINES;
	}
	
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (canPlaceAt(world, pos))
		{
			for (int i = 0; i < 10; i++)
			{
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++)
				{
					npos.set(x, y - j, z);
					if (world.isAir(npos) && canPlaceAt(world, npos))
					{
						int h = random.nextInt(20) + 1;
						int sy = npos.getY();
						for (int n = 0; n < h; n++)
						{
							npos.setY(sy + n);
							if (!world.isAir(npos.up()))
							{
								BlocksHelper.setWithoutUpdate(world, npos, Blocks.TWISTING_VINES.getDefaultState());
								break;
							}
							BlocksHelper.setWithoutUpdate(world, npos, Blocks.TWISTING_VINES_PLANT.getDefaultState());
						}
						BlocksHelper.setWithoutUpdate(world, npos, Blocks.TWISTING_VINES.getDefaultState());
						break;
					}
				}
			}
		}
	}
}
