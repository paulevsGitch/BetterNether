package paulevs.betternether.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;

public class StructureBuilding extends StructureNBT implements IStructureWorld
{
	private Mutable posMut = new Mutable();
	private int offsetY;
	
	public StructureBuilding(String structure, int offsetY)
	{
		super(structure);
		this.offsetY = offsetY;
	}
	
	@Override
	public void randomize(Random random)
	{
		this.randomRM(random);
	}
	
	@Override
	public void generate(IWorld world, BlockPos pos)
	{
		generateCentered(world, pos.up(offsetY));
	}
	
	@Override
	public float getAirFraction(IWorld world, BlockPos pos)
	{
		int airCount = 0;
		
		Mutable size = new Mutable(structure.getSize().rotate(rotation));
		size.setX(Math.abs(size.getX()));
		size.setZ(Math.abs(size.getZ()));
		
		BlockPos start = pos.add(-size.getX() >> 1, 0, -size.getZ() >> 1);
		BlockPos end = pos.add(size.getX() >> 1, size.getY() + offsetY, size.getZ() >> 1);
		
		for (int x = start.getX(); x < end.getX(); x++)
			for (int y = start.getY(); y < end.getY(); y++)
				for (int z = start.getZ(); z < end.getZ(); z++)
				{
					posMut.set(x, y, z);
					if (world.isAir(posMut))
						airCount ++;
				}
		return (float) airCount / (size.getX() * size.getY() * size.getZ());
	}
	
	@Override
	public float getAirFractionBottom(IWorld world, BlockPos pos)
	{
		int airCount = 0;
		
		Mutable size = new Mutable(structure.getSize().rotate(rotation));
		size.setX(Math.abs(size.getX()));
		size.setZ(Math.abs(size.getZ()));
		
		BlockPos start = pos.add(-size.getX() >> 1, -2, -size.getZ() >> 1);
		BlockPos end = pos.add(size.getX() >> 1, 0, size.getZ() >> 1);
		
		for (int x = start.getX(); x < end.getX(); x++)
			for (int y = start.getY(); y < end.getY(); y++)
				for (int z = start.getZ(); z < end.getZ(); z++)
				{
					posMut.set(x, y, z);
					if (world.isAir(posMut))
						airCount ++;
				}
		return (float) airCount / (size.getX() * size.getY() * size.getZ());
	}
}
