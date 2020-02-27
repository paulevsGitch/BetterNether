package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.Random;

import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.registers.BiomesRegister;

public class BiomeChunk
{
	protected static final int WIDTH = 16;
	private static final int SM_WIDTH = WIDTH >> 1;
	private static final int MASK_A = SM_WIDTH - 1;
	private static final int MASK_C = WIDTH - 1;
	private static final ArrayList<NetherBiome> SURROUNDING = new ArrayList<NetherBiome>(6);
	private static final ArrayList<NetherBiome> BIOME_NO_REPEATS = new ArrayList<NetherBiome>(BiomesRegister.getBiomeCount());
	
	private final int sm_height;
	private final int maxY;
	private final int maskB;
	private final NetherBiome[][][] PreBio;
	private final NetherBiome[][][] biomes;
	
	public BiomeChunk(BiomeMap map, Random random)
	{
		sm_height = clampOne(map.maxHeight >> 1);
		maskB = sm_height - 1;
		maxY = map.maxHeight - 1;
		PreBio = new NetherBiome[sm_height][SM_WIDTH][SM_WIDTH];
		biomes = new NetherBiome[map.maxHeight][WIDTH][WIDTH];
		
		for (int y = 0; y < sm_height; y++)
			for (int x = 0; x < SM_WIDTH; x++)
				for (int z = 0; z < SM_WIDTH; z++)
					PreBio[y][x][z] = BiomesRegister.getBiome(random);
		
		for (int y = 0; y < sm_height; y++)
			for (int x = 0; x < SM_WIDTH; x++)
				for (int z = 0; z < SM_WIDTH; z++)
					if (((x + y + z) & 1) == 0)
					{
						if (y - 1 >= 0)
							SURROUNDING.add(PreBio[y - 1][x][z]);
						if (y + 1 < sm_height)
							SURROUNDING.add(PreBio[y + 1][x][z]);
						
						if (x - 1 >= 0)
							SURROUNDING.add(PreBio[y][x - 1][z]);
						if (x + 1 < SM_WIDTH)
							SURROUNDING.add(PreBio[y][x + 1][z]);
						
						if (z - 1 >= 0)
							SURROUNDING.add(PreBio[y][x][z - 1]);
						if (z + 1 < SM_WIDTH)
							SURROUNDING.add(PreBio[y][x][z + 1]);
						
						PreBio[y][x][z] = getNonRepeat(random, PreBio[y][x][z]);
					}
		
		for (int y = 0; y < map.maxHeight; y++)
			for (int x = 0; x < WIDTH; x++)
				for (int z = 0; z < WIDTH; z++)
					biomes[y][x][z] = PreBio[offsetY(y, random)][offsetXZ(x, random)][offsetXZ(z, random)].getSubBiome(random);
	}

	public NetherBiome getBiome(int x, int y, int z)
	{
		return biomes[clamp(y)][x & MASK_C][z & MASK_C];
	}
	
	private int offsetXZ(int x, Random random)
	{
		return ((x + random.nextInt(2)) >> 1) & MASK_A;
	}
	
	private int offsetY(int y, Random random)
	{
		return ((y + random.nextInt(2)) >> 1) & maskB;
	}
	
	private int clamp(int y)
	{
		return y < 0 ? 0 : y > maxY ? maxY : y;
	}
	
	private int clampOne(int x)
	{
		return x < 1 ? 1 : x;
	}
	
	private NetherBiome getNonRepeat(Random random, NetherBiome center)
	{
		BIOME_NO_REPEATS.addAll(BiomesRegister.getBiomesList());
		BIOME_NO_REPEATS.removeAll(SURROUNDING);
		NetherBiome result = center;
		if (!BIOME_NO_REPEATS.isEmpty())
			result = BIOME_NO_REPEATS.get(random.nextInt(BIOME_NO_REPEATS.size()));
		BIOME_NO_REPEATS.clear();
		SURROUNDING.clear();
		return result;
	}
}
