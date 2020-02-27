package paulevs.betternether.registers;

import java.util.ArrayList;
import java.util.Random;

import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.biomes.NetherBiomeGravelDesert;
import paulevs.betternether.biomes.NetherBiomeJungle;
import paulevs.betternether.biomes.NetherBoneReef;
import paulevs.betternether.biomes.NetherGrasslands;
import paulevs.betternether.biomes.NetherMagmaLand;
import paulevs.betternether.biomes.NetherMushroomForest;
import paulevs.betternether.biomes.NetherMushroomForestEdge;
import paulevs.betternether.biomes.NetherPoorGrasslands;
import paulevs.betternether.biomes.NetherSoulForest;
import paulevs.betternether.biomes.NetherSoulPlain;
import paulevs.betternether.biomes.NetherSwampland;
import paulevs.betternether.biomes.NetherSwamplandTerraces;
import paulevs.betternether.biomes.NetherWartForest;
import paulevs.betternether.biomes.NetherWartForestEdge;
import paulevs.betternether.config.Config;

public class BiomesRegister
{
	private static final ArrayList<NetherBiome> REGISTRY = new ArrayList<NetherBiome>();
	
	public static final NetherBiome BIOME_EMPTY_NETHER = new NetherBiome("Empty Nether");
	public static final NetherBiome BIOME_GRAVEL_DESERT = new NetherBiomeGravelDesert("Gravel Desert");
	public static final NetherBiome BIOME_NETHER_JUNGLE = new NetherBiomeJungle("Nether Jungle");
	public static final NetherBiome BIOME_WART_FOREST = new NetherWartForest("Wart Forest");
	public static final NetherBiome BIOME_GRASSLANDS = new NetherGrasslands("Nether Grasslands");
	public static final NetherBiome BIOME_MUSHROOM_FOREST = new NetherMushroomForest("Nether Mushroom Forest");
	public static final NetherBiome BIOME_MUSHROOM_FOREST_EDGE = new NetherMushroomForestEdge("Nether Mushroom Forest Edge");
	public static final NetherBiome BIOME_WART_FOREST_EDGE = new NetherWartForestEdge("Nether Wart Forest Edge");
	public static final NetherBiome BIOME_BONE_REEF = new NetherBoneReef("Bone Reef");
	public static final NetherBiome BIOME_POOR_GRASSLANDS = new NetherPoorGrasslands("Poor Nether Grasslands");
	public static final NetherBiome NETHER_SWAMPLAND = new NetherSwampland("Nether Swampland");
	public static final NetherBiome NETHER_SWAMPLAND_TERRACES = new NetherSwamplandTerraces("Nether Swampland Terraces");
	public static final NetherBiome MAGMA_LAND = new NetherMagmaLand("Magma Land");
	public static final NetherBiome SOUL_PLAIN = new NetherSoulPlain("Soul Plain");
	public static final NetherBiome SOUL_FOREST = new NetherSoulForest("Soul Forest");
	
	private static int maxChance = 0;
	
	public static void register()
	{
		registerBiome(BIOME_EMPTY_NETHER);
		registerBiome(BIOME_GRAVEL_DESERT);
		registerBiome(BIOME_NETHER_JUNGLE);
		registerBiome(BIOME_WART_FOREST);
		registerBiome(BIOME_GRASSLANDS);
		registerBiome(BIOME_MUSHROOM_FOREST);
		registerEdgeBiome(BIOME_MUSHROOM_FOREST_EDGE, BIOME_MUSHROOM_FOREST, 2);
		registerEdgeBiome(BIOME_WART_FOREST_EDGE, BIOME_WART_FOREST, 2);
		registerSubBiome(BIOME_BONE_REEF, BIOME_GRASSLANDS, 0.3F);
		registerSubBiome(BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS, 0.3F);
		registerBiome(NETHER_SWAMPLAND);
		registerSubBiome(NETHER_SWAMPLAND_TERRACES, NETHER_SWAMPLAND, 1F);
		registerBiome(MAGMA_LAND);
		//registerBiome(SOUL_PLAIN, 0.5F);
		//registerSubBiome(SOUL_FOREST, SOUL_PLAIN, 1F);
		registerSubBiome(SOUL_PLAIN, BIOME_WART_FOREST, 1F);
	}
	
	public static void registerBiome(NetherBiome biome)
	{
		String regName = biome.getRegistryName();
		if (Config.getBoolean("biomes", regName, true))
		{
			float chance = Config.getFloat("biomes", regName + "_chance", 1);
			maxChance += chance;
			biome.setGenChance(maxChance);
			biome.build();
			REGISTRY.add(biome);
		}
	}
	
	public static void registerBiome(NetherBiome biome, float chance)
	{
		String regName = biome.getRegistryName();
		if (Config.getBoolean("biomes", regName, true))
		{
			float ch = Config.getFloat("biomes", regName + "_chance", chance);
			maxChance += ch;
			biome.setGenChance(maxChance);
			biome.build();
			REGISTRY.add(biome);
		}
	}
	
	public static void registerEdgeBiome(NetherBiome biome, NetherBiome mainBiome, int size)
	{
		if (Config.getBoolean("biomes", biome.getRegistryName(), true))
		{
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(size);
			biome.build();
		}
	}
	
	public static void registerSubBiome(NetherBiome biome, NetherBiome mainBiome, float chance)
	{
		String regName = biome.getRegistryName();
		if (Config.getBoolean("biomes", regName, true))
		{
			chance = Config.getFloat("biomes", regName + "_subchance", chance);
			mainBiome.addSubBiome(biome, chance);
			biome.build();
		}
	}
	
	public static NetherBiome getBiome(Random random)
	{
		float chance = random.nextFloat() * maxChance;
		for (NetherBiome biome: REGISTRY)
			if (biome.canGenerate(chance))
				return biome;
		return REGISTRY.get(0);
	}

	public static int getBiomeCount()
	{
		return REGISTRY.size();
	}

	public static ArrayList<NetherBiome> getBiomesList()
	{
		return REGISTRY;
	}
}
