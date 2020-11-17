package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.google.common.collect.Maps;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
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

public class BiomesRegistry {
	private static final ArrayList<NetherBiome> REGISTRY = new ArrayList<NetherBiome>();
	private static final ArrayList<NetherBiome> ALL_BIOMES = new ArrayList<NetherBiome>();
	private static final HashMap<Biome, NetherBiome> MUTABLE = Maps.newHashMap();
	private static final ArrayList<NetherBiome> GENERATOR = new ArrayList<NetherBiome>();

	private static final HashMap<NetherBiome, Float> DEF_CHANCES_MAIN = Maps.newHashMap();
	private static final HashMap<NetherBiome, Float> DEF_CHANCES_SUB = Maps.newHashMap();
	private static final HashMap<NetherBiome, Integer> DEF_SIZE_EDGE = Maps.newHashMap();

	public static final NetherBiome BIOME_EMPTY_NETHER = new NetherBiomeWrapper(new Identifier("nether_wastes"));
	public static final NetherBiome BIOME_CRIMSON_FOREST = new NetherBiomeWrapper(new Identifier("crimson_forest"));
	public static final NetherBiome BIOME_WARPED_FOREST = new NetherBiomeWrapper(new Identifier("warped_forest"));
	public static final NetherBiome BIOME_BASALT_DELTAS = new NetherBiomeWrapper(new Identifier("basalt_deltas"));

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

	public static void register() {
		registerBiome(BIOME_GRAVEL_DESERT);
		registerBiome(BIOME_NETHER_JUNGLE);
		registerBiome(BIOME_WART_FOREST);
		registerBiome(BIOME_GRASSLANDS);
		registerBiome(BIOME_MUSHROOM_FOREST);
		registerEdgeBiome(BIOME_MUSHROOM_FOREST_EDGE, BIOME_MUSHROOM_FOREST, 2);
		registerEdgeBiome(BIOME_WART_FOREST_EDGE, BIOME_WART_FOREST, 2);
		registerBiome(BIOME_BONE_REEF);
		registerSubBiome(BIOME_SULFURIC_BONE_REEF, BIOME_BONE_REEF, 0.3F);
		registerSubBiome(BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS, 0.3F);
		registerBiome(NETHER_SWAMPLAND);
		registerSubBiome(NETHER_SWAMPLAND_TERRACES, NETHER_SWAMPLAND, 1F);
		registerBiome(MAGMA_LAND);
		registerSubBiome(SOUL_PLAIN, BIOME_WART_FOREST, 1F);
		registerSubBiome(CRIMSON_GLOWING_WOODS, BIOME_CRIMSON_FOREST, 0.3F);
		registerSubBiome(OLD_WARPED_WOODS, BIOME_WARPED_FOREST, 1F);
		registerSubBiome(CRIMSON_PINEWOOD, BIOME_CRIMSON_FOREST, 0.3F);
		registerSubBiome(OLD_FUNGIWOODS, BIOME_MUSHROOM_FOREST, 0.3F);
		registerSubBiome(FLOODED_DELTAS, BIOME_BASALT_DELTAS, 1F);
		registerBiome(UPSIDE_DOWN_FOREST);
		registerSubBiome(OLD_SWAMPLAND, NETHER_SWAMPLAND, 1F);

		registerDefaultWrapped(BIOME_EMPTY_NETHER);
		registerDefaultWrapped(BIOME_CRIMSON_FOREST);
		registerDefaultWrapped(BIOME_WARPED_FOREST);
		registerDefaultWrapped(BIOME_BASALT_DELTAS);
	}

