package paulevs.betternether.registry;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import paulevs.betternether.BetterNether;
import paulevs.betternether.biomes.CrimsonGlowingWoods;
import paulevs.betternether.biomes.CrimsonPinewood;
import paulevs.betternether.biomes.FloodedDeltas;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.biomes.NetherBiomeBuilder;
import paulevs.betternether.biomes.NetherBiomeConfig;
import paulevs.betternether.biomes.NetherBoneReef;
import paulevs.betternether.biomes.NetherGrasslands;
import paulevs.betternether.biomes.NetherGravelDesert;
import paulevs.betternether.biomes.NetherJungle;
import paulevs.betternether.biomes.NetherMagmaLand;
import paulevs.betternether.biomes.NetherMushroomForest;
import paulevs.betternether.biomes.NetherMushroomForestEdge;
import paulevs.betternether.biomes.NetherPoorGrasslands;
import paulevs.betternether.biomes.NetherSoulPlain;
import paulevs.betternether.biomes.NetherSulfuricBoneReef;
import paulevs.betternether.biomes.NetherSwampland;
import paulevs.betternether.biomes.NetherSwamplandTerraces;
import paulevs.betternether.biomes.NetherWartForest;
import paulevs.betternether.biomes.NetherWartForestEdge;
import paulevs.betternether.biomes.OldFungiwoods;
import paulevs.betternether.biomes.OldSwampland;
import paulevs.betternether.biomes.OldWarpedWoods;
import paulevs.betternether.biomes.UpsideDownForest;
import paulevs.betternether.config.Configs;
import ru.bclib.api.biomes.BiomeAPI;
import ru.bclib.api.LifeCycleAPI;
import ru.bclib.world.biomes.BCLBiome;

import java.util.Random;

