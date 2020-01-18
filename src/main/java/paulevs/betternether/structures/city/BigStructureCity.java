package paulevs.betternether.structures.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.big.BigStructure;

public class BigStructureCity extends BigStructure
{
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	protected CityGenerator generator;
	protected ArrayList<BuildingInfo> buildings;
	
	public BigStructureCity(BlockPos pos, int cx, int cz, CityGenerator generator, Random random)
	{
		super(pos, cx, cz);
		this.generator = generator;
		buildings = generator.generate(pos, random);
	}
	
	public BigStructureCity(CompoundTag structure, CityGenerator generator)
	{
		super(structure);
		ListTag builds = structure.getList("buildings", 10);
		buildings = new ArrayList<BuildingInfo>();
		for (int i = 0; i < builds.size(); i++)
		{
			buildings.add(new BuildingInfo(builds.getCompound(i), generator.getBuildings()));
		}
	}
	
	@Override
	public CompoundTag toNBT()
	{
		CompoundTag root = super.toNBT();
		ListTag builds = new ListTag();
		root.put("buildings", builds);
		for (BuildingInfo b: buildings)
			builds.add(b.toNBT());
		return root;
	}
	
	@Override
	public boolean setChunk(IWorld world, int cx, int cz)
	{
		if (super.setChunk(world, cx, cz))
		{
			int sx = (cx << 4) | 8;
			int sz = (cz << 4) | 8;
			BlockBox boundingBox = new BlockBox(sx, 0, sz, sx + 15, 256, sz + 15);
			for (BuildingInfo b: buildings)
				b.building.placeInChunk(world, b.pos, boundingBox);
			for (int y = 5; y < 32; y++)
			{
				for (int x = 0; x < 16; x++)
				{
					int wx = x + sx;
					for (int z = 0; z < 16; z++)
					{
						int wz = z + sz;
						BlockPos pos = new BlockPos(wx, y, wz);
						Block b = world.getBlockState(pos).getBlock();
						if (b == Blocks.LAVA)
							BlocksHelper.setWithoutUpdate(world, pos, LAVA);
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public List<BlockPos> getPosList(BlockPos pos)
	{
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		for (BuildingInfo bi: buildings)
		{
			BlockPos center = bi.building.getBoungingBox().getCenter();
			center = center.add(bi.pos).subtract(pos);
			center = center.add(0, 40 - center.getY(), 0);
			positions.add(center);
		}
		return positions;
	}
	
	public List<Integer> getRadiuses()
	{
		ArrayList<Integer> rad = new ArrayList<Integer>();
		for (BuildingInfo bi: buildings)
		{
			int sideX = bi.building.getBoungingBox().getSideX();
			int sideZ = bi.building.getBoungingBox().getSideZ();
			rad.add(Math.max(sideX, sideZ));
		}
		return rad;
	}
	
	public int getCitySide()
	{
		BuildingInfo info = buildings.get(0);
		int minX = info.pos.getX();
		int maxX = info.building.getBoungingBox().getMaxX() + info.pos.getX();
		int minZ = info.pos.getZ();
		int maxZ = info.building.getBoungingBox().getMaxZ() + info.pos.getZ();
		for (BuildingInfo bi: buildings)
		{
			minX = Math.min(minX, bi.pos.getX());
			maxX = Math.max(maxX, bi.building.getBoungingBox().getMaxX() + bi.pos.getX());
			minZ = Math.min(minZ, bi.pos.getZ());
			maxZ = Math.max(maxZ, bi.building.getBoungingBox().getMinZ() + bi.pos.getZ());
		}
		return Math.max(maxX - minX, maxZ - minZ);
	}
}
