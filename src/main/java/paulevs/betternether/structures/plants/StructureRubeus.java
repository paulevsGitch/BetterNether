package paulevs.betternether.structures.plants;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureFuncScatter;

public class StructureRubeus extends StructureFuncScatter
{
	private static final float[] CURVE_X = new float[] {9F, 5F, 1.5F, 0.5F, 3F, 7F};
	private static final float[] CURVE_Y = new float[] {20F, 17F, 12F, 4F, 1F, -2F};
	private static final Set<BlockPos> POINTS = new HashSet<BlockPos>();
	
	public StructureRubeus()
	{
		super(7);
	}
	
	@Override
	public void grow(IWorld world, BlockPos pos, Random random)
	{
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 0);
		float scale = MHelper.randRange(0.5F, 1F, random);
		int minCount = scale < 0.75 ? 3 : 4;
		int maxCount = scale < 0.75 ? 5 : 7;
		int count = MHelper.randRange(minCount, maxCount, random);
		for (int n = 0; n < count; n++)
		{
			float branchSize = MHelper.randRange(0.5F, 1F, random) * scale;
			float angle = n * MHelper.PI2 / count;
			float radius = CURVE_X[0] * branchSize;
			int x1 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			int y1 = Math.round(pos.getY() + CURVE_Y[0] * branchSize + MHelper.randRange(-2F, 2F, random) * branchSize);
			int z1 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
			crown(world, x1, y1 + 1, z1, 5 * branchSize, random, BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
			
			for (int i = 1; i < CURVE_X.length; i++)
			{
				radius = CURVE_X[i] * branchSize;
				int x2 = Math.round(pos.getX() + radius * (float) Math.cos(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
				int y2 = Math.round(pos.getY() + CURVE_Y[i] * branchSize + (CURVE_Y[i] > 0 ? MHelper.randRange(-2F, 2F, random) * branchSize : 0));
				int z2 = Math.round(pos.getZ() + radius * (float) Math.sin(angle) + MHelper.randRange(-2F, 2F, random) * branchSize);
				line(world, x1, y1, z1, x2, y2, z2);
				x1 = x2;
				y1 = y2;
				z1 = z2;
			}
		}
		
		for (BlockPos bpos: POINTS)
		{
			if (POINTS.contains(bpos.up()) && POINTS.contains(bpos.down()))
				setCondition(world, bpos, pos.getY(), BlocksRegistry.RUBEUS_LOG.getDefaultState());
			else
				setCondition(world, bpos, pos.getY(), BlocksRegistry.RUBEUS_BARK.getDefaultState());
		}
		
		POINTS.clear();
	}
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		int length = BlocksHelper.upRay(world, pos, StructureStalagnate.MAX_LENGTH + 2);
		if (length >= StructureStalagnate.MAX_LENGTH)
			super.generate(world, pos, random);
	}

	@Override
	protected boolean isStructure(BlockState state)
	{
		return state.getBlock() == BlocksRegistry.RUBEUS_LOG;
	}

	@Override
	protected boolean isGround(BlockState state)
	{
		return BlocksHelper.isNetherGround(state);
	}
	
	private void line(IWorld world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int dx = x2 - x1;
		int dy = y2 - y1;
		int dz = z2 - z1;
		int mx = Math.max(Math.max(Math.abs(dx), Math.abs(dy)), Math.abs(dz));
		float fdx = (float) dx / mx;
		float fdy = (float) dy / mx;
		float fdz = (float) dz / mx;
		float px = x1;
		float py = y1;
		float pz = z1;
		POINTS.add(POS.set(x1, y1, z1).toImmutable());
		POINTS.add(POS.set(x2, y2, z2).toImmutable());
		//int y = POS.getY();
		for (int i = 0; i < mx; i++)
		{
			px += fdx;
			py += fdy;
			pz += fdz;
			POS.set(Math.round(px), Math.round(py), Math.round(pz));
			POINTS.add(POS.toImmutable());
			/*if (y > POS.getY())
			{
				y = POS.getY();
				POINTS.add(POS.down());
			}
			else if (y < POS.getY())
			{
				y = POS.getY();
				POINTS.add(POS.up());
			}*/
		}
	}
	
	private void crown(IWorld world, int x, int y, int z, float radius, Random random, BlockState leaves)
	{
		float halfR = radius * 0.5F;
		float r2 = radius * radius;
		int start = (int) Math.floor(- radius);
		for (int cy = start; cy <= radius; cy++)
		{
			int cy2_out = cy * cy;
			float cy2_in = cy + halfR;
			cy2_in *= cy2_in;
			POS.setY((int) (y + cy - halfR));
			for (int cx = start; cx <= radius; cx++)
			{
				int cx2 = cx * cx;
				POS.setX(x + cx);
				for (int cz = start; cz <= radius; cz++)
				{
					int cz2 = cz * cz;
					if (cx2 + cy2_out + cz2 < r2 && cx2 + cy2_in + cz2 > r2)
					{
						POS.setZ(z + cz);
						setIfAirLeaves(world, POS, leaves);
					}
				}
			}
		}
	}
	
	private void setCondition(IWorld world, BlockPos pos, int y, BlockState state)
	{
		if (pos.getY() > y)
			setIfAir(world, pos, state);
		else
			setIfGroundOrAir(world, pos, state);
	}
	
	private void setIfAir(IWorld world, BlockPos pos, BlockState state)
	{
		BlockState bState = world.getBlockState(pos);
		if (world.isAir(pos) || bState.getBlock() == BlocksRegistry.RUBEUS_LEAVES || bState.getMaterial().isReplaceable())
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
	
	private void setIfGroundOrAir(IWorld world, BlockPos pos, BlockState state)
	{
		BlockState bState = world.getBlockState(pos);
		if (bState.isAir() || bState.getBlock() == BlocksRegistry.RUBEUS_LEAVES || bState.getMaterial().isReplaceable() || BlocksHelper.isNetherGround(bState))
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
	
	private void setIfAirLeaves(IWorld world, BlockPos pos, BlockState state)
	{
		BlockState bState = world.getBlockState(pos);
		if (world.isAir(pos) || bState.getMaterial().isReplaceable())
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
}
