package paulevs.betternether.biomes;

import net.minecraft.sound.SoundEvents;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.bones.StructureBoneReef;
import paulevs.betternether.structures.plants.StructureNetherGrass;

public class NetherBoneReef extends NetherBiome
{
	public NetherBoneReef(String name)
	{
		super(new BiomeDefenition(name)
				.setColor(228, 65, 53)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD));
		addStructure("bone_reef", new StructureBoneReef(), StructureType.FLOOR, 0.2F, true);
		addStructure("nether_grass", new StructureNetherGrass(), StructureType.FLOOR, 0.4F, false);
	}
}
