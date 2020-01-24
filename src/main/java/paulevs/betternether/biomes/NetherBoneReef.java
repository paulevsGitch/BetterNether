package paulevs.betternether.biomes;

import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.bones.StructureBoneReef;
import paulevs.betternether.structures.plants.StructureNetherGrass;

public class NetherBoneReef extends NetherBiome
{
	public NetherBoneReef(String name)
	{
		super(name);
		addStructure("bone_reef", new StructureBoneReef(), StructureType.FLOOR, 0.2F, false);
		addStructure("nether_grass", new StructureNetherGrass(), StructureType.FLOOR, 0.4F, false);
	}
}
