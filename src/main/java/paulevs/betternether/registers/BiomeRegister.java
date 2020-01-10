package paulevs.betternether.registers;

import java.util.HashMap;

import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.biomes.NetherBiomeGravelDesert;
import paulevs.betternether.biomes.NetherBiomeJungle;
import paulevs.betternether.config.Config;

public class BiomeRegister
{
	private static int biomeCount = 0;
	public static final HashMap<Integer, NetherBiome> REGISTRY = new HashMap<Integer, NetherBiome>();
	
	public static final NetherBiome BIOME_EMPTY_NETHER = new NetherBiome("Empty Nether");
	public static final NetherBiome BIOME_GRAVEL_DESERT = new NetherBiomeGravelDesert("Gravel Desert");
	public static final NetherBiome BIOME_NETHER_JUNGLE = new NetherBiomeJungle("Nether Jungle");
	//public static final NetherBiome BIOME_WART_FOREST = new NetherWartForest("Wart Forest");
	//public static final NetherBiome BIOME_GRASSLANDS = new NetherGrasslands("Nether Grasslands");
	//public static final NetherBiome BIOME_MUSHROOM_FOREST = new NetherMushroomForest("Nether Mushroom Forest");
	//public static final NetherBiome BIOME_MUSHROOM_FOREST_EDGE = new NetherMushroomForestEdge("Nether Mushroom Forest Edge");
	//public static final NetherBiome BIOME_WART_FOREST_EDGE = new NetherWartForestEdge("Nether Wart Forest Edge");
	//public static final NetherBiome BIOME_BONE_REEF = new NetherBoneReef("Bone Reef");
	//public static final NetherBiome BIOME_POOR_GRASSLANDS = new NetherPoorGrasslands("Poor Nether Grasslands");
	
	public static void register()
	{
		registerBiome(BIOME_EMPTY_NETHER);
		registerBiome(BIOME_GRAVEL_DESERT);
		registerBiome(BIOME_NETHER_JUNGLE);
		//registerBiome(BIOME_WART_FOREST);
		//registerBiome(BIOME_GRASSLANDS);
		//registerBiome(BIOME_MUSHROOM_FOREST);
		//registerEdgeBiome(BIOME_MUSHROOM_FOREST_EDGE, BIOME_MUSHROOM_FOREST, 10);
		//registerEdgeBiome(BIOME_WART_FOREST_EDGE, BIOME_WART_FOREST, 9);
		//registerSubBiome(BIOME_BONE_REEF, BIOME_GRASSLANDS);
		//registerSubBiome(BIOME_POOR_GRASSLANDS, BIOME_GRASSLANDS);
	}
	
	protected static void registerBiome(NetherBiome biome)
	{
		if (Config.getBoolean("biomes", biome.getRegistryName(), true))
		{
			REGISTRY.put(biomeCount++, biome);
		}
	}
	
	protected static void registerEdgeBiome(NetherBiome biome, NetherBiome mainBiome, int size)
	{
		if (Config.getBoolean("biomes", biome.getRegistryName(), true))
		{
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(size);
		}
	}
	
	protected static void registerSubBiome(NetherBiome biome, NetherBiome mainBiome)
	{
		if (Config.getBoolean("biomes", biome.getRegistryName(), true))
		{
			mainBiome.addSubBiome(biome);
		}
	}
	
	public static NetherBiome getBiomeID(int id)
	{
		return REGISTRY.get(id);
	}

	public static int getBiomeCount()
	{
		return biomeCount;
	}
}
