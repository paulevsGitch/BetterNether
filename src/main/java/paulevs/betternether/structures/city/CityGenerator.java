package paulevs.betternether.structures.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CityGenerator
{
	private List<StructureCityBuilding> buildings;
	private List<StructureCityBuilding> roadEnds;
	private List<BoundingBox> bounds;
	private List<BlockPos> ends;
	private List<BlockPos> add;
	private List<BlockPos> rem;
	
	public CityGenerator()
	{
		buildings = new ArrayList<StructureCityBuilding>();
		roadEnds = new ArrayList<StructureCityBuilding>();
		bounds = new ArrayList<BoundingBox>();
		ends = new ArrayList<BlockPos>();
		add = new ArrayList<BlockPos>();
		rem = new ArrayList<BlockPos>();
		
		addBuildingToList("city_center", -10);
		addBuildingToList("city_library_01");
		addBuildingToList("city_building_01");
		addBuildingToList("city_building_02");
		addBuildingToList("city_building_03");
		addBuildingToList("city_building_04");
		addBuildingToList("city_building_05");
		addBuildingToList("city_building_06");
		addBuildingToList("city_enchanter_01");
		
		addRoadEndToList("road_end_01");
		addRoadEndToList("road_end_02", -2);
	}
	
	private void addBuildingToList(String name)
	{
		StructureCityBuilding building = new StructureCityBuilding("city/" + name);
		buildings.add(building);
		buildings.add(building.getRotated(Rotation.CLOCKWISE_90));
		buildings.add(building.getRotated(Rotation.CLOCKWISE_180));
		buildings.add(building.getRotated(Rotation.COUNTERCLOCKWISE_90));
	}
	
	private void addBuildingToList(String name, int offsetY)
	{
		StructureCityBuilding building = new StructureCityBuilding("city/" + name, offsetY);
		buildings.add(building);
		buildings.add(building.getRotated(Rotation.CLOCKWISE_90));
		buildings.add(building.getRotated(Rotation.CLOCKWISE_180));
		buildings.add(building.getRotated(Rotation.COUNTERCLOCKWISE_90));
	}
	
	private void addRoadEndToList(String name)
	{
		StructureCityBuilding building = new StructureCityBuilding("city/" + name);
		roadEnds.add(building);
		roadEnds.add(building.getRotated(Rotation.CLOCKWISE_90));
		roadEnds.add(building.getRotated(Rotation.CLOCKWISE_180));
		roadEnds.add(building.getRotated(Rotation.COUNTERCLOCKWISE_90));
	}
	
	private void addRoadEndToList(String name, int offsetY)
	{
		StructureCityBuilding building = new StructureCityBuilding("city/" + name, offsetY);
		roadEnds.add(building);
		roadEnds.add(building.getRotated(Rotation.CLOCKWISE_90));
		roadEnds.add(building.getRotated(Rotation.CLOCKWISE_180));
		roadEnds.add(building.getRotated(Rotation.COUNTERCLOCKWISE_90));
	}
	
	private void placeCenterBuilding(World world, BlockPos pos, StructureCityBuilding building)
	{
		BoundingBox bb = building.getBoungingBox().offset(pos);
		bounds.add(bb);
		building.place(world, pos.add(0, building.getYOffset(), 0));
		for (int i = 0; i < building.getEndsCount(); i++)
			ends.add(pos.add(building.getOffsettedPos(i).add(0, building.getYOffset(), 0)));
	}
	
	private void attachBuildings(World world, Random random)
	{
		for (BlockPos pos : ends)
		{
			for (int n = 0; n < 8; n++)
			{
				StructureCityBuilding building = buildings.get(4 + random.nextInt(buildings.size() - 4));
				int index = random.nextInt(building.getEndsCount());
				BlockPos offset = building.getPos(index);
				BoundingBox bb = building.getBoungingBox().offset(pos).offsetNegative(offset);
				if (noCollisions(bb))
				{
					BlockPos npos = new BlockPos(bb.x1, pos.getY() - offset.getY() + building.getYOffset(), bb.z1);
					bounds.add(bb);
					rem.add(pos);
					for (int i = 0; i < building.getEndsCount(); i++)
						if (i != index)
							add.add(npos.add(building.getOffsettedPos(i)));
					building.place(world, npos);
					break;
				}
			}
		}
		ends.removeAll(rem);
		ends.addAll(add);
		rem.clear();
		add.clear();
	}
	
	private void closeRoads(World world)
	{
		for (BlockPos pos : ends)
		{
			for (int n = 0; n < roadEnds.size(); n++)
			{
				StructureCityBuilding building = roadEnds.get(n);
				BlockPos offset = building.getPos(0);
				BoundingBox bb = building.getBoungingBox().offset(pos).offsetNegative(offset);
				if (noCollisions(bb))
				{
					BlockPos npos = new BlockPos(bb.x1, pos.getY() - offset.getY() + building.getYOffset(), bb.z1);
					bounds.add(bb);
					building.place(world, npos);
					break;
				}
			}
		}
		ends.clear();
		bounds.clear();
		rem.clear();
		add.clear();
	}
	
	public void generate(World world, BlockPos pos, Random random)
	{
		placeCenterBuilding(world, pos, buildings.get(random.nextInt(4)));
		for (int i = 0; i < 2 + random.nextInt(4); i++)
			attachBuildings(world, random);
		closeRoads(world);
	}
	
	private boolean noCollisions(BoundingBox bb)
	{
		for (BoundingBox b : bounds)
			if (bb.isColliding(b))
				return false;
		return true;
	}
}
