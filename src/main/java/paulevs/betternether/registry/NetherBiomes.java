package paulevs.betternether.registry;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import paulevs.betternether.BetterNether;
import paulevs.betternether.world.biomes.CrimsonGlowingWoods;
import paulevs.betternether.world.biomes.CrimsonPinewood;
import paulevs.betternether.world.biomes.FloodedDeltas;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.biomes.NetherBoneReef;
import paulevs.betternether.world.biomes.NetherGrasslands;
import paulevs.betternether.world.biomes.NetherGravelDesert;
import paulevs.betternether.world.biomes.NetherJungle;
import paulevs.betternether.world.biomes.NetherMagmaLand;
import paulevs.betternether.world.biomes.NetherMushroomForest;
import paulevs.betternether.world.biomes.NetherMushroomForestEdge;
import paulevs.betternether.world.biomes.NetherPoorGrasslands;
import paulevs.betternether.world.biomes.NetherSoulPlain;
import paulevs.betternether.world.biomes.NetherSulfuricBoneReef;
import paulevs.betternether.world.biomes.NetherSwampland;
import paulevs.betternether.world.biomes.NetherSwamplandTerraces;
import paulevs.betternether.world.biomes.NetherWartForest;
import paulevs.betternether.world.biomes.NetherWartForestEdge;
import paulevs.betternether.world.biomes.OldFungiwoods;
import paulevs.betternether.world.biomes.OldSwampland;
import paulevs.betternether.world.biomes.OldWarpedWoods;
import paulevs.betternether.world.biomes.UpsideDownForest;
import paulevs.betternether.config.Configs;
import paulevs.betternether.world.biomes.UpsideDownForestCleared;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import ru.bclib.api.WorldDataAPI;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.api.LifeCycleAPI;
import ru.bclib.util.ModUtil;
import ru.bclib.world.biomes.BCLBiome;
import ru.bclib.world.generator.BCLibNetherBiomeSource;
import ru.bclib.world.generator.GeneratorOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetherBiomes {
	public static final List<NetherBiome> ALL_BN_BIOMES = new ArrayList<>(21);
	private static class Config {
		public static final NetherBiomeConfig BIOME_GRAVEL_DESERT = new NetherGravelDesert.Config("Gravel Desert");
		public static final NetherBiomeConfig BIOME_NETHER_JUNGLE = new NetherJungle.Config("Nether Jungle");
		public static final NetherBiomeConfig BIOME_WART_FOREST = new NetherWartForest.Config("Wart Forest");
		public static final NetherBiomeConfig BIOME_GRASSLANDS = new NetherGrasslands.Config("Nether Grasslands");
		public static final NetherBiomeConfig BIOME_MUSHROOM_FOREST = new NetherMushroomForest.Config("Nether Mushroom Forest");
		public static final NetherBiomeConfig BIOME_MUSHROOM_FOREST_EDGE = new NetherMushroomForestEdge.Config("Nether Mushroom Forest Edge");
		public static final NetherBiomeConfig BIOME_WART_FOREST_EDGE = new NetherWartForestEdge.Config("Wart Forest Edge");
		public static final NetherBiomeConfig BIOME_BONE_REEF = new NetherBoneReef.Config("Bone Reef");
		public static final NetherBiomeConfig BIOME_SULFURIC_BONE_REEF = new NetherSulfuricBoneReef.Config("Sulfuric Bone Reef");
		public static final NetherBiomeConfig BIOME_POOR_GRASSLANDS = new NetherPoorGrasslands.Config("Poor Nether Grasslands");
		public static final NetherBiomeConfig NETHER_SWAMPLAND = new NetherSwampland.Config("Nether Swampland");
		public static final NetherBiomeConfig NETHER_SWAMPLAND_TERRACES = new NetherSwamplandTerraces.Config("Nether Swampland Terraces");
		public static final NetherBiomeConfig MAGMA_LAND = new NetherMagmaLand.Config("Magma Land");
		public static final NetherBiomeConfig SOUL_PLAIN = new NetherSoulPlain.Config("Soul Plain");
		public static final NetherBiomeConfig CRIMSON_GLOWING_WOODS = new CrimsonGlowingWoods.Config("Crimson Glowing Woods");
		public static final NetherBiomeConfig OLD_WARPED_WOODS = new OldWarpedWoods.Config("Old Warped Woods");
		public static final NetherBiomeConfig CRIMSON_PINEWOOD = new CrimsonPinewood.Config("Crimson Pinewood");
		public static final NetherBiomeConfig OLD_FUNGIWOODS = new OldFungiwoods.Config("Old Fungiwoods");
		public static final NetherBiomeConfig FLOODED_DELTAS = new FloodedDeltas.Config("Flooded Deltas");
		public static final NetherBiomeConfig UPSIDE_DOWN_FOREST = new UpsideDownForest.Config("Upside Down Forest");
		public static final NetherBiomeConfig UPSIDE_DOWN_FOREST_CLEARED = new UpsideDownForestCleared.Config("upside_down_forest_cleared");
		public static final NetherBiomeConfig OLD_SWAMPLAND = new OldSwampland.Config("Old Swampland");
	}
	
	public static final BCLBiome BIOME_GRAVEL_DESERT = registerNetherBiome(Config.BIOME_GRAVEL_DESERT);
	public static final BCLBiome BIOME_NETHER_JUNGLE = registerNetherBiome(Config.BIOME_NETHER_JUNGLE);
	public static final BCLBiome BIOME_WART_FOREST = registerNetherBiome(Config.BIOME_WART_FOREST, Config.BIOME_WART_FOREST_EDGE);
	public static final BCLBiome BIOME_GRASSLANDS = registerNetherBiome(Config.BIOME_GRASSLANDS);
	public static final BCLBiome BIOME_MUSHROOM_FOREST = registerNetherBiome(Config.BIOME_MUSHROOM_FOREST, Config.BIOME_MUSHROOM_FOREST_EDGE);
	public static final BCLBiome BIOME_MUSHROOM_FOREST_EDGE = BIOME_MUSHROOM_FOREST.getEdge();
	public static final BCLBiome BIOME_WART_FOREST_EDGE = BIOME_WART_FOREST.getEdge();
	public static final BCLBiome BIOME_BONE_REEF = registerNetherBiome(Config.BIOME_BONE_REEF);
	public static final BCLBiome BIOME_SULFURIC_BONE_REEF = registerSubBiome(Config.BIOME_SULFURIC_BONE_REEF, BIOME_BONE_REEF);
	public static final BCLBiome BIOME_POOR_GRASSLANDS = registerSubBiome(Config.BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS);
	public static final BCLBiome NETHER_SWAMPLAND = registerNetherBiome(Config.NETHER_SWAMPLAND);
	public static final BCLBiome NETHER_SWAMPLAND_TERRACES = registerSubBiome(Config.NETHER_SWAMPLAND_TERRACES, NETHER_SWAMPLAND);
	public static final BCLBiome MAGMA_LAND = registerNetherBiome(Config.MAGMA_LAND);
	public static final BCLBiome SOUL_PLAIN = registerSubBiome(Config.SOUL_PLAIN, BIOME_WART_FOREST);
	public static final BCLBiome CRIMSON_GLOWING_WOODS = registerSubBiome(Config.CRIMSON_GLOWING_WOODS, BiomeAPI.CRIMSON_FOREST_BIOME);
	public static final BCLBiome OLD_WARPED_WOODS = registerSubBiome(Config.OLD_WARPED_WOODS, BiomeAPI.WARPED_FOREST_BIOME);
	public static final BCLBiome CRIMSON_PINEWOOD = registerSubBiome(Config.CRIMSON_PINEWOOD, BiomeAPI.CRIMSON_FOREST_BIOME);
	public static final BCLBiome OLD_FUNGIWOODS = registerSubBiome(Config.OLD_FUNGIWOODS, BIOME_MUSHROOM_FOREST);
	public static final BCLBiome FLOODED_DELTAS = registerSubBiome(Config.FLOODED_DELTAS, BiomeAPI.BASALT_DELTAS_BIOME);
	public static final BCLBiome UPSIDE_DOWN_FOREST = registerNetherBiome(Config.UPSIDE_DOWN_FOREST);
	public static final BCLBiome UPSIDE_DOWN_FOREST_CLEARED = registerNetherBiome(Config.UPSIDE_DOWN_FOREST_CLEARED);
	public static final BCLBiome OLD_SWAMPLAND = registerSubBiome(Config.OLD_SWAMPLAND, NETHER_SWAMPLAND);

	public static void register() {
		BiomeAPI.registerNetherBiomeModification((biomeID, biome) -> {
			if (!biomeID.getNamespace().equals(BetterNether.MOD_ID) && biome.getBiomeCategory()==BiomeCategory.NETHER) {
				NetherEntities.modifyNonBNBiome(biomeID, biome);
				NetherFeatures.modifyNonBNBiome(biomeID, biome);
				NetherStructures.modifyNonBNBiome(biomeID, biome);
			}
		});
		
		LifeCycleAPI.beforeLevelLoad(NetherBiomes::onWorldLoad);
	}
	
	private static NetherBiome registerNetherBiome(NetherBiomeConfig config) {
		final NetherBiome biome = NetherBiomeBuilder.create(config);
		
		if (biome.getGenChance() > 0.0F)
		{
			ALL_BN_BIOMES.add(biome);
			BiomeAPI.registerNetherBiome(biome);
		}
		
		return biome;
	}
	
	private static NetherBiome registerNetherBiome(NetherBiomeConfig config, NetherBiomeConfig edgeConfig) {
		final NetherBiome edge = NetherBiomeBuilder.create(edgeConfig);
		final NetherBiome biome = NetherBiomeBuilder.create(config, edge);
		
		
		if (biome.getGenChance() > 0.0F)
		{
			ALL_BN_BIOMES.add(biome);
			BiomeAPI.registerNetherBiome(biome);
		}
		
		if (biome.getEdge()!=null && edge.getGenChance()> 0.0f){
			ALL_BN_BIOMES.add(edge);
			BiomeAPI.registerNetherBiome(edge);
		}
		
		return biome;
	}
	
	
	private static NetherBiome registerSubBiome(NetherBiomeConfig config, BCLBiome mainBiome) {
		final NetherBiome biome = NetherBiomeBuilder.create(config);
		
		if (biome.getGenChance() > 0.0F)
		{
			ALL_BN_BIOMES.add(biome);
			BiomeAPI.registerSubBiome(mainBiome, biome);
		}
		
		return biome;
	}

	public static BCLBiome getBiome(Random random) {
		return BiomeAPI.NETHER_BIOME_PICKER.getBiome(random);
	}
	public static boolean useLegacyGeneration = false;
	private static final String TAG_GEN_VERSION = "generator_version";
	private static final String TAG_VERSION = "version";
	public static void onWorldLoad() {
		CompoundTag root = WorldDataAPI.getRootTag(BetterNether.MOD_ID);
		String version = "0.0.0";
		if (root.contains(TAG_VERSION)){
			version = root.getString(TAG_VERSION);
		}
		
		if (!ModUtil.isLargerOrEqualVersion(version, "6.0.0")){
			root.putString(TAG_VERSION, ModUtil.getModVersion(BetterNether.MOD_ID));
			root.putString(TAG_GEN_VERSION, "1.17");
			useLegacyGeneration = true;
			WorldDataAPI.saveFile(BetterNether.MOD_ID);
		} else if (!root.contains(TAG_GEN_VERSION)){
			root.putString(TAG_GEN_VERSION, GeneratorOptions.useOldBiomeGenerator()?"1.17":"1.18");
			useLegacyGeneration = false;
			WorldDataAPI.saveFile(BetterNether.MOD_ID);
		} else {
			useLegacyGeneration = "1.17".equals(root.getString(TAG_GEN_VERSION));
		}
		
		BCLibNetherBiomeSource.setForceLegacyGeneration(useLegacyGeneration);
		BetterNether.LOGGER.info("Using legacy (1.17) generator: " + useLegacyGeneration);
		
		NetherChunkPopulatorFeature.clearGeneratorPool();
	}
}
