package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureNetherSakura implements IStructure
{
	private static final Mutable POS = new Mutable();
	private static final Mutable POS2 = new Mutable();
	
	public StructureNetherSakura() {}
	
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (pos.getY() < 96) return;
		grow(world, pos, random, true);
	}
	
	public void grow(ServerWorldAccess world, BlockPos pos, Random random, boolean natural)
	{
		int l = MHelper.randRange(15, 24, random);
		double height = MHelper.randRange(10, 15, random);
		double radius = height * (0.2 + random.nextDouble() * 0.1);
		
		if ((l + height) - BlocksHelper.downRay(world, pos, (int) (l + height)) > 10) return;
		
		l = BlocksHelper.downRay(world, pos, l + 1);
		int l2 = l * 2 / 3;
		for (int x = -3; x <= 3; x++)
		{
			int x2 = x * x;
			POS.setX(pos.getX() + x);
			for (int z = -3; z <= 3; z++)
			{
				int z2 = z * z;
				double d = x2 + z2 + 1.4;
				if (d < 10)
				{
					if (d < 2.8 || random.nextBoolean())
					{
						POS.setZ(pos.getZ() + z);
						double length = MHelper.randRange(l2, l, random) / (d > 2 ? d : 1);
						if (length < 1) length = 1;
						int start = MHelper.randRange(-2, 0, random);
						for (int y = start; y < length; y++)
						{
							POS.setY(pos.getY() - y);
							if (canReplace(world.getBlockState(POS))) BlocksHelper.setWithoutUpdate(world, POS, BlocksRegistry.NETHER_SAKURA.log.getDefaultState());
						}
						if (BlocksRegistry.NETHER_SAKURA.isTreeLog(world.getBlockState(POS).getBlock())) BlocksHelper.setWithoutUpdate(world, POS, BlocksRegistry.NETHER_SAKURA.bark.getDefaultState());
					}
					
					if (d < 2)
					{
						crown(world, POS, radius, height, random);
					}
				}
			}
		}
	}
	
	private void crown(WorldAccess world, BlockPos pos, double radius, double height, Random random)
	{
		BlockState leaves = BlocksRegistry.NETHER_SAKURA_LEAVES.getDefaultState();
		double r2 = radius * radius;
		int start = (int) Math.floor(- radius);
		for (int cy = 0; cy <= radius; cy++)
		{
			int cy2 = cy * cy;
			POS2.setY((int) (pos.getY() + cy));
			for (int cx = start; cx <= radius; cx++)
			{
				int cx2 = cx * cx;
				POS2.setX(pos.getX() + cx);
				for (int cz = start; cz <= radius; cz++)
				{
					int cz2 = cz * cz;
					if (cx2 + cy2 + cz2 < r2)
					{
						POS2.setZ(pos.getZ() + cz);
						if (world.getBlockState(POS2).getMaterial().isReplaceable()) BlocksHelper.setWithoutUpdate(world, POS2, leaves);
					}
				}
			}
		}
		BlockState state;
		for (int cy = 0; cy <= height; cy++)
		{
			r2 = radius * (1 - (double) cy / height);
			r2 *= r2;
			POS2.setX(pos.getX());
			POS2.setZ(pos.getZ());
			POS2.setY(pos.getY() - cy);
			if (!(state = world.getBlockState(POS2)).getMaterial().isReplaceable() && !BlocksRegistry.NETHER_SAKURA.isTreeLog(state.getBlock())) return;
			for (int cx = start; cx <= radius; cx++)
			{
				int cx2 = cx * cx;
				POS2.setX(pos.getX() + cx);
				for (int cz = start; cz <= radius; cz++)
				{
					int cz2 = cz * cz;
					if (cx2 + cz2 < r2)
					{
						POS2.setZ(pos.getZ() + cz);
						if (world.getBlockState(POS2).getMaterial().isReplaceable()) BlocksHelper.setWithoutUpdate(world, POS2, leaves);
					}
				}
			}
		}
	}
	
	private boolean canReplace(BlockState state)
	{
		return BlocksHelper.isNetherGround(state) || state.getMaterial().isReplaceable();
	}
}
