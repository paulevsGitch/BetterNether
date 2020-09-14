package paulevs.betternether.structures.plants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureAnchorTreeRoot implements IStructure
{
	private static final Set<BlockPos> BLOCKS = new HashSet<BlockPos>(2048);
	private static final Mutable POS = new Mutable();
	
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (pos.getY() < 96) return;
		
		double angle = random.nextDouble() * Math.PI * 2;
		double dx = Math.sin(angle);
		double dz = Math.cos(angle);
		double size = MHelper.randRange(10, 25, random) * 0.5;
		int count = MHelper.floor(size / 1.5);
		if (count < 3) count = 3;
		if ((count & 1) == 0) count++;
		POS.set(pos.getX() - dx * size, pos.getY() + 10, pos.getZ() - dz * size);
		BlockPos start = POS.up(BlocksHelper.upRay(world, POS, 64));
		if (start.getY() < pos.getY()) start = POS.set(start).add(0, 10, 0).toImmutable();
		POS.set(pos.getX() + dx * size, pos.getY() + 10, pos.getZ() + dz * size);
		BlockPos end = POS.up(BlocksHelper.upRay(world, POS, 64));
		if (end.getY() < pos.getY()) end = POS.set(end).add(0, 10, 0).toImmutable();
		List<BlockPos> blocks = lineParable(start, end, count, random, 0.8);
		
		BLOCKS.clear();
		buildLine(blocks, 1.3 + random.nextDouble());
		
		BlockState state;
		for (BlockPos bpos: BLOCKS)
		{
			if (bpos.getY() < 1 || bpos.getY() > 126) continue;
			if (!BlocksHelper.isNetherGround(state = world.getBlockState(bpos)) && !state.getMaterial().isReplaceable()) continue;
			if (BLOCKS.contains(bpos.up()) && BLOCKS.contains(bpos.down()))
				BlocksHelper.setWithoutUpdate(world, bpos, BlocksRegistry.ANCHOR_TREE.log.getDefaultState());
			else
				BlocksHelper.setWithoutUpdate(world, bpos, BlocksRegistry.ANCHOR_TREE.bark.getDefaultState());
		}
	}
	
	private void buildLine(List<BlockPos> blocks, double radius)
	{
		for (int i = 0; i < blocks.size() - 1; i++)
		{
			BlockPos a = blocks.get(i);
			BlockPos b = blocks.get(i + 1);
			if (b.getY() < a.getY())
			{
				BlockPos c = b;
				b = a;
				a = c;
			}
			int count = (int) Math.ceil(Math.sqrt(b.getSquaredDistance(a)));
			for (int j = 0; j < count; j++)
				sphere(lerpCos(a, b, (double) j / count), radius);
		}
	}
	
	private BlockPos lerpCos(BlockPos start, BlockPos end, double mix)
	{
		double v = lcos(mix);
		double x = MathHelper.lerp(v, start.getX(), end.getX());
		double y = MathHelper.lerp(v, start.getY(), end.getY());
		double z = MathHelper.lerp(v, start.getZ(), end.getZ());
		return new BlockPos(x, y, z);
	}
	
	private double lcos(double mix)
	{
		return MathHelper.clamp(0.5 - Math.cos(mix * Math.PI) * 0.5, 0, 1);
	}
	
	private List<BlockPos> lineParable(BlockPos start, BlockPos end, int count, Random random, double range)
	{
		List<BlockPos> result = new ArrayList<BlockPos>(count);
		int max = count - 1;
		int middle = count / 2;
		result.add(start);
		double size = Math.sqrt(start.getSquaredDistance(end)) * 0.8;
		for (int i = 1; i < max; i++)
		{
			double offset = (i - middle) / middle;
			offset = 1 - offset * offset;
			double delta = (double) i / max;
			double x = MathHelper.lerp(delta, start.getX(), end.getX()) + random.nextGaussian() * range;
			double y = MathHelper.lerp(delta, start.getY(), end.getY()) - offset * size;
			double z = MathHelper.lerp(delta, start.getZ(), end.getZ()) + random.nextGaussian() * range;
			result.add(new BlockPos(x, y, z));
		}
		result.add(end);
		return result;
	}
	
	private void sphere(BlockPos pos, double radius)
	{
		int x1 = MHelper.floor(pos.getX() - radius);
		int y1 = MHelper.floor(pos.getY() - radius);
		int z1 = MHelper.floor(pos.getZ() - radius);
		int x2 = MHelper.floor(pos.getX() + radius + 1);
		int y2 = MHelper.floor(pos.getY() + radius + 1);
		int z2 = MHelper.floor(pos.getZ() + radius + 1);
		radius *= radius;
		
		for (int x = x1; x <= x2; x++)
		{
			int px2 = x - pos.getX();
			px2 *= px2;
			for (int z = z1; z <= z2; z++)
			{
				int pz2 = z - pos.getZ();
				pz2 *= pz2;
				for (int y = y1; y <= y2; y++)
				{
					int py2 = y - pos.getY();
					py2 *= py2;
					if (px2 + pz2 + py2 <= radius) BLOCKS.add(new BlockPos(x, y, z));
				}
			}
		}
	}
}
