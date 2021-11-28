package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import paulevs.betternether.BetterNether;
import paulevs.betternether.biomes.CrimsonGlowingWoods;
import paulevs.betternether.biomes.CrimsonPinewood;
import paulevs.betternether.biomes.FloodedDeltas;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.biomes.NetherBiomeWrapper;
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
import paulevs.betternether.config.Config;
import paulevs.betternether.config.Configs;
import ru.bclib.BCLib;
import ru.bclib.api.BiomeAPI;
import ru.bclib.world.biomes.BCLBiome;

public class BiomesRegistry {
	private static final ArrayList<NetherBiome> REGISTRY = new ArrayList<NetherBiome>();
	private static final ArrayList<NetherBiome> ALL_BIOMES = new ArrayList<NetherBiome>();
	private static final HashMap<Biome, NetherBiome> MUTABLE = Maps.newHashMap();
	private static final ArrayList<NetherBiome> GENERATOR = new ArrayList<NetherBiome>();
	private static final Set<Integer> OCCUPIED_IDS = Sets.newHashSet();

	public static final NetherBiome BIOME_EMPTY_NETHER = new NetherBiomeWrapper(new ResourceLocation("nether_wastes"));
	public static final NetherBiome BIOME_CRIMSON_FOREST = new NetherBiomeWrapper(new ResourceLocation("crimson_forest"));
	public static final NetherBiome BIOME_WARPED_FOREST = new NetherBiomeWrapper(new ResourceLocation("warped_forest"));
	public static final NetherBiome BIOME_BASALT_DELTAS = new NetherBiomeWrapper(new ResourceLocation("basalt_deltas"));

	public static final NetherBiome BIOME_GRAVEL_DESERT = new NetherGravelDesert("Gravel Desert");
	public static final NetherBiome BIOME_NETHER_JUNGLE = new NetherJungle("Nether Jungle");
	public static final NetherBiome BIOME_WART_FOREST = new NetherWartForest("Wart Forest");
	public static final NetherBiome BIOME_GRASSLANDS = new NetherGrasslands("Nether Grasslands");
	public static final NetherBiome BIOME_MUSHROOM_FOREST = new NetherMushroomForest("Nether Mushroom Forest");
	public static final NetherBiome BIOME_MUSHROOM_FOREST_EDGE = new NetherMushroomForestEdge("Nether Mushroom Forest Edge");
	public static final NetherBiome BIOME_WART_FOREST_EDGE = new NetherWartForestEdge("Wart Forest Edge");
	public static final NetherBiome BIOME_BONE_REEF = new NetherBoneReef("Bone Reef");
	public static final NetherBiome BIOME_SULFURIC_BONE_REEF = new NetherSulfuricBoneReef("Sulfuric Bone Reef");
	public static final NetherBiome BIOME_POOR_GRASSLANDS = new NetherPoorGrasslands("Poor Nether Grasslands");
	public static final NetherBiome NETHER_SWAMPLAND = new NetherSwampland("Nether Swampland");
	public static final NetherBiome NETHER_SWAMPLAND_TERRACES = new NetherSwamplandTerraces("Nether Swampland Terraces");
	public static final NetherBiome MAGMA_LAND = new NetherMagmaLand("Magma Land");
	public static final NetherBiome SOUL_PLAIN = new NetherSoulPlain("Soul Plain");
	public static final NetherBiome CRIMSON_GLOWING_WOODS = new CrimsonGlowingWoods("Crimson Glowing Woods");
	public static final NetherBiome OLD_WARPED_WOODS = new OldWarpedWoods("Old Warped Woods");
	public static final NetherBiome CRIMSON_PINEWOOD = new CrimsonPinewood("Crimson Pinewood");
	public static final NetherBiome OLD_FUNGIWOODS = new OldFungiwoods("Old Fungiwoods");
	public static final NetherBiome FLOODED_DELTAS = new FloodedDeltas("Flooded Deltas");
	public static final NetherBiome UPSIDE_DOWN_FOREST = new UpsideDownForest("Upside Down Forest");
	public static final NetherBiome OLD_SWAMPLAND = new OldSwampland("Old Swampland");

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
		registerNetherBiome(BIOME_WART_FOREST);
		registerNetherBiome(BIOME_GRASSLANDS);
		registerNetherBiome(BIOME_MUSHROOM_FOREST);
		registerEdgeBiome(BIOME_MUSHROOM_FOREST_EDGE, BIOME_MUSHROOM_FOREST, 2);
		registerEdgeBiome(BIOME_WART_FOREST_EDGE, BIOME_WART_FOREST, 2);
		registerNetherBiome(BIOME_BONE_REEF);
		registerSubBiome(BIOME_SULFURIC_BONE_REEF, BIOME_BONE_REEF, 0.3F);
		registerSubBiome(BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS, 0.3F);
		registerNetherBiome(NETHER_SWAMPLAND);
		registerSubBiome(NETHER_SWAMPLAND_TERRACES, NETHER_SWAMPLAND, 1F);
		registerNetherBiome(MAGMA_LAND);
		registerSubBiome(SOUL_PLAIN, BIOME_WART_FOREST, 1F);
		registerSubBiome(CRIMSON_GLOWING_WOODS, BIOME_CRIMSON_FOREST, 0.3F);
		registerSubBiome(OLD_WARPED_WOODS, BIOME_WARPED_FOREST, 1F);
		registerSubBiome(CRIMSON_PINEWOOD, BIOME_CRIMSON_FOREST, 0.3F);
		registerSubBiome(OLD_FUNGIWOODS, BIOME_MUSHROOM_FOREST, 0.3F);
		registerSubBiome(FLOODED_DELTAS, BIOME_BASALT_DELTAS, 1F);
		registerNetherBiome(UPSIDE_DOWN_FOREST);
		registerSubBiome(OLD_SWAMPLAND, NETHER_SWAMPLAND, 1F);