	private static void registerDefaultWrapped(NetherBiome biome) {
		float chance = Config.getFloat("biomes.minecraft.main", biome.getRegistryName() + "_chance", 1);
		if (chance > 0.0F) {
			maxChance += chance;
			biome.setGenChance(maxChance);
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getRegistryName(), "plants_and_structures_density", 1));
			biome.build();
			REGISTRY.add(biome);
			ALL_BIOMES.add(biome);
			DEF_CHANCES_MAIN.put(biome, 1F);
		}
	}

	private static void registerBiomeDirect(String regName, NetherBiome biome) {
		Registry.register(BuiltinRegistries.BIOME, biome.getID(), biome.getBiome());
	}

	public static void registerBiome(NetherBiome biome) {
		String regName = biome.getRegistryName();
		float chance = Config.getFloat("biomes.betternether.main", regName + "_chance", 1);
		if (chance > 0.0F) {
			maxChance += chance;
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getRegistryName(), "plants_and_structures_density", 1));
			biome.setGenChance(maxChance);
			biome.build();
			REGISTRY.add(biome);
			ALL_BIOMES.add(biome);
			DEF_CHANCES_MAIN.put(biome, chance);
			registerBiomeDirect(regName, biome);
		}
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
			if (biome.getCategory() == Category.NETHER && !BiomesRegistry.MUTABLE.containsKey(biome)) {
				Identifier id = biomeRegistry.getId(biome);
				NetherBiome netherBiome = new NetherBiomeWrapper(biomeRegistry.getId(biome), biome);
				netherBiome.setActualBiome(biome);
				MUTABLE.put(biome, netherBiome);

				float chance = Config.getFloat("biomes." + id.getNamespace() + ".main", id.getPath() + "_chance", 1);
				if (chance > 0.0F) {
					maxChance += chance;
					netherBiome.setGenChance(maxChance);
					netherBiome.setPlantDensity(Config.getFloat("generator.biome." + netherBiome.getRegistryName(), "plants_and_structures_density", 1));
					netherBiome.build();
					GENERATOR.add(netherBiome);
					DEF_CHANCES_MAIN.put(netherBiome, 1F);
				}
			}
		}
	}

	public static void registerEdgeBiome(NetherBiome biome, NetherBiome mainBiome, int size) {
		String regName = biome.getRegistryName();
		int sizeConf = Config.getInt("biomes.betternether.edge", regName + "_size", size);
		if (sizeConf > 0.0F) {
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getRegistryName(), "plants_and_structures_density", 1));
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(sizeConf);
			biome.build();
			DEF_SIZE_EDGE.put(biome, sizeConf);
			ALL_BIOMES.add(biome);
			registerBiomeDirect(regName, biome);
		}
	}

	public static void registerSubBiome(NetherBiome biome, NetherBiome mainBiome, float chance) {
		String regName = biome.getRegistryName();
		chance = Config.getFloat("biomes.betternether.variation", regName + "_chance", chance);
		if (chance > 0.0F) {
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getRegistryName(), "plants_and_structures_density", 1));
			mainBiome.addSubBiome(biome, chance);
			biome.build();
			DEF_CHANCES_SUB.put(biome, chance);
			ALL_BIOMES.add(biome);
			registerBiomeDirect(regName, biome);
		}
	}

	public static NetherBiome getBiome(Random random) {
		float chance = random.nextFloat() * maxChance;
		for (NetherBiome biome : GENERATOR)
			if (biome.canGenerate(chance))
				return biome;
		return BIOME_EMPTY_NETHER;
	}

	public static NetherBiome getFromBiome(Biome biome) {
		return MUTABLE.getOrDefault(biome, BIOME_EMPTY_NETHER);
	}

	public static ArrayList<NetherBiome> getRegisteredBiomes() {
		return REGISTRY;
	}

	/**
	 * External mod API. Register common biome with generation chance (default
	 * value).
	 * 
	 * @param biome
	 *            - {@link NetherBiome} itself
	 * @param chance
	 *            - default value of biome generation chance. Additive for all
	 *            biomes (actual chance = <this value> / summ(<other biome
	 *            chances>))
	 */
	public static void registerModBiome(NetherBiome biome, float chance) {
		String regName = biome.getRegistryName();
		float genChance = Config.getFloat("biomes." + biome.getNamespace() + ".main", regName + "_chance", chance);
		if (genChance > 0) {
			maxChance += genChance;
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getNamespace() + "." + regName, "plants_and_structures_density", 1));
			biome.setGenChance(maxChance);
			biome.build();
			REGISTRY.add(biome);
			ALL_BIOMES.add(biome);
			DEF_CHANCES_MAIN.put(biome, chance);
			registerBiomeDirect(regName, biome);
		}
	}

	/**
	 * External mod API. Register edge for {@link NetherBiome}.
	 * 
	 * @param biome
	 *            - {@link NetherBiome} itself
	 * @param mainBiome
	 *            - parent {@link NetherBiome}
	 * @param size
	 *            - width of biome edge in blocks
	 */
	public static void registerModEdgeBiome(NetherBiome biome, NetherBiome mainBiome, int size) {
		String regName = biome.getRegistryName();
		int sizeConf = Config.getInt("biomes." + biome.getNamespace() + ".edge", regName + "_size", size);
		if (sizeConf > 0) {
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getNamespace() + "." + regName, "plants_and_structures_density", 1));
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(sizeConf);
			biome.build();
			DEF_SIZE_EDGE.put(biome, sizeConf);
			ALL_BIOMES.add(biome);
			registerBiomeDirect(regName, biome);
		}
	}

	/**
	 * External mod API. Register biome variation for {@link NetherBiome} (as a
	 * sub-biome) with different generation chance. Main Biome always have its
	 * chance as 1.0
	 * 
	 * @param biome
	 *            - {@link NetherBiome} itself
	 * @param mainBiome
	 *            - parent {@link NetherBiome}
	 * @param chance
	 *            - chance of biome to appear instead of main biome (default
	 *            value). Additive for all sub-biomes (actual chance = <this
	 *            value> / summ(<other sub-biome chances>))
	 */
	public static void registerModSubBiome(NetherBiome biome, NetherBiome mainBiome, float chance) {
		String regName = biome.getRegistryName();
		chance = Config.getFloat("biomes." + biome.getNamespace() + ".variation", regName + "_chance", chance);
		if (chance > 0) {
			biome.setPlantDensity(Config.getFloat("generator.biome." + biome.getNamespace() + "." + regName, "plants_and_structures_density", 1));
			mainBiome.addSubBiome(biome, chance);
			biome.build();
			DEF_CHANCES_SUB.put(biome, chance);
			ALL_BIOMES.add(biome);
			registerBiomeDirect(regName, biome);
		}
	}

	public static float getDefaultMainChance(NetherBiome biome) {
		Float val = DEF_CHANCES_MAIN.get(biome);
		return val == null ? 1 : val.floatValue();
	}

	public static float getDefaultSubChance(NetherBiome biome) {
		Float val = DEF_CHANCES_SUB.get(biome);
		return val == null ? 1 : val.floatValue();
	}

	public static int getDefaultEdgeSize(NetherBiome biome) {
		Integer val = DEF_SIZE_EDGE.get(biome);
		return val == null ? 1 : val.intValue();
	}

	public static ArrayList<NetherBiome> getAllBiomes() {
		return ALL_BIOMES;
	}
}
