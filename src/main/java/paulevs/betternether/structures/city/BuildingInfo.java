package paulevs.betternether.structures.city;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;

public class BuildingInfo
{
	public StructureCityBuilding building;
	public BlockPos pos;

	public BuildingInfo(StructureCityBuilding building, BlockPos pos)
	{
		this.building = building;
		this.pos = pos;
	}

	public BuildingInfo(CompoundTag root, List<StructureCityBuilding> buildings)
	{
		int[] prePos = root.getIntArray("pos");
		int preRot = root.getInt("rotation");
		String name = root.getString("name");
		
		pos = new BlockPos(prePos[0], prePos[1], prePos[2]);
		BlockRotation rot = BlockRotation.values()[preRot];
		for (StructureCityBuilding b: buildings)
			if (b.getName() == name && b.getRotation() == rot)
			{
				building = b;
				return;
			}
		if (building == null)
			building = new StructureCityBuilding(name).getRotated(rot);
	}
	
	public CompoundTag toNBT()
	{
		CompoundTag root = new CompoundTag();
		root.putIntArray("pos", new int[] {pos.getX(), pos.getY(), pos.getZ()});
		root.putString("name", building.getName());
		root.putInt("rotation", building.getRotation().ordinal());
		return root;
	}
}