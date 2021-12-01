package paulevs.betternether.registry;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import paulevs.betternether.BetterNether;
import paulevs.betternether.biomes.CrimsonGlowingWoods;
import paulevs.betternether.biomes.CrimsonPinewood;
import paulevs.betternether.biomes.FloodedDeltas;
import paulevs.betternether.biomes.NetherBiomeData;
import paulevs.betternether.biomes.NetherBoneReefData;
import paulevs.betternether.biomes.NetherGrasslandsData;
import paulevs.betternether.biomes.NetherGravelDesertData;
import paulevs.betternether.biomes.NetherJungleData;
import paulevs.betternether.biomes.NetherMagmaLandData;
import paulevs.betternether.biomes.NetherMushroomForestData;
import paulevs.betternether.biomes.NetherMushroomForestEdgeData;
import paulevs.betternether.biomes.NetherPoorGrasslandsData;
import paulevs.betternether.biomes.NetherSoulPlainData;
import paulevs.betternether.biomes.NetherSulfuricBoneReefData;
import paulevs.betternether.biomes.NetherSwamplandData;
import paulevs.betternether.biomes.NetherSwamplandTerracesData;
import paulevs.betternether.biomes.NetherWartForestData;
import paulevs.betternether.biomes.NetherWartForestEdgeData;
import paulevs.betternether.biomes.OldFungiwoods;
import paulevs.betternether.biomes.OldSwampland;
import paulevs.betternether.biomes.OldWarpedWoods;
import paulevs.betternether.biomes.UpsideDownForest;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.features.CavesFeature;
import paulevs.betternether.world.features.PathsFeature;
import paulevs.betternether.world.structures.CityFeature;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.api.LifeCycleAPI;
import ru.bclib.world.biomes.BCLBiome;

import java.util.ArrayList;
import java.util.Random;

public class NetherBiomes {
	private static final ArrayList<BCLBiome> REGISTRY = new ArrayList<>();
	private static final ArrayList<BCLBiome> ALL_BIOMES = new ArrayList<>();

	private static class Data {
		public static final NetherBiomeData BIOME_GRAVEL_DESERT = new NetherGravelDesertData("Gravel Desert");
		public static final NetherBiomeData BIOME_NETHER_JUNGLE = new NetherJungleData("Nether Jungle");
		public static final NetherBiomeData BIOME_WART_FOREST = new NetherWartForestData("Wart Forest");
		public static final NetherBiomeData BIOME_GRASSLANDS = new NetherGrasslandsData("Nether Grasslands");
		public static final NetherBiomeData BIOME_MUSHROOM_FOREST = new NetherMushroomForestData("Nether Mushroom Forest");
		public static final NetherBiomeData BIOME_MUSHROOM_FOREST_EDGE = new NetherMushroomForestEdgeData("Nether Mushroom Forest Edge");
		public static final NetherBiomeData BIOME_WART_FOREST_EDGE = new NetherWartForestEdgeData("Wart Forest Edge");
		public static final NetherBiomeData BIOME_BONE_REEF = new NetherBoneReefData("Bone Reef");
		public static final NetherBiomeData BIOME_SULFURIC_BONE_REEF = new NetherSulfuricBoneReefData("Sulfuric Bone Reef");
		public static final NetherBiomeData BIOME_POOR_GRASSLANDS = new NetherPoorGrasslandsData("Poor Nether Grasslands");
		public static final NetherBiomeData NETHER_SWAMPLAND = new NetherSwamplandData("Nether Swampland");
		public static final NetherBiomeData NETHER_SWAMPLAND_TERRACES = new NetherSwamplandTerracesData("Nether Swampland Terraces");
		public static final NetherBiomeData MAGMA_LAND = new NetherMagmaLandData("Magma Land");
		public static final NetherBiomeData SOUL_PLAIN = new NetherSoulPlainData("Soul Plain");
		public static final NetherBiomeData CRIMSON_GLOWING_WOODS = new CrimsonGlowingWoods("Crimson Glowing Woods");
		public static final NetherBiomeData OLD_WARPED_WOODS = new OldWarpedWoods("Old Warped Woods");
		public static final NetherBiomeData CRIMSON_PINEWOOD = new CrimsonPinewood("Crimson Pinewood");
		public static final NetherBiomeData OLD_FUNGIWOODS = new OldFungiwoods("Old Fungiwoods");
		public static final NetherBiomeData FLOODED_DELTAS = new FloodedDeltas("Flooded Deltas");
		public static final NetherBiomeData UPSIDE_DOWN_FOREST = new UpsideDownForest("Upside Down Forest");
		public static final NetherBiomeData OLD_SWAMPLAND = new OldSwampland("Old Swampland");
	}
	
	public static final BCLBiome BIOME_GRAVEL_DESERT = NetherBiomeData.create(Data.BIOME_GRAVEL_DESERT);
	public static final BCLBiome BIOME_NETHER_JUNGLE = NetherBiomeData.create(Data.BIOME_NETHER_JUNGLE);

	private static int maxDefChance = 0;
	private static int maxChance = 0;
	private static int biomeID = 7000;

