package paulevs.betternether.biomes;

import java.util.ArrayList;
import java.util.List;

import paulevs.betternether.config.ConfigLoader;

public class BiomeRegister
{
	public static int biomeCount;
	public static NetherBiome[] BiomeRegistry;
	public static NetherBiome BIOME_EMPTY_NETHER;
	public static NetherBiome BIOME_GRAVEL_DESERT;
	public static NetherBiome BIOME_NETHER_JUNGLE;
	public static NetherBiome BIOME_WART_FOREST;
	public static NetherBiome BIOME_GRASSLANDS;
	public static NetherBiome BIOME_MUSHROOM_FOREST;
	public static NetherBiome BIOME_MUSHROOM_FOREST_EDGE;
	public static NetherBiome BIOME_WART_FOREST_EDGE;
	public static NetherBiome BIOME_BONE_REEF;
	public static NetherBiome BIOME_POOR_GRASSLANDS;
	
	public static void registerBiomes()
	{
		List<NetherBiome> biomes = new ArrayList<NetherBiome>();
		BIOME_EMPTY_NETHER = registerBiome(new NetherBiome("Empty Nether"), biomes);
		BIOME_GRAVEL_DESERT = registerBiome(new NetherBiomeGravelDesert("Gravel Desert"), biomes);
		BIOME_NETHER_JUNGLE = registerBiome(new NetherBiomeJungle("Nether Jungle"), biomes);
		BIOME_WART_FOREST = registerBiome(new NetherWartForest("Wart Forest"), biomes);
		BIOME_GRASSLANDS = registerBiome(new NetherGrasslands("Nether Grasslands"), biomes);
		BIOME_MUSHROOM_FOREST = registerBiome(new NetherMushroomForest("Nether Mushroom Forest"), biomes);
		BIOME_MUSHROOM_FOREST_EDGE = registerEdgeBiome(new NetherMushroomForestEdge("Nether Mushroom Forest Edge"), BIOME_MUSHROOM_FOREST, 10);
		BIOME_WART_FOREST_EDGE = registerEdgeBiome(new NetherWartForestEdge("Nether Mushroom Forest Edge"), BIOME_WART_FOREST, 9);
		BIOME_BONE_REEF = registerSubBiome(new NetherBoneReef("Bone Reef"), BIOME_GRASSLANDS);
		BIOME_POOR_GRASSLANDS = registerSubBiome(new NetherPoorGrasslands("Poor Nether Grasslands"), BIOME_GRASSLANDS);
		biomeCount = biomes.size();
		BiomeRegistry = new NetherBiome[biomeCount];
		for (int i = 0; i < biomeCount; i++)
			BiomeRegistry[i] = biomes.get(i);
	}
	
	private static NetherBiome registerBiome(NetherBiome biome, List<NetherBiome> biomes)
	{
		if (ConfigLoader.mustInitBiome())
		{
			biomes.add(biome);
			return biome;
		}
		else
			return null;
	}
	
	private static NetherBiome registerEdgeBiome(NetherBiome biome, NetherBiome mainBiome, int size)
	{
		if (ConfigLoader.mustInitBiome() && mainBiome != null)
		{
			mainBiome.setEdge(biome);
			mainBiome.setEdgeSize(size);
			return biome;
		}
		else
			return null;
	}
	
	private static NetherBiome registerSubBiome(NetherBiome biome, NetherBiome mainBiome)
	{
		if (ConfigLoader.mustInitBiome() && mainBiome != null)
		{
			mainBiome.addSubBiome(biome);
			return biome;
		}
		else
			return null;
	}
	
	public static NetherBiome getBiomeID(int id)
	{
		return BiomeRegistry[id];
	}
	
	public static NetherBiome[] getBiomes()
	{
		return BiomeRegistry;
	}
}
