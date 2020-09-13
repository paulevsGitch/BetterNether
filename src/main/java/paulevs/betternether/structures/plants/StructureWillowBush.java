package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherGrass;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureWillowBush implements IStructure
{
	private static final Mutable POS = new Mutable();
	
	public StructureWillowBush() {}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (!world.isAir(pos) || !world.isAir(pos.up())|| !world.isAir(pos.up(15)))
			return;
		
		float r = random.nextFloat() * 2F + 0.5F;
		int count = (int) r;
		
		for (int i = 0; i < count; i++)
		{
			float fr = r - i;
			int ir = (int) Math.ceil(fr);
			float r2 = fr * fr;
			
			int x1 = pos.getX() - ir;
			int x2 = pos.getX() + ir;
			int z1 = pos.getZ() - ir;
			int z2 = pos.getZ() + ir;
			
			POS.setY(pos.getY() + i);
			
			for (int x = x1; x < x2; x++)
			{
				POS.setX(x);
				int sqx = x - pos.getX();
				sqx *= sqx;
				for (int z = z1; z < z2; z++)
				{
					int sqz = z - pos.getZ();
					sqz *= sqz;
					POS.setZ(z);
					if (sqx + sqz < r2 + random.nextFloat() * r)
					{
						int dx = POS.getX() - pos.getX();
						int dy = POS.getY() - pos.getY();
						int dz = POS.getZ() - pos.getZ();
						int ax = Math.abs(dx);
						int ay = Math.abs(dy);
						int az = Math.abs(dz);
						int max = Math.max(ax, Math.max(ay, az));
						Direction dir;
						if (max == ax) dir = Direction.from(Axis.X, dx > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						else if (max == ay) dir = Direction.from(Axis.Y, dy > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						else dir = Direction.from(Axis.Z, dz > 0 ? AxisDirection.POSITIVE : AxisDirection.NEGATIVE);
						setIfAir(world, POS, BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, dir));
					}
				}
			}
		}
		
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.WILLOW_BARK.getDefaultState());
		setIfAir(world, pos.up(), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.UP));
		setIfAir(world, pos.north(), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.NORTH));
		setIfAir(world, pos.south(), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.SOUTH));
		setIfAir(world, pos.east(), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.EAST));
		setIfAir(world, pos.west(), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.WEST));
	}
	
	private void setIfAir(WorldAccess world, BlockPos pos, BlockState state)
	{
		if (world.isAir(pos) || world.getBlockState(pos).getBlock() instanceof BlockNetherGrass)
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
}