	public static void register() {
		BuiltinRegistries.BIOME.forEach((biome) -> {
			if (biome.getBiomeCategory() == BiomeCategory.NETHER) {
				ResourceLocation id = BuiltinRegistries.BIOME.getKey(biome);
				Configs.GENERATOR.getFloat("biomes." + id.getNamespace() + ".main", id.getPath() + "_chance", 1);
			}
		});
		
		registerNetherBiome(BIOME_GRAVEL_DESERT);
		registerNetherBiome(BIOME_NETHER_JUNGLE);
//		registerNetherBiome(BIOME_WART_FOREST);
//		registerNetherBiome(BIOME_GRASSLANDS);
//		registerNetherBiome(BIOME_MUSHROOM_FOREST);
//		registerEdgeBiome(BIOME_MUSHROOM_FOREST_EDGE, BIOME_MUSHROOM_FOREST, 2);
//		registerEdgeBiome(BIOME_WART_FOREST_EDGE, BIOME_WART_FOREST, 2);
//		registerNetherBiome(BIOME_BONE_REEF);
//		registerSubBiome(BIOME_SULFURIC_BONE_REEF, BIOME_BONE_REEF, 0.3F);
//		registerSubBiome(BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS, 0.3F);
//		registerNetherBiome(NETHER_SWAMPLAND);
//		registerSubBiome(NETHER_SWAMPLAND_TERRACES, NETHER_SWAMPLAND, 1F);
//		registerNetherBiome(MAGMA_LAND);
//		registerSubBiome(SOUL_PLAIN, BIOME_WART_FOREST, 1F);
//		registerSubBiome(CRIMSON_GLOWING_WOODS, BiomeAPI.CRIMSON_FOREST_BIOME, 0.3F);
//		registerSubBiome(OLD_WARPED_WOODS, BiomeAPI.WARPED_FOREST_BIOME, 1F);
//		registerSubBiome(CRIMSON_PINEWOOD, BiomeAPI.CRIMSON_FOREST_BIOME, 0.3F);
//		registerSubBiome(OLD_FUNGIWOODS, BIOME_MUSHROOM_FOREST, 0.3F);
//		registerSubBiome(FLOODED_DELTAS, BiomeAPI.BASALT_DELTAS_BIOME, 1F);
//		registerNetherBiome(UPSIDE_DOWN_FOREST);
//		registerSubBiome(OLD_SWAMPLAND, NETHER_SWAMPLAND, 1F);
		
		RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, id, biome) -> {
			if (biome.getBiomeCategory() == BiomeCategory.NETHER) {
				ResourceLocation bioID = BuiltinRegistries.BIOME.getKey(biome);
				Configs.GENERATOR.getFloat("biomes." + bioID.getNamespace() + ".main", bioID.getPath() + "_chance", 1);
			}
		});
		
		BiomeAPI.registerNetherBiomeModification((biomeID, biome) -> {
			if (!biomeID.getNamespace().equals(BetterNether.MOD_ID)) {
				modifyNonBNBiome(biome);
				NetherFeatures.modifyNonBNBiome(biome);
				NetherStructures.modifyNonBNBiome(biome);
			}
		});

		LifeCycleAPI.onLevelLoad(NetherBiomes::onWorldLoad);
	}
	
	private static void modifyNonBNBiome(Biome biome) {
		BiomeAPI.addBiomeMobSpawn(biome, EntityRegistry.FIREFLY, 5, 3, 6);
		BiomeAPI.addBiomeMobSpawn(biome, EntityRegistry.HYDROGEN_JELLYFISH, 5, 2, 5);
		BiomeAPI.addBiomeMobSpawn(biome, EntityRegistry.NAGA, 8, 3, 5);
	}
	
	
	private static void registerNetherBiome(BCLBiome biome) {
		float chance = Configs.GENERATOR.getFloat("biomes." + biome.getID().getNamespace() + ".main", biome.getID().getPath() + "_chance", 1);
		if (chance > 0.0F) {
			maxChance += chance;
			REGISTRY.add(biome);
			ALL_BIOMES.add(biome);
			
			Biome b = BuiltinRegistries.BIOME.get(biome.getID());
			if (b==null) {
				BiomeAPI.registerNetherBiome(biome);
			}
		}
	}
	
	private static void registerEdgeBiome(BCLBiome biome, BCLBiome mainBiome, int size) {
		final NetherBiomeData data = NetherBiomeData.getCustomNetherData(biome);
		final String regName = data.getRegistryName();
		int sizeConf = (int)Configs.GENERATOR.getFloat("biomes.betternether.edge", regName + "_size", size);
		if (sizeConf > 0.0F) {
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(sizeConf);
			ALL_BIOMES.add(biome);
			
			BiomeAPI.registerBiome(biome);
		}
	}
	
	private static void registerSubBiome(BCLBiome biome, BCLBiome mainBiome, float chance) {
		final NetherBiomeData data = NetherBiomeData.getCustomNetherData(biome);
		final String regName = data.getRegistryName();
		chance = Configs.GENERATOR.getFloat("biomes.betternether.variation", regName + "_chance", chance);
		if (chance > 0.0F) {
			ALL_BIOMES.add(biome);
			
			BiomeAPI.registerSubBiome(mainBiome, biome);
		}
	}

	public static BCLBiome getBiome(Random random) {
		return BiomeAPI.NETHER_BIOME_PICKER.getBiome(random);
	}

	public static ArrayList<BCLBiome> getAllBiomes() {
		return ALL_BIOMES;
	}
	
	public static void onWorldLoad(ServerLevel level, long seed, Registry<Biome> registry) {
		CavesFeature.onLoad(seed);
		PathsFeature.onLoad(seed);
		CityFeature.initGenerator();
	}
}
