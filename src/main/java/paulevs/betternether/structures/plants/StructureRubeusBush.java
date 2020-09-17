package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.RubeusLog;
import paulevs.betternether.blocks.shapes.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureRubeusBush implements IStructure
{
	private static final Mutable POS = new Mutable();
	
	public StructureRubeusBush()
	{
		
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		if (!world.isAir(pos) || !world.isAir(pos.up())|| !world.isAir(pos.up(15)))
			return;
		
		float r = random.nextFloat() * 3 + 1;
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
						setIfAir(world, POS, BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
					}
				}
			}
		}
		
		BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.RUBEUS_BARK.getDefaultState().with(RubeusLog.SHAPE, TripleShape.MIDDLE));
		setIfAir(world, pos.up(), BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
		setIfAir(world, pos.north(), BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
		setIfAir(world, pos.south(), BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
		setIfAir(world, pos.east(), BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
		setIfAir(world, pos.west(), BlocksRegistry.RUBEUS_LEAVES.getDefaultState());
	}
	
	private void setIfAir(WorldAccess world, BlockPos pos, BlockState state)
	{
		if (world.isAir(pos))
			BlocksHelper.setWithoutUpdate(world, pos, state);
	}
}