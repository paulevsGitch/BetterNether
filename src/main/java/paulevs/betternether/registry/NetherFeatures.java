package paulevs.betternether.registry;

import com.google.common.collect.Lists;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.features.BlockFixFeature;
import paulevs.betternether.world.features.CavesFeature;
import paulevs.betternether.world.features.CleanupFeature;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.features.PathsFeature;
import paulevs.betternether.world.structures.city.CityFeature;
import ru.bclib.api.LifeCycleAPI;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.world.features.BCLFeature;
import ru.bclib.world.features.DefaultFeature;

import java.util.List;
import java.util.function.Supplier;

public class NetherFeatures {
	// Ores //
	public static final BCLFeature CINCINNASITE_ORE =
		registerOre("cincinnasite", NetherBlocks.CINCINNASITE_ORE,
			10, 8,
			PlacementUtils.RANGE_10_10,
			false);

	public static final BCLFeature NETHER_RUBY_ORE =
		registerOre("nether_ruby", NetherBlocks.NETHER_RUBY_ORE,
			5, 8,
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(32)),
			false);

	public static final BCLFeature NETHER_RUBY_ORE_TOP =
		registerOre("nether_ruby_top", NetherBlocks.NETHER_RUBY_ORE,
			3, 6,
			HeightRangePlacement.triangle(VerticalAnchor.belowTop(30), VerticalAnchor.top()),
			true);

	public static final BCLFeature NETHER_LAPIS_ORE =
		registerOre("nether_lapis", NetherBlocks.NETHER_LAPIS_ORE,
			18, 4,
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.belowTop(10)),
			false);

	public static final BCLFeature NETHER_REDSTONE_ORE =
		registerOre("nether_redstone", NetherBlocks.NETHER_REDSTONE_ORE,
			1, 16,
			HeightRangePlacement.triangle(VerticalAnchor.absolute(8), VerticalAnchor.absolute(32)),
			false);

	// Maintainance //
	public static final BCLFeature CLEANUP_FEATURE = registerChunkFeature("nether_clean", Decoration.RAW_GENERATION, CleanupFeature::new);
	public static final BCLFeature FIX_FEATURE = registerChunkFeature("nether_fix", Decoration.TOP_LAYER_MODIFICATION, BlockFixFeature::new);

	// Terrain //
	public static final BCLFeature CAVES_FEATURE = registerChunkFeature("nether_caves", Decoration.UNDERGROUND_STRUCTURES, CavesFeature::new);
	public static final BCLFeature PATHS_FEATURE = registerChunkFeature("nether_paths", Decoration.LAKES, PathsFeature::new);
	public static final BCLFeature POPULATOR_FEATURE = registerChunkFeature("nether_populator", Decoration.VEGETAL_DECORATION, NetherChunkPopulatorFeature::new);

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
	
	private static BCLFeature registerOre(String name, Block blockOre, int veins, int veinSize, PlacementModifier placement, boolean rare){
		return _registerOre(
			name+"_ore",
			blockOre,
			Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_count", veins),
			Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_size", veinSize),
			placement,
			rare
		);
	}
	
	private static BCLFeature _registerOre(String name, Block blockOre, int veins, int veinSize, PlacementModifier placementModifier, boolean rare) {
		return BCLFeature.makeOreFeature(
			BetterNether.makeID(name),
			blockOre,
			Blocks.NETHERRACK,
			veins,
			veinSize,
			placementModifier,
			rare);
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
		if (NetherFeatures.HAS_CLEANING_PASS) builder.feature(CLEANUP_FEATURE);
		if (NetherFeatures.HAS_CAVES) builder.feature(CAVES_FEATURE);
		if (NetherFeatures.HAS_PATHS) builder.feature(PATHS_FEATURE);
		if (NetherFeatures.HAS_FIXING_PASS) builder.feature(FIX_FEATURE);

		builder.feature(POPULATOR_FEATURE);
		
		return builder;
	}
	
	public static BCLBiomeBuilder addDefaultOres(BCLBiomeBuilder builder) {
		return builder
			   .feature(CINCINNASITE_ORE)
			   .feature(NETHER_RUBY_ORE)
			   .feature(NETHER_RUBY_ORE_TOP)
			   .feature(NETHER_LAPIS_ORE)
			   .feature(NETHER_REDSTONE_ORE);
	}
	
	public static void modifyNonBNBiome(ResourceLocation biomeID, Biome biome) {
		if (NetherFeatures.HAS_CAVES){
			BiomeAPI.addBiomeFeature(biome, CAVES_FEATURE);
		}
		if (NetherFeatures.HAS_PATHS){
			BiomeAPI.addBiomeFeature(biome, PATHS_FEATURE);
		}

		//BiomeAPI.addBiomeFeature(biome, POPULATOR_FEATURE);

		BiomeAPI.addBiomeFeature(biome, CINCINNASITE_ORE);
		BiomeAPI.addBiomeFeature(biome, NETHER_RUBY_ORE_TOP);
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
