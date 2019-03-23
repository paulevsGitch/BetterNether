package paulevs.betternether.structures.city;

import java.util.Map;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.Template;
import paulevs.betternether.structures.StructureNBT;

public class StructureCityBuilding extends StructureNBT
{
	private BoundingBox bb;
	private BlockPos[] ends;
	private EnumFacing[] dirs;
	private BlockPos rotationOffset;
	private Rotation rotation;
	private int offsetY;
	
	public StructureCityBuilding(String structure)
	{
		super(structure);
		this.offsetY = 0;
		init();
	}
	
	public StructureCityBuilding(String structure, int offsetY)
	{
		super(structure);
		this.offsetY = offsetY;
		init();
	}
	
	protected StructureCityBuilding(ResourceLocation location, Template template)
	{
		super(location, template);
		init();
	}
	
	private void init()
	{
		BlockPos size = template.getSize();
		bb = new BoundingBox(size);
		Map<BlockPos, String> map = template.getDataBlocks(BlockPos.ORIGIN, DEFAULT_SETTINGS);
		ends = new BlockPos[map.size()];
		dirs = new EnumFacing[map.size()];
		int i = 0;
		BlockPos center = new BlockPos(size.getX() >> 1, size.getY(), size.getZ() >> 1);
		for(BlockPos pos : map.keySet())
		{
			ends[i] = pos;
			dirs[i++] = getDir(pos.add(-center.getX(), 0, -center.getZ()));
		}
		rotationOffset = new BlockPos(0, 0, 0);
		rotation = Rotation.NONE;
	}
	
	private EnumFacing getDir(BlockPos pos)
	{
		int ax = Math.abs(pos.getX());
		int az = Math.abs(pos.getZ());
		int mx = Math.max(ax, az);
		if (mx == ax)
		{
			if (pos.getX() > 0)
				return EnumFacing.EAST;
			else
				return EnumFacing.WEST;
		}
		else
		{
			if (pos.getZ() > 0)
				return EnumFacing.SOUTH;
			else
				return EnumFacing.NORTH;
		}
	}
	
	/*private EnumFacing getDir(BlockPos pos)
	{
		if (pos.getX() == 0)
		{
			return EnumFacing.WEST;
		}
		else if (pos.getX() > 0)
		{
			return EnumFacing.WEST;
		}
	}*/

	public BoundingBox getBoungingBox()
	{
		return bb;
	}
	
	public void place(World world, BlockPos pos)
	{
		BlockPos p = pos.add(rotationOffset);
		template.addBlocksToWorld(world, p, DEFAULT_SETTINGS.setRotation(rotation));
		for (BlockPos rep : ends)
		{
			world.setBlockState(rep.add(pos), Blocks.AIR.getDefaultState());
		}
		IBlockState state;
		int d;
		for (int x = 0; x < bb.x2; x++)
			for (int z = 0; z < bb.z2; z++)
			{
				p = pos.add(x, 0, z);
				state = world.getBlockState(p);
				if (state.isFullBlock())
				{
					for (d = 1; d < pos.getY() - 5; d++)
					{
						if (world.getBlockState(p.down(d)).isFullBlock())
							break;
					}
					for (int y = 1; y < d; y++)
					{
						world.setBlockState(p.down(y), state);
					}
				}
			}
	}

	public BlockPos[] getEnds()
	{
		return ends;
	}
	
	public int getEndsCount()
	{
		return ends.length;
	}
	
	public BlockPos getOffsettedPos(int index)
	{
		return ends[index].offset(dirs[index]);
	}

	public BlockPos getPos(int index)
	{
		return ends[index];
	}
	
	public StructureCityBuilding getRotated(Rotation rotation)
	{
		StructureCityBuilding building = this.clone();
		building.rotation = rotation;
		building.rotationOffset = building.template.getSize().rotate(rotation);
		int x = building.rotationOffset.getX();
		int z = building.rotationOffset.getZ();
		if (x < 0)
			x = -x - 1;
		else
			x = 0;
		if (z < 0)
			z = -z - 1;
		else
			z = 0;
		building.rotationOffset = new BlockPos(x, 0, z);
		for (int i = 0; i < building.dirs.length; i++)
		{
			building.dirs[i] = rotated(building.dirs[i], rotation);
			building.ends[i] = building.ends[i].rotate(rotation).add(building.rotationOffset);
		}
		building.bb.rotate(rotation);
		building.offsetY = this.offsetY;
		return building;
	}
	
	public StructureCityBuilding getRandomRotated(Random random)
	{
		return getRotated(Rotation.values()[random.nextInt(Rotation.values().length)]);
	}
	
	public StructureCityBuilding clone()
	{
		return new StructureCityBuilding(location, template);
	}
	
	private EnumFacing rotated(EnumFacing dir, Rotation rotation)
	{
		EnumFacing f;
		switch (rotation)
		{
		case CLOCKWISE_90:
			f = dir.rotateY();
			break;
		case CLOCKWISE_180:
			f = dir.getOpposite();
			break;
		case COUNTERCLOCKWISE_90:
			f = dir.rotateYCCW();
			break;
		default:
			f = dir;
			break;
		}
		return f;
	}
	
	public int getYOffset()
	{
		return offsetY;
	}
}
