package paulevs.betternether.registry;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import paulevs.betternether.BetterNether;
import paulevs.betternether.biomes.BiomeDefinition;
import paulevs.betternether.config.Configs;
import paulevs.betternether.mixin.common.BiomeGenerationSettingsAccessor;
import paulevs.betternether.world.features.BlockFixFeature;
import paulevs.betternether.world.features.CavesFeature;
import paulevs.betternether.world.features.CleanupFeature;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.features.PathsFeature;
import ru.bclib.api.BiomeAPI;
import ru.bclib.world.biomes.BCLBiomeDef;
import ru.bclib.world.features.BCLFeature;
import ru.bclib.world.features.DefaultFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NetherFeatures {
	// Ores //
	public static final BCLFeature CINCINNASITE_ORE = registerOre("cincinnasite", NetherBlocks.CINCINNASITE_ORE, 9, 8, 5, 128);
	public static final BCLFeature NETHER_RUBY_ORE = registerOre("nether_ruby", NetherBlocks.NETHER_RUBY_ORE, 5, 3, 5, 64);
	public static final BCLFeature NETHER_LAPIS_ORE = registerOre("nether_lapis", NetherBlocks.NETHER_LAPIS_ORE, 18, 4, 32, 128);
	public static final BCLFeature NETHER_REDSTONE_ORE = registerOre("nether_redstone", NetherBlocks.NETHER_REDSTONE_ORE, 2, 16, 32, 80);
	
	// Maintainance //
	public static final BCLFeature CLEANUP_FEATURE =registerChunkFeature("nether_clean", Decoration.RAW_GENERATION, CleanupFeature::new);
	public static final BCLFeature FIX_FEATURE =registerChunkFeature("nether_fix", Decoration.TOP_LAYER_MODIFICATION, BlockFixFeature::new);
	
	// Terrain //
	public static final BCLFeature CAVES_FEATURE =registerChunkFeature("nether_caves", Decoration.UNDERGROUND_STRUCTURES, CavesFeature::new);
	public static final BCLFeature PATHS_FEATURE =registerChunkFeature("nether_paths", Decoration.SURFACE_STRUCTURES, PathsFeature::new);
	public static final BCLFeature POPULATOR_FEATURE =registerChunkFeature("nether_populator", Decoration.SURFACE_STRUCTURES, NetherChunkPopulatorFeature::new);
	
	// Cached Config data //
	public static final boolean HAS_CLEANING_PASS = Configs.GENERATOR.getBoolean("generator.world.terrain", "terrain_cleaning_pass", true);
	public static final boolean HAS_CAVES = Configs.GENERATOR.getBoolean("generator.world.environment", "generate_caves", true);
	public static final boolean HAS_PATHS = Configs.GENERATOR.getBoolean("generator.world.environment", "generate_paths", true);
	public static final boolean HAS_FIXING_PASS = Configs.GENERATOR.getBoolean("generator.world.terrain", "world_fixing_pass", true);
	
	private static <T extends DefaultFeature> BCLFeature registerChunkFeature(String name, Decoration step, Supplier<T> feature){
		return BCLFeature.makeChunkFeature(
			BetterNether.makeID("feature_" + name),
			step,
			feature.get()
		);
	}
	
	private static BCLFeature registerOre(String name, Block blockOre, int veins, int veinSize, int minY, int maxY){
		return _registerOre(
			name+"_ore",
			blockOre,
			Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_count", veins),
			Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_size", veinSize),
			Configs.GENERATOR.getInt("generator.world.ores." + name, "minY", minY),
			Configs.GENERATOR.getInt("generator.world.ores." + name, "maxY", maxY)
		);
	}
	
	private static BCLFeature _registerOre(String name, Block blockOre, int veins, int veinSize,  int minY, int maxY) {
		return BCLFeature.makeOreFeature(BetterNether.makeID(name), blockOre, Blocks.NETHERRACK, veins, veinSize, minY, maxY);
	}
	
	private static void addFeature(BCLFeature feature, List<List<Supplier<ConfiguredFeature<?, ?>>>> features) {
		int index = feature.getFeatureStep().ordinal();
		if (features.size() > index) {
			features.get(index).add(() -> feature.getFeatureConfigured());
		}
		else {
			List<Supplier<ConfiguredFeature<?, ?>>> newFeature = Lists.newArrayList();
			newFeature.add(() -> feature.getFeatureConfigured());
			features.add(newFeature);
		}
	}
	
	public static void addDefaultFeatures(BCLBiomeDef def) {
		if (NetherFeatures.HAS_CLEANING_PASS) {
			def.addFeature(CLEANUP_FEATURE);
		}
		if (NetherFeatures.HAS_CAVES){
			def.addFeature(CAVES_FEATURE);
		}
		if (NetherFeatures.HAS_PATHS){
			def.addFeature(PATHS_FEATURE);
		}
		
		def.addFeature(POPULATOR_FEATURE);
		
		def.addFeature(CINCINNASITE_ORE);
		def.addFeature(NETHER_RUBY_ORE);
		def.addFeature(NETHER_LAPIS_ORE);
		def.addFeature(NETHER_REDSTONE_ORE);
		
		if (NetherFeatures.HAS_FIXING_PASS){
			def.addFeature(FIX_FEATURE);
		}
	}
	
	private static void registerBiomeFeatures(List<List<Supplier<ConfiguredFeature<?, ?>>>> features) {
		if (NetherFeatures.HAS_CAVES){
			addFeature(CAVES_FEATURE, features);
		}
		if (NetherFeatures.HAS_PATHS){
			addFeature(PATHS_FEATURE, features);
		}
		
		addFeature(POPULATOR_FEATURE, features);
		
		addFeature(CINCINNASITE_ORE, features);
		addFeature(NETHER_RUBY_ORE, features);
		addFeature(NETHER_LAPIS_ORE, features);
		addFeature(NETHER_REDSTONE_ORE, features);
	}
	
	public static void modifyNonBNBiome(Biome biome) {
		BiomeGenerationSettingsAccessor accessor = (BiomeGenerationSettingsAccessor) biome.getGenerationSettings();
		List<List<Supplier<ConfiguredFeature<?, ?>>>> preFeatures = accessor.be_getFeatures();
		List<List<Supplier<ConfiguredFeature<?, ?>>>> features = new ArrayList<>(preFeatures.size());
		preFeatures.forEach((list) -> features.add(Lists.newArrayList(list)));
		
		registerBiomeFeatures(features);
		
		accessor.be_setFeatures(features);
	}
	
	public static void register() {
	
	}
}
