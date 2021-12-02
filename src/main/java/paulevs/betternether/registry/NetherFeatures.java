package paulevs.betternether.registry;

import com.google.common.collect.Lists;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.features.BlockFixFeature;
import paulevs.betternether.world.features.CavesFeature;
import paulevs.betternether.world.features.CleanupFeature;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.features.PathsFeature;
import paulevs.betternether.world.structures.CityFeature;
import ru.bclib.api.LifeCycleAPI;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BiomeAPI;
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
	
	private static void addFeature(BCLFeature feature, List<List<Supplier<PlacedFeature>>> features) {
		int index = feature.getDecoration().ordinal();
		if (features.size() > index) {
			features.get(index).add(() -> feature.getPlacedFeature());
		}
		else {
			List<Supplier<PlacedFeature>> newFeature = Lists.newArrayList();
			newFeature.add(() -> feature.getPlacedFeature());
			features.add(newFeature);
		}
	}
	
	public static BCLBiomeBuilder addDefaultFeatures(BCLBiomeBuilder builder) {
		if (NetherFeatures.HAS_CLEANING_PASS) {
			builder.feature(CLEANUP_FEATURE);
		}
		if (NetherFeatures.HAS_CAVES){
			builder.feature(CAVES_FEATURE);
		}
		if (NetherFeatures.HAS_PATHS){
			builder.feature(PATHS_FEATURE);
		}
		
		builder
			.feature(POPULATOR_FEATURE)
			.feature(CINCINNASITE_ORE)
			.feature(NETHER_RUBY_ORE)
			.feature(NETHER_LAPIS_ORE)
			.feature(NETHER_REDSTONE_ORE);
		
		if (NetherFeatures.HAS_FIXING_PASS){
			builder.feature(FIX_FEATURE);
		}
		
		return builder;
	}
	
	public static void modifyNonBNBiome(Biome biome) {
		if (NetherFeatures.HAS_CAVES){
			BiomeAPI.addBiomeFeature(biome, CAVES_FEATURE);
		}
		if (NetherFeatures.HAS_PATHS){
			BiomeAPI.addBiomeFeature(biome, PATHS_FEATURE);
		}
		
		BiomeAPI.addBiomeFeature(biome, POPULATOR_FEATURE);
		
		BiomeAPI.addBiomeFeature(biome, CINCINNASITE_ORE);
		BiomeAPI.addBiomeFeature(biome, NETHER_RUBY_ORE);
		BiomeAPI.addBiomeFeature(biome, NETHER_LAPIS_ORE);
		BiomeAPI.addBiomeFeature(biome, NETHER_REDSTONE_ORE);
	}
	
	public static void register() {
		LifeCycleAPI.onLevelLoad(NetherFeatures::onWorldLoad);
	}
	
	public static void onWorldLoad(ServerLevel level, long seed, Registry<Biome> registry) {
		CavesFeature.onLoad(seed);
		PathsFeature.onLoad(seed);
		
		CityFeature.initGenerator();
	}
}
