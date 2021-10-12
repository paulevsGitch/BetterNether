package paulevs.betternether.biomes;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.interfaces.IStructureFeatures;
import ru.bclib.world.biomes.BCLBiomeDef;

public class BiomeDefinition extends BCLBiomeDef {
	private boolean defaultOres = true;
	private boolean defaultMobs = true;
	private boolean defaultFeatures = true;
	private boolean defaultStructureFeatures = true;
	private boolean stalactites = true;
	private boolean bnStructures = true;

	public BiomeDefinition(String name) {
		this(new ResourceLocation(BetterNether.MOD_ID, name.replace(' ', '_').toLowerCase()));
	}

	public BiomeDefinition(ResourceLocation id) {
		super(id);
		netherBiome();
		setSurface(SurfaceBuilders.NETHER);
		setTemperature(2F);
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
	@Override
	public BiomeDefinition addMobSpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
		ResourceLocation eID = Registry.ENTITY_TYPE.getKey(type);
		if (eID != Registry.ENTITY_TYPE.getDefaultKey()) {
			String path = "generator.biome." + getID().getNamespace() + "." + getID().getPath() + ".mobs." + eID.getNamespace() + "." + eID.getPath();
			weight = Configs.BIOMES.getInt(path, "weight", weight);
			minGroupSize = Configs.BIOMES.getInt(path, "min_group_size", minGroupSize);
			maxGroupSize = Configs.BIOMES.getInt(path, "max_group_size", maxGroupSize);
			super.addMobSpawn(type, weight, minGroupSize, maxGroupSize);
		}
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
	@Override
	public BiomeDefinition setFogColor(int r, int g, int b) {
		String path = "generator.biome." + getID().getNamespace() + "." + getID().getPath() + ".fog_color";
		r = Mth.clamp(Configs.BIOMES.getInt(path, "red", r), 0, 255);
		g = Mth.clamp(Configs.BIOMES.getInt(path, "green", g), 0, 255);
		b = Mth.clamp(Configs.BIOMES.getInt(path, "blue", b), 0, 255);
		super.setFogColor(r, g, b);
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
	@Override
	public BiomeDefinition setWaterColor(int r, int g, int b) {
		String path = "generator.biome." + getID().getNamespace() + "." + getID().getPath() + ".water_color";
		r = Mth.clamp(Configs.BIOMES.getInt(path, "red", r), 0, 255);
		g = Mth.clamp(Configs.BIOMES.getInt(path, "green", g), 0, 255);
		b = Mth.clamp(Configs.BIOMES.getInt(path, "blue", b), 0, 255);
		super.setWaterColor(r, g, b);
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
	@Override
	public BiomeDefinition setWaterFogColor(int r, int g, int b) {
		String path = "generator.biome." + getID().getNamespace() + "." + getID().getPath() + ".water_fog_color";
		r = Mth.clamp(Configs.BIOMES.getInt(path, "red", r), 0, 255);
		g = Mth.clamp(Configs.BIOMES.getInt(path, "green", g), 0, 255);
		b = Mth.clamp(Configs.BIOMES.getInt(path, "blue", b), 0, 255);
		super.setWaterFogColor(r, g, b);
		return this;
	}
	
	@Override
	protected void addCustomToBuild(BiomeGenerationSettings.Builder generationSettings) {
		
		if (defaultOres) BiomeDefaultFeatures.addNetherDefaultOres(generationSettings);
		if (defaultStructureFeatures) addDefaultStructures(generationSettings);
		if (defaultFeatures) addDefaultFeatures(generationSettings);
	}

	public boolean didAddDefaultSpaw = false;
	public Biome build() {
		if (!didAddDefaultSpaw && defaultMobs){
			didAddDefaultSpaw = true;
			addDefaultMobs();
		}
		return super.build();
	}

	private void addDefaultStructures(BiomeGenerationSettings.Builder generationSettings) {
		IStructureFeatures sf = (IStructureFeatures)(Object)structureFeatures;
		addStructureFeature(sf.getRUINED_PORTAL_NETHER());
		addStructureFeature(sf.getNETHER_BRIDGE());
		addStructureFeature(sf.getBASTION_REMNANT());
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

	private void addDefaultMobs() {
		addMobSpawn(EntityType.GHAST, 50, 4, 4);
		addMobSpawn(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4);
		addMobSpawn(EntityType.MAGMA_CUBE, 2, 4, 4);
		addMobSpawn(EntityType.ENDERMAN, 1, 4, 4);
		addMobSpawn(EntityType.PIGLIN, 15, 4, 4);
		addMobSpawn(EntityType.STRIDER, 60, 1, 2);
	}

	public boolean hasStalactites() {
		return stalactites;
	}

	public boolean hasBNStructures() {
		return bnStructures;
	}
	
	@Override
	public BiomeDefinition setLoop(SoundEvent loop) {
		super.setLoop(loop);
		return this;
	}
	
	@Override
	public BiomeDefinition setMood(SoundEvent mood) {
		super.setMood(mood);
		return this;
	}
	
	@Override
	public BiomeDefinition setAdditions(SoundEvent additions) {
		super.setAdditions(additions);
		return this;
	}
	
	@Override
	public BiomeDefinition setMusic(SoundEvent music) {
		super.setMusic(music);
		return this;
	}
	
	@Override
	public BiomeDefinition setParticles(ParticleOptions particle, float probability) {
		super.setParticles(particle, probability);
		return this;
	}
	
	@Override
	public BiomeDefinition setGenChance(float genChance) {
		super.setGenChance(genChance);
		return this;
	}
	
	@Override
	public BiomeDefinition setSurface(Block block){
		super.setSurface(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderBaseConfiguration(
			block.defaultBlockState(),
			Blocks.NETHERRACK.defaultBlockState(),
			Blocks.NETHERRACK.defaultBlockState()
		)));
		return this;
	}
}