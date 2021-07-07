package paulevs.betternether.biomes;

import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects.Builder;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import com.google.common.collect.Lists;
import paulevs.betternether.BetterNether;
import paulevs.betternether.MHelper;
import paulevs.betternether.config.Configs;

public class BiomeDefinition {
	private final List<ConfiguredStructureFeature<?, ?>> structures = Lists.newArrayList();
	private final List<FeatureInfo> features = Lists.newArrayList();
	private final List<SpawnInfo> mobs = Lists.newArrayList();

	private AmbientParticleSettings particleConfig;
	private AmbientAdditionsSettings additions;
	private AmbientMoodSettings mood;
	private SoundEvent music;
	private SoundEvent loop;

	private int waterFogColor = 329011;
	private int waterColor = 4159204;
	private int fogColor = 3344392;

	private boolean defaultOres = true;
	private boolean defaultMobs = true;
	private boolean defaultFeatures = true;
	private boolean defaultStructureFeatures = true;
	private boolean stalactites = true;
	private boolean bnStructures = true;

	private final ResourceLocation id;

	public BiomeDefinition(String name) {
		this.id = new ResourceLocation(BetterNether.MOD_ID, name.replace(' ', '_').toLowerCase());
	}

	public BiomeDefinition(ResourceLocation id) {
		this.id = id;
	}

	public BiomeDefinition setStalactites(boolean value) {
		stalactites = value;
		return this;
	}

	public BiomeDefinition setBNStructures(boolean value) {
		bnStructures = value;
		return this;
	}

	/**
	 * Set default ores generation
	 * 
	 * @param value
	 *            - if true (default) then default ores will be generated
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultOres(boolean value) {
		defaultOres = value;
		return this;
	}

	/**
	 * Set default nether structure features to be added
	 * 
	 * @param value
	 *            - if true (default) then default structure features (nether
	 *            fortresses, caves, etc.) will be added into biome
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultStructureFeatures(boolean value) {
		defaultStructureFeatures = value;
		return this;
	}

	/**
	 * Set default nether features to be added
	 * 
	 * @param value
	 *            - if true (default) then default features (small structures)
	 *            will be added into biome
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultFeatures(boolean value) {
		defaultFeatures = value;
		return this;
	}

	/**
	 * Set default Nether Wastes mobs to be added
	 * 
	 * @param value
	 *            - if true (default) then default mobs will be added into biome
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setDefaultMobs(boolean value) {
		defaultMobs = value;
		return this;
	}

	public BiomeDefinition setParticleConfig(AmbientParticleSettings config) {
		this.particleConfig = config;
		return this;
	}

	/**
	 * Adds mob into biome
	 * 
	 * @param type
	 *            - {@link EntityType}
	 * @param weight
	 *            - cumulative spawning weight
	 * @param minGroupSize
	 *            - minimum count of mobs in the group
	 * @param maxGroupSize
	 *            - maximum count of mobs in the group
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition addMobSpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
		ResourceLocation eID = Registry.ENTITY_TYPE.getKey(type);
		if (eID != Registry.ENTITY_TYPE.getDefaultKey()) {
			String path = "generator.biome." + id.getNamespace() + "." + id.getPath() + ".mobs." + eID.getNamespace() + "." + eID.getPath();
			SpawnInfo info = new SpawnInfo();
			info.type = type;
			info.weight = Configs.BIOMES.getInt(path, "weight", weight);
			info.minGroupSize = Configs.BIOMES.getInt(path, "min_group_size", minGroupSize);
			info.maxGroupSize = Configs.BIOMES.getInt(path, "max_group_size", maxGroupSize);
			mobs.add(info);
		}
		return this;
	}

	/**
	 * Adds feature (small structure) into biome - plants, ores, small
	 * buildings, etc.
	 * 
	 * @param feature
	 *            - {@link ConfiguredStructureFeature} to add
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition addStructureFeature(ConfiguredStructureFeature<?, ?> feature) {
		structures.add(feature);
		return this;
	}

	public BiomeDefinition addFeature(Decoration featureStep, ConfiguredFeature<?, ?> feature) {
		FeatureInfo info = new FeatureInfo();
		info.featureStep = featureStep;
		info.feature = feature;
		features.add(info);
		return this;
	}

	/**
	 * Sets biome fog color
	 * 
	 * @param r
	 *            - Red [0 - 255]
	 * @param g
	 *            - Green [0 - 255]
	 * @param b
	 *            - Blue [0 - 255]
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setFogColor(int r, int g, int b) {
		String path = "generator.biome." + id.getNamespace() + "." + id.getPath() + ".fog_color";
		r = Mth.clamp(Configs.BIOMES.getInt(path, "red", r), 0, 255);
		g = Mth.clamp(Configs.BIOMES.getInt(path, "green", g), 0, 255);
		b = Mth.clamp(Configs.BIOMES.getInt(path, "blue", b), 0, 255);
		this.fogColor = MHelper.color(r, g, b);
		return this;
	}

	/**
	 * Sets biome water color
	 * 
	 * @param r
	 *            - Red [0 - 255]
	 * @param g
	 *            - Green [0 - 255]
	 * @param b
	 *            - Blue [0 - 255]
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setWaterColor(int r, int g, int b) {
		String path = "generator.biome." + id.getNamespace() + "." + id.getPath() + ".water_color";
		r = Mth.clamp(Configs.BIOMES.getInt(path, "red", r), 0, 255);
		g = Mth.clamp(Configs.BIOMES.getInt(path, "green", g), 0, 255);
		b = Mth.clamp(Configs.BIOMES.getInt(path, "blue", b), 0, 255);
		this.waterColor = MHelper.color(r, g, b);
		return this;
	}

	/**
	 * Sets biome underwater fog color
	 * 
	 * @param r
	 *            - Red [0 - 255]
	 * @param g
	 *            - Green [0 - 255]
	 * @param b
	 *            - Blue [0 - 255]
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setWaterFogColor(int r, int g, int b) {
		String path = "generator.biome." + id.getNamespace() + "." + id.getPath() + ".water_fog_color";
		r = Mth.clamp(Configs.BIOMES.getInt(path, "red", r), 0, 255);
		g = Mth.clamp(Configs.BIOMES.getInt(path, "green", g), 0, 255);
		b = Mth.clamp(Configs.BIOMES.getInt(path, "blue", b), 0, 255);
		this.waterFogColor = MHelper.color(r, g, b);
		return this;
	}

	/**
	 * Plays in never-ending loop for as long as player is in the biome
	 * 
	 * @param loop
	 *            - SoundEvent
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setLoop(SoundEvent loop) {
		this.loop = loop;
		return this;
	}

	/**
	 * Plays commonly while the player is in the biome
	 * 
	 * @param mood
	 *            - SoundEvent
	 * @return this {@link BiomeDefinition}
	 */
	public BiomeDefinition setMood(SoundEvent mood) {
		this.mood = new AmbientMoodSettings(mood, 6000, 8, 2.0D);
		return this;
	}

