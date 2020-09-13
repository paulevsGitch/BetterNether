package paulevs.betternether.biomes;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.BiomeParticleConfig;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;

public class UpsideDownForest extends NetherBiome
{
	public UpsideDownForest(String name)
	{
		super(new BiomeDefinition(name)
				.setFogColor(111, 188, 111)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setMusic(SoundEvents.MUSIC_NETHER_CRIMSON_FOREST)
				.setParticleConfig(new BiomeParticleConfig(ParticleTypes.MYCELIUM, 0.1F)));
		this.setNoiseDensity(0.5F);
		addStructure("large_red_mushroom", new StructureMedRedMushroom(), StructureType.FLOOR, 0.12F, true);
	}
}
