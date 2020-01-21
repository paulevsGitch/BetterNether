package paulevs.betternether.biomes;

import paulevs.betternether.structures.plants.StructureSoulLily;

public class NetherSoulForest extends NetherSoulPlain
{
	public NetherSoulForest(String name)
	{
		super(name);
		addStructure("soul_lily", new StructureSoulLily(), StructureType.FLOOR, 0.5F, false);
	}
}