	/**
	 * Set additional sounds. They plays once every 6000-17999 ticks while the
	 * player is in the biome
	 * 
	 * @param additions
	 *            - SoundEvent
	 * @return this BiomeDefenition
	 */
	public BiomeDefinition setAdditions(SoundEvent additions) {
		this.additions = new AmbientAdditionsSettings(additions, 0.0111);
		return this;
	}

	/**
	 * Set background music for biome
	 * 
	 * @param music
	 * @return
	 */
	public BiomeDefinition setMusic(SoundEvent music) {
		this.music = music;
		return this;
	}

	public Biome build() {
		MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
		BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
		Builder effects = new Builder();

		if (defaultMobs) addDefaultMobs(spawnSettings);
		mobs.forEach((spawn) -> {
			spawnSettings.addSpawn(spawn.type.getCategory(), new MobSpawnSettings.SpawnerData(spawn.type, spawn.weight, spawn.minGroupSize, spawn.maxGroupSize));
		});

		generationSettings.surfaceBuilder(SurfaceBuilders.NETHER);
		structures.forEach((structure) -> generationSettings.addStructureStart(structure));
		features.forEach((info) -> generationSettings.addFeature(info.featureStep, info.feature));
		if (defaultOres) BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
		if (defaultStructureFeatures) addDefaultStructures(generationSettings);
		if (defaultFeatures) addDefaultFeatures(generationSettings);

		effects.skyColor(fogColor).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(fogColor);
		if (loop != null) effects.ambientLoopSound(loop);
		if (mood != null) effects.ambientMoodSound(mood);
		if (additions != null) effects.ambientAdditionsSound(additions);
		if (particleConfig != null) effects.ambientParticle(particleConfig);
		effects.backgroundMusic(Musics.createGameMusic(music != null ? music : SoundEvents.MUSIC_BIOME_NETHER_WASTES));

		return new Biome.BiomeBuilder()
				.precipitation(Precipitation.NONE)
				.biomeCategory(BiomeCategory.NETHER)
				.depth(0.1F)
				.scale(0.2F)
				.temperature(2.0F)
				.downfall(0.0F)
				.specialEffects(effects.build())
				.mobSpawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build())
				.build();
	}

	private void addDefaultStructures(BiomeGenerationSettings.Builder generationSettings) {
		generationSettings.addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER);
		generationSettings.addStructureStart(StructureFeatures.NETHER_BRIDGE);
		generationSettings.addStructureStart(StructureFeatures.BASTION_REMNANT);
		generationSettings.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
		generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
	}

	private void addDefaultFeatures(BiomeGenerationSettings.Builder generationSettings) {
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.BROWN_MUSHROOM_NETHER);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.RED_MUSHROOM_NETHER);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA);
		generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED);
		BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
		BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
	}

	private void addDefaultMobs(MobSpawnSettings.Builder spawnSettings) {
		spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4));
		spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4));
		spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 2, 4, 4));
		spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4));
		spawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 15, 4, 4));
		spawnSettings.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));
	}

	private static final class SpawnInfo {
		EntityType<?> type;
		int weight;
		int minGroupSize;
		int maxGroupSize;
	}

	private static final class FeatureInfo {
		Decoration featureStep;
		ConfiguredFeature<?, ?> feature;
	}

	public ResourceLocation getID() {
		return id;
	}

	public boolean hasStalactites() {
		return stalactites;
	}

	public boolean hasBNStructures() {
		return bnStructures;
	}
}