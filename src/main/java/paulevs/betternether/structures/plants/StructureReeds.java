package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureReeds implements IStructure
{
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (world.isAir(pos) && BlocksRegistry.NETHER_REED.canPlaceAt(world.getBlockState(pos), world, pos))
		{
			BlockState med = BlocksRegistry.NETHER_REED.getDefaultState().with(BlockNetherReed.TOP, false);
			int h = random.nextInt(3);
			for (int i = 0; i < h; i++)
			{
				BlockPos posN = pos.up(i);
				BlockPos up = posN.up();
				if (world.isAir(posN))
				{
					if (world.isAir(up))
						BlocksHelper.setWithoutUpdate(world, posN, med);
					else
					{
						BlocksHelper.setWithoutUpdate(world, posN, BlocksRegistry.NETHER_REED.getDefaultState());
						return;
					}
				}
			}
			BlocksHelper.setWithoutUpdate(world, pos.up(h), BlocksRegistry.NETHER_REED.getDefaultState());
		}
	}
}