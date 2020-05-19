package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureSoulVein implements IStructure
{
	private Mutable npos = new Mutable();
	
	private boolean canPlaceAt(WorldAccess world, BlockPos pos)
	{
		return BlocksRegistry.SOUL_VEIN.canPlaceAt(BlocksRegistry.SOUL_VEIN.getDefaultState(), world, pos);
	}
	
	@Override
	public void generate(WorldAccess world, BlockPos pos, Random random)
	{
		if (world.isAir(pos) && canPlaceAt(world, pos))
		{
			BlockState state = BlocksRegistry.SOUL_VEIN.getDefaultState();
			BlockState sand = BlocksRegistry.VEINED_SAND.getDefaultState();
			int x1 = pos.getX() - 1;
			int x2 = pos.getX() + 1;
			int z1 = pos.getZ() - 1;
			int z2 = pos.getZ() + 1;
			for (int x = x1; x <= x2; x++)
				for (int z = z1; z <= z2; z++)
				{
					int y = pos.getY() + 2;
					for (int j = 0; j < 4; j++)
					{
						npos.set(x, y - j, z);
						if (world.isAir(npos) && canPlaceAt(world, npos))
						{
							BlocksHelper.setWithoutUpdate(world, npos, state);
							BlocksHelper.setWithoutUpdate(world, npos.down(), sand);
						}
					}
				}
		}
	}
}