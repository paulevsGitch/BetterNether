package paulevs.betternether.structures.decorations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;

public class StructureCrystal implements IStructure
{
	private static final double SQRT05 = Math.sqrt(0.5);
	private static final Mutable POS = new Mutable();
	private static final ArrayList<BlockPos> SHAPE = new ArrayList<BlockPos>();
	private static final float MAX_ANGLE_X = (float) Math.toRadians(45);
	private static final float MAX_ANGLE_Y = (float) (Math.PI * 2);
	
	@Override
	public void generate(IWorld world, BlockPos pos, Random random)
	{
		double a = random.nextDouble();
		double radius = 2 + a * a * 5;
		int sideXZ = (int) Math.ceil(radius * 2);
		int sideY = (int) Math.ceil(radius * 3);
		float angleX = random.nextFloat() * MAX_ANGLE_X;
		float angleY = random.nextFloat() * MAX_ANGLE_Y;
		for (int y = -sideY; y <= sideY; y++)
			for (int x = -sideXZ; x <= sideXZ; x++)
				for (int z = -sideXZ; z <= sideXZ; z++)
				{
					Vec3d v = new Vec3d(x, y, z).rotateX(angleX).rotateY(angleY);
					if (isInside(v.x, v.y, v.z, radius))
					{
						POS.setX(pos.getX() + x);
						POS.setY(pos.getY() + y);
						POS.setZ(pos.getZ() + z);
						//BlockState state = world.getBlockState(POS);
						//if (state.getMaterial().isReplaceable() || state.getBlock() == BlocksRegister.MAGMA_FLOWER)
						{
							BlocksHelper.setWithoutUpdate(world, POS, Blocks.OBSIDIAN.getDefaultState());
						}
					}
				}
	}
	
	private boolean isInside(double x, double y, double z, double size)
	{
		return dodecahedronSDF(x / size, y / size * 0.3, z / size) <= 0;
	}
	
	private double dodecahedronSDF(double x, double y, double z)
	{
		x = Math.abs(x);
		y = Math.abs(y);
		z = Math.abs(z);
	    return (Math.max(Math.max(x + y, y + z), z + x) - 1) * SQRT05;
	}
	
	private void fillShape()
	{
		SHAPE.add(new BlockPos(0, 0, 0));
		boolean add = true;
		while (add)
		{
			add = false;
			Iterator<BlockPos> iterator = SHAPE.iterator();
			while (iterator.hasNext())
			{
				BlockPos origin = iterator.next();
				for (Direction d: Direction.values())
				{
					BlockPos p2 = origin.offset(d);
					
				}
			}
		}
	}
}