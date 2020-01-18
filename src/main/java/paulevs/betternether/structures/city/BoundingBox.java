package paulevs.betternether.structures.city;

import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;

public class BoundingBox
{
	int x1, x2, z1, z2;
	
	public BoundingBox(int x1, int z1, int x2, int z2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.z1 = z1;
		this.z2 = z2;
	}
	
	public BoundingBox(BlockPos size, int offsetX, int offsetZ)
	{
		this.x1 = offsetX;
		this.x2 = x1 + size.getX();
		this.z1 = offsetZ;
		this.z2 = z1 + size.getZ();
	}
	
	public BoundingBox(BlockPos size)
	{
		this.x1 = 0;
		this.x2 = size.getX();
		this.z1 = 0;
		this.z2 = size.getZ();
	}
	
	public boolean isColliding(BoundingBox bb)
	{
		boolean colX = (bb.x1 < x2) && (x1 < bb.x2);
		boolean colZ = (bb.z1 < z2) && (z1 < bb.z2);
		return colX && colZ;
	}

	public BoundingBox offset(BlockPos offset)
	{
		return new BoundingBox(x1 + offset.getX(), z1 + offset.getZ(), x2 + offset.getX(), z2 + offset.getZ());
	}

	public BoundingBox offsetNegative(BlockPos offset)
	{
		return new BoundingBox(x1 - offset.getX(), z1 - offset.getZ(), x2 - offset.getX(), z2 - offset.getZ());
	}
	
	public String toString()
	{
		return x1 + " " + z1 + " " + x2 + " " + z2;
	}
	
	public void rotate(BlockRotation rotation)
	{
		BlockPos start = new BlockPos(x1, 0, z1);
		BlockPos end = new BlockPos(x2, 0, z2);
		start = start.rotate(rotation);
		end = end.rotate(rotation);
		int nx1 = Math.min(start.getX(), end.getX());
		int nx2 = Math.max(start.getX(), end.getX());
		int nz1 = Math.min(start.getZ(), end.getZ());
		int nz2 = Math.max(start.getZ(), end.getZ());
		x1 = 0;
		z1 = 0;
		x2 = nx2 - nx1;
		z2 = nz2 - nz1;
	}

	public BlockPos getCenter()
	{
		return new BlockPos((x2 + x1) * 0.5, 0, (z2 + z1) * 0.5);
	}
	
	public int getSideX()
	{
		return x2 - x1;
	}
	
	public int getSideZ()
	{
		return z2 - z1;
	}
	
	public int getMinX()
	{
		return x1;
	}

	public int getMaxX()
	{
		return x2;
	}
	
	public int getMinZ()
	{
		return z1;
	}

	public int getMaxZ()
	{
		return z2;
	}
}
