package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;
import net.minecraft.world.biome.BiomeEffects.Builder;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import paulevs.betternether.BetterNether;
import paulevs.betternether.MHelper;
import paulevs.betternether.config.Config;

public class BiomeDefenition
{
	private static final Random random = new Random();
	
	private String name;
	private String group;
	private int fogColor;
	private SoundEvent loop;
	private BiomeMoodSound mood;
	private BiomeAdditionsSound additions;
	private MixedNoisePoint noise;
	private BiomeParticleConfig particleConfig;
	private SoundEvent music;
	
	public BiomeDefenition(String name)
	{
		this(name, BetterNether.MOD_ID);
	}
	
	public BiomeDefenition(String name, String group)
	{
		this.name = name.toLowerCase().replace(' ', '_');
		this.group = group;
		random.setSeed(name.hashCode());
		noise = new Biome.MixedNoisePoint(
				random.nextFloat(),
				random.nextFloat(),
				random.nextFloat(),
				random.nextFloat() * 2 - 1, 1.0F);
	}
	
	/**
	 * Sets biome fog color
	 * @param r - Red [0 - 255]
	 * @param g - Green [0 - 255]
	 * @param b - Blue [0 - 255]
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setFogColor(int r, int g, int b)
	{
		String path = "generator.biome.betternether." + name + ".fog_color";
		r = MathHelper.clamp(Config.getInt(path, "red", r), 0, 255);
		g = MathHelper.clamp(Config.getInt(path, "green", g), 0, 255);
		b = MathHelper.clamp(Config.getInt(path, "blue", b), 0, 255);
		this.fogColor = MHelper.color(r, g, b);
		return this;
	}
	
	/**
	 * Plays in never-ending loop for as long as player is in the biome
	 * @param loop - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setLoop(SoundEvent loop)
	{
		this.loop = loop;
		return this;
	}
	
	/**
	 * Plays commonly while the player is in the biome
	 * @param mood - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setMood(SoundEvent mood)
	{
		this.mood = new BiomeMoodSound(mood, 6000, 8, 2.0D);
		return this;
	}
	
	/**
	 * Plays once every 6000-17999 ticks while the player is in the biome
	 * @param additions - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefenition setAdditions(SoundEvent additions)
	{
		this.additions = new BiomeAdditionsSound(additions, 0.0111);
		return this;
	}
	
	public BiomeDefenition setMusic(SoundEvent music)
	{
		this.music = music;
		return this;
	}
	
	/*public Settings buildBiomeSettings()
	{
		Builder effects = new Builder()
				.waterColor(4159204)
				.waterFogColor(329011)
				.fogColor(fogColor);
		if (loop != null)
			effects.loopSound(loop);
		if (mood != null)
			effects.moodSound(mood);
		if (additions != null)
			effects.additionsSound(additions);
		if (particleConfig != null)
			effects.particleConfig(particleConfig);
		effects.music(MusicType.method_27283(music != null ? music : SoundEvents.MUSIC_NETHER_WARPED_FOREST));
		
		return new Settings()
				.configureSurfaceBuilder(SurfaceBuilder.NETHER, SurfaceBuilder.NETHER_CONFIG)
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.NETHER)
				.depth(0.1F)
				.scale(0.2F)
				.temperature(2.0F)
				.downfall(0.0F)
				.effects(effects.build())
				.parent((String) null)
				.noises(ImmutableList.of(noise));
	}*/
	
	public Biome build()
	{
		SpawnSettings spawnSettings = (new SpawnSettings.Builder())
				.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GHAST, 50, 4, 4))
				.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4))
				.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 2, 4, 4))
				.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 1, 4, 4))
				.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.PIGLIN, 15, 4, 4))
				.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.STRIDER, 60, 1, 2)).build();
		GenerationSettings.Builder builder = (new GenerationSettings.Builder())
				.surfaceBuilder(ConfiguredSurfaceBuilders.NETHER)
				.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL_NETHER)
				.structureFeature(ConfiguredStructureFeatures.FORTRESS)
				.structureFeature(ConfiguredStructureFeatures.BASTION_REMNANT)
				.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE)
				.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.SPRING_LAVA);
		DefaultBiomeFeatures.addDefaultMushrooms(builder);
		builder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_OPEN)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_FIRE)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.PATCH_SOUL_FIRE)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE_EXTRA)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.GLOWSTONE)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.BROWN_MUSHROOM_NETHER)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.RED_MUSHROOM_NETHER)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.ORE_MAGMA)
				.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ConfiguredFeatures.SPRING_CLOSED);
		DefaultBiomeFeatures.addNetherMineables(builder);
		
		Builder effects = new Builder()
				.skyColor(fogColor)
				.waterColor(4159204)
				.waterFogColor(329011)
				.fogColor(fogColor);
		if (loop != null)
			effects.loopSound(loop);
		if (mood != null)
			effects.moodSound(mood);
		if (additions != null)
			effects.additionsSound(additions);
		if (particleConfig != null)
			effects.particleConfig(particleConfig);
		effects.music(MusicType.createIngameMusic(music != null ? music : SoundEvents.MUSIC_NETHER_WARPED_FOREST));
		
		return new Biome.Builder()
				.precipitation(Biome.Precipitation.NONE).category(Biome.Category.NETHER).depth(0.1F)
				.scale(0.2F).temperature(2.0F).downfall(0.0F)
				.effects(effects.build())
				.spawnSettings(spawnSettings)
				.generationSettings(builder.build())
				.build();
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getGroup()
	{
		return group;
	}
	
	public BiomeDefenition setParticleConfig(BiomeParticleConfig config)
	{
		this.particleConfig = config;
		return this;
	}
}
