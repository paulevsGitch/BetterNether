package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;

public class StructureWorld extends StructureNBT implements IStructure
{
	protected static final Mutable POS = new Mutable();
	protected final StructureType type;
	protected final int offsetY;
	
	public StructureWorld(String structure, int offsetY, StructureType type)
	{
		super(structure);
		this.offsetY = offsetY;
		this.type = type;
	}
	
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random)
	{
		randomRM(random);
		if (canGenerate(world, pos))
		{
			generateCentered(world, pos.up(offsetY));
		}
	}

	private boolean canGenerate(WorldAccess world, BlockPos pos)
	{
		if (type == StructureType.FLOOR)
			return getAirFraction(world, pos) > 0.6 && getAirFractionFoundation(world, pos) < 0.5;
		else if (type == StructureType.LAVA)
			return getLavaFractionFoundation(world, pos) > 0.9 && getAirFraction(world, pos) > 0.9;
		else if (type == StructureType.UNDER)
			return getAirFraction(world, pos) < 0.2;
		else if (type == StructureType.CEIL)
			return getAirFractionBottom(world, pos) > 0.7 && getAirFraction(world, pos) < 0.6;
		else
			return false;
	}

	private float getAirFraction(WorldAccess world, BlockPos pos)
	{
		int airCount = 0;
		
		Mutable size = new Mutable().set(structure.getSize().rotate(rotation));
		size.setX(Math.abs(size.getX()));
		size.setZ(Math.abs(size.getZ()));
		
		BlockPos start = pos.add(-(size.getX() >> 1), 0, -(size.getZ() >> 1));
		BlockPos end = pos.add(size.getX() >> 1, size.getY() + offsetY, size.getZ() >> 1);
		int count = 0;
		
		for (int x = start.getX(); x <= end.getX(); x++)
		{
			POS.setX(x);
			for (int y = start.getY(); y <= end.getY(); y++)
			{
				POS.setY(y);
				for (int z = start.getZ(); z <= end.getZ(); z++)
				{
					POS.setZ(z);
					if (world.isAir(POS))
						airCount ++;
					count ++;
				}
			}
		}
		
		return (float) airCount / count;
	}
	
	private float getLavaFractionFoundation(WorldAccess world, BlockPos pos)
	{
		int lavaCount = 0;
		
		Mutable size = new Mutable().set(structure.getSize().rotate(rotation));
		size.setX(Math.abs(size.getX()));
		size.setZ(Math.abs(size.getZ()));
		
		BlockPos start = pos.add(-(size.getX() >> 1), 0, -(size.getZ() >> 1));
		BlockPos end = pos.add(size.getX() >> 1, 0, size.getZ() >> 1);
		int count = 0;
		
		POS.setY(pos.getY() - 1);
		for (int x = start.getX(); x <= end.getX(); x++)
		{
			POS.setX(x);
			for (int z = start.getZ(); z <= end.getZ(); z++)
			{
				POS.setZ(z);
				if (BlocksHelper.isLava(world.getBlockState(POS)))
					lavaCount ++;
				count ++;
			}
		}
		
		return (float) lavaCount / count;
	}
	
	private float getAirFractionFoundation(WorldAccess world, BlockPos pos)
	{
		int airCount = 0;
		
		Mutable size = new Mutable().set(structure.getSize().rotate(rotation));
		size.setX(Math.abs(size.getX()));
		size.setZ(Math.abs(size.getZ()));
		
		BlockPos start = pos.add(-(size.getX() >> 1), -1, -(size.getZ() >> 1));
		BlockPos end = pos.add(size.getX() >> 1, 0, size.getZ() >> 1);
		int count = 0;
		
		for (int x = start.getX(); x <= end.getX(); x++)
		{
			POS.setX(x);
			for (int y = start.getY(); y <= end.getY(); y++)
			{
				POS.setY(y);
				for (int z = start.getZ(); z <= end.getZ(); z++)
				{
					POS.setZ(z);
					if (world.getBlockState(POS).getMaterial().isReplaceable())
						airCount ++;
					count ++;
				}
			}
		}
		
		return (float) airCount / count;
	}
	
	private float getAirFractionBottom(WorldAccess world, BlockPos pos)
	{
		int airCount = 0;
		
		Mutable size = new Mutable().set(structure.getSize().rotate(rotation));
		size.setX(Math.abs(size.getX()));
		size.setZ(Math.abs(size.getZ()));
		
		float y1 = Math.min(offsetY, 0);
		float y2 = Math.max(offsetY, 0);
		BlockPos start = pos.add(-(size.getX() >> 1), y1, -(size.getZ() >> 1));
		BlockPos end = pos.add(size.getX() >> 1, y2, size.getZ() >> 1);
		int count = 0;
		
		for (int x = start.getX(); x <= end.getX(); x++)
		{
			POS.setX(x);
			for (int y = start.getY(); y <= end.getY(); y++)
			{
				POS.setY(y);
				for (int z = start.getZ(); z <= end.getZ(); z++)
				{
					POS.setZ(z);
					if (world.getBlockState(POS).getMaterial().isReplaceable())
						airCount ++;
					count ++;
				}
			}
		}
		
		return (float) airCount / count;
	}

	public boolean loaded()
	{
		return structure != null;
	}

	public StructureType getType()
	{
		return type;
	}
}