public class NetherBiomes {
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
		public static final NetherBiomeConfig OLD_SWAMPLAND = new OldSwampland.Config("Old Swampland");
	}
	
	public static final BCLBiome BIOME_GRAVEL_DESERT = registerNetherBiome(Config.BIOME_GRAVEL_DESERT);
	public static final BCLBiome BIOME_NETHER_JUNGLE = registerNetherBiome(Config.BIOME_NETHER_JUNGLE);
	public static final BCLBiome BIOME_WART_FOREST = registerNetherBiome(Config.BIOME_WART_FOREST);
	public static final BCLBiome BIOME_GRASSLANDS = registerNetherBiome(Config.BIOME_GRASSLANDS);
	public static final BCLBiome BIOME_MUSHROOM_FOREST = registerNetherBiome(Config.BIOME_MUSHROOM_FOREST);
	public static final BCLBiome BIOME_MUSHROOM_FOREST_EDGE = registerEdgeBiome(Config.BIOME_MUSHROOM_FOREST_EDGE, BIOME_MUSHROOM_FOREST, 2);;
	public static final BCLBiome BIOME_WART_FOREST_EDGE = registerEdgeBiome(Config.BIOME_WART_FOREST_EDGE, BIOME_WART_FOREST, 2);
	public static final BCLBiome BIOME_BONE_REEF = registerNetherBiome(Config.BIOME_BONE_REEF);
	public static final BCLBiome BIOME_SULFURIC_BONE_REEF = registerSubBiome(Config.BIOME_SULFURIC_BONE_REEF, BIOME_BONE_REEF, 0.3F);
	public static final BCLBiome BIOME_POOR_GRASSLANDS = registerSubBiome(Config.BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS, 0.3F);;
	public static final BCLBiome NETHER_SWAMPLAND = registerNetherBiome(Config.NETHER_SWAMPLAND);
	public static final BCLBiome NETHER_SWAMPLAND_TERRACES = registerSubBiome(Config.NETHER_SWAMPLAND_TERRACES, NETHER_SWAMPLAND, 1F);
	public static final BCLBiome MAGMA_LAND = registerNetherBiome(Config.MAGMA_LAND);
	public static final BCLBiome SOUL_PLAIN = registerSubBiome(Config.SOUL_PLAIN, BIOME_WART_FOREST, 1F);
	public static final BCLBiome CRIMSON_GLOWING_WOODS = registerSubBiome(Config.CRIMSON_GLOWING_WOODS, BiomeAPI.CRIMSON_FOREST_BIOME, 0.3F);
	public static final BCLBiome OLD_WARPED_WOODS = registerSubBiome(Config.OLD_WARPED_WOODS, BiomeAPI.WARPED_FOREST_BIOME, 1F);
	public static final BCLBiome CRIMSON_PINEWOOD = registerSubBiome(Config.CRIMSON_PINEWOOD, BiomeAPI.CRIMSON_FOREST_BIOME, 0.3F);
	public static final BCLBiome OLD_FUNGIWOODS = registerSubBiome(Config.OLD_FUNGIWOODS, BIOME_MUSHROOM_FOREST, 0.3F);
	public static final BCLBiome FLOODED_DELTAS = registerSubBiome(Config.FLOODED_DELTAS, BiomeAPI.BASALT_DELTAS_BIOME, 1F);
	public static final BCLBiome UPSIDE_DOWN_FOREST = registerNetherBiome(Config.UPSIDE_DOWN_FOREST);
	public static final BCLBiome OLD_SWAMPLAND = registerSubBiome(Config.OLD_SWAMPLAND, NETHER_SWAMPLAND, 1F);

	public static void register() {
		BuiltinRegistries.BIOME.forEach((biome) -> {
			if (biome.getBiomeCategory() == BiomeCategory.NETHER) {
				ResourceLocation id = BuiltinRegistries.BIOME.getKey(biome);
				Configs.GENERATOR.getFloat("biomes." + id.getNamespace() + ".main", id.getPath() + "_chance", 1);
			}
		});
		
		RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, id, biome) -> {
			if (biome.getBiomeCategory() == BiomeCategory.NETHER) {
				ResourceLocation bioID = BuiltinRegistries.BIOME.getKey(biome);
				Configs.GENERATOR.getFloat("biomes." + bioID.getNamespace() + ".main", bioID.getPath() + "_chance", 1);
			}
		});
		
		BiomeAPI.registerNetherBiomeModification((biomeID, biome) -> {
			if (!biomeID.getNamespace().equals(BetterNether.MOD_ID)) {
				NetherEntities.modifyNonBNBiome(biome);
				NetherFeatures.modifyNonBNBiome(biome);
				NetherStructures.modifyNonBNBiome(biome);
			}
		});

		LifeCycleAPI.onLevelLoad(NetherFeatures::onWorldLoad);
	}
	
	private static NetherBiome registerNetherBiome(NetherBiomeConfig config) {
		final NetherBiome biome = NetherBiomeBuilder.create(config);
		final float chance = Configs.GENERATOR.getFloat("biomes." + biome.getID().getNamespace() + ".main", biome.getID().getPath() + "_chance", 1);
		biome.setGenChance(chance);
		
		if (chance > 0.0F) {
			BiomeAPI.registerNetherBiome(biome);
		}
		
		return biome;
	}
	
	private static NetherBiome registerEdgeBiome(NetherBiomeConfig config, BCLBiome mainBiome, int size) {
		final NetherBiome biome = NetherBiomeBuilder.create(config);
		final String regName = biome.getRegistryName();
		final int sizeConf = (int)Configs.GENERATOR.getFloat("biomes.betternether.edge", regName + "_size", size);
		
		mainBiome.setEdge(biome);
		mainBiome.setEdgeSize(sizeConf);
		if (sizeConf > 0.0F) {
			BiomeAPI.registerBiome(biome);
		}
		
		return biome;
	}
	
	private static NetherBiome registerSubBiome(NetherBiomeConfig config, BCLBiome mainBiome, float chance) {
		final NetherBiome biome = NetherBiomeBuilder.create(config);
		final String regName = biome.getRegistryName();
		chance = Configs.GENERATOR.getFloat("biomes.betternether.variation", regName + "_chance", chance);
		biome.setGenChance(chance);
		
		if (chance > 0.0F) {
			BiomeAPI.registerSubBiome(mainBiome, biome);
		}
		
		return biome;
	}

	public static BCLBiome getBiome(Random random) {
		return BiomeAPI.NETHER_BIOME_PICKER.getBiome(random);
	}
}