		registerNetherBiome(BIOME_EMPTY_NETHER);
		registerNetherBiome(BIOME_CRIMSON_FOREST);
		registerNetherBiome(BIOME_WARPED_FOREST);
		registerNetherBiome(BIOME_BASALT_DELTAS);
		
		RegistryEntryAddedCallback.event(BuiltinRegistries.BIOME).register((i, id, biome) -> {
			if (biome.getBiomeCategory() == BiomeCategory.NETHER) {
				ResourceLocation bioID = BuiltinRegistries.BIOME.getKey(biome);
				Configs.GENERATOR.getFloat("biomes." + bioID.getNamespace() + ".main", bioID.getPath() + "_chance", 1);
			}
		});
		
		BiomeAPI.registerNetherBiomeModification((biomeID, biome) -> {
			if (!biomeID.getNamespace().equals(BetterNether.MOD_ID)) {
				BiomeAPI.addBiomeMobSpawn(biome, EntityRegistry.FIREFLY, 5, 3, 6);
				BiomeAPI.addBiomeMobSpawn(biome, EntityRegistry.HYDROGEN_JELLYFISH, 5, 2, 5);
				BiomeAPI.addBiomeMobSpawn(biome, EntityRegistry.NAGA, 8, 3, 5);
			}
		});
	}
	
	public static void mutateRegistry(Registry<Biome> biomeRegistry) {
		GENERATOR.clear();
		GENERATOR.addAll(REGISTRY);

		MUTABLE.clear();
		for (NetherBiome netherBiome : BiomesRegistry.getAllBiomes()) {
			Biome biome = biomeRegistry.get(netherBiome.getID());
			netherBiome.setActualBiome(biome);
			MUTABLE.put(biome, netherBiome);
		}

		if (maxDefChance == 0) maxDefChance = maxChance;
		maxChance = maxDefChance;

		Iterator<Biome> iterator = biomeRegistry.iterator();
		while (iterator.hasNext()) {
			Biome biome = iterator.next();
			if (biome.getBiomeCategory() == BiomeCategory.NETHER && !BiomesRegistry.MUTABLE.containsKey(biome)) {
				ResourceLocation id = biomeRegistry.getKey(biome);
				NetherBiome netherBiome = new NetherBiomeWrapper(biomeRegistry.getKey(biome), biome);
				netherBiome.setActualBiome(biome);
				MUTABLE.put(biome, netherBiome);

				float chance = Configs.GENERATOR.getFloat("biomes." + id.getNamespace() + ".main", id.getPath() + "_chance", 1);
				if (chance > 0.0F) {
					maxChance += chance;
					//netherBiome.setGenChance(maxChance);
					String path = "generator.biome." + netherBiome.getID().getNamespace() + "." + netherBiome.getID().getPath();
					netherBiome.setPlantDensity(Configs.BIOMES.getFloat(path, "plants_and_structures_density", 1));
					netherBiome.build();
					GENERATOR.add(netherBiome);
				}
			}
		}
		
		Config.save();
	}

	private static void register(NetherBiome biome) {
		if (BuiltinRegistries.BIOME.get(biome.getID()) == null) {
			if (OCCUPIED_IDS.isEmpty()) {
				BuiltinRegistries.BIOME.forEach((bio) -> {
					OCCUPIED_IDS.add(BuiltinRegistries.BIOME.getId(bio));
				});
			}
			biomeID ++;
			while (OCCUPIED_IDS.contains(biomeID)) {
				biomeID ++;
			}
			//Registry.registerMapping(BuiltinRegistries.BIOME, biomeID, biome.getID().toString(), biome.getBiome());
		}
	}

	public static void registerNetherBiome(NetherBiome biome) {
		float chance = Configs.GENERATOR.getFloat("biomes." + biome.getID().getNamespace() + ".main", biome.getID().getPath() + "_chance", 1);
		if (chance > 0.0F) {
			maxChance += chance;
			String path = "generator.biome." + biome.getID().getNamespace() + "." + biome.getID().getPath();
			biome.setPlantDensity(Configs.BIOMES.getFloat(path, "plants_and_structures_density", 1));
			//biome.setGenChance(maxChance);
			biome.build();
			REGISTRY.add(biome);
			ALL_BIOMES.add(biome);
			register(biome);
			
			Biome b = BuiltinRegistries.BIOME.get(biome.getID());
			if (b==null) {
				BiomeAPI.registerNetherBiome(biome);
			}
		}
	}

	public static void registerEdgeBiome(NetherBiome biome, NetherBiome mainBiome, int size) {
		String regName = biome.getRegistryName();
		int sizeConf = (int)Configs.GENERATOR.getFloat("biomes.betternether.edge", regName + "_size", size);
		if (sizeConf > 0.0F) {
			String path = "generator.biome." + biome.getID().getNamespace() + "." + biome.getID().getPath();
			biome.setPlantDensity(Configs.BIOMES.getFloat(path, "plants_and_structures_density", 1));
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(sizeConf);
			biome.build();
			ALL_BIOMES.add(biome);
			register(biome);
			
			BiomeAPI.registerBiome(biome);
		}
	}

	public static void registerSubBiome(NetherBiome biome, NetherBiome mainBiome, float chance) {
		String regName = biome.getRegistryName();
		chance = Configs.GENERATOR.getFloat("biomes.betternether.variation", regName + "_chance", chance);
		if (chance > 0.0F) {
			String path = "generator.biome." + biome.getID().getNamespace() + "." + biome.getID().getPath();
			biome.setPlantDensity(Configs.BIOMES.getFloat(path, "plants_and_structures_density", 1));
			biome.build();
			ALL_BIOMES.add(biome);
			register(biome);
			
			BiomeAPI.registerSubBiome(mainBiome, biome);
		}
	}

	public static BCLBiome getBiome(Random random) {
		return BiomeAPI.NETHER_BIOME_PICKER.getBiome(random);
		//float chance = random.nextFloat() * maxChance;
		/*for (NetherBiome biome : GENERATOR)
			if (biome.canGenerate(chance))
				return biome;*/
		//return BIOME_EMPTY_NETHER;
	}

	/*public static NetherBiome getFromBiome(Biome biome) {
		return MUTABLE.getOrDefault(biome, BIOME_EMPTY_NETHER);
	}*/

	public static ArrayList<NetherBiome> getRegisteredBiomes() {
		return REGISTRY;
	}

	public static ArrayList<NetherBiome> getAllBiomes() {
		return ALL_BIOMES;
	}
}
