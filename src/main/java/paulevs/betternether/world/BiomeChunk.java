package paulevs.betternether.world;

import java.util.Random;

import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.registers.BiomesRegister;

public class BiomeChunk
{
	protected static final int WIDTH = 16;
	protected static final int HEIGHT = (128 * 3) >> BiomeMap.DEPTH;
	private static final int SM_WIDTH = WIDTH >> 1;
	private static final int SM_HEIGHT = HEIGHT >> 1;
	private static final int MASK_A = SM_WIDTH - 1;
	private static final int MASK_B = SM_HEIGHT - 1;
	private static final int MASK_C = WIDTH - 1;
	private static final int MASK_D = HEIGHT - 1;
	private static final NetherBiome[][][] PRE_BIO = new NetherBiome[SM_HEIGHT][SM_WIDTH][SM_WIDTH];
	
	private NetherBiome[][][] biomes = new NetherBiome[HEIGHT][WIDTH][WIDTH];
	
	public BiomeChunk(Random random)
	{
		for (int y = 0; y < SM_HEIGHT; y++)
			for (int x = 0; x < SM_WIDTH; x++)
				for (int z = 0; z < SM_WIDTH; z++)
					PRE_BIO[y][x][z] = BiomesRegister.getBiomeID(random.nextInt(BiomesRegister.getBiomeCount()));
		
		biomes = new NetherBiome[HEIGHT][WIDTH][WIDTH];
		for (int y = 0; y < HEIGHT; y++)
			for (int x = 0; x < WIDTH; x++)
				for (int z = 0; z < WIDTH; z++)
					biomes[y][x][z] = PRE_BIO[offsetY(y, random)][offsetXZ(y, random)][offsetXZ(y, random)].getSubBiome(random);
	}

	public NetherBiome getBiome(int x, int y, int z)
	{
		return biomes[clamp(y)][x & MASK_C][z & MASK_C];
	}
	
	private int offsetXZ(int x, Random random)
	{
		return ((x + random.nextInt(2)) >> 2) & MASK_A;
	}
	
	private int offsetY(int y, Random random)
	{
		return ((y + random.nextInt(2)) >> 2) & MASK_B;
	}
	
	private int clamp(int y)
	{
		return y < 0 ? 0 : y > MASK_D ? MASK_D : y;
	}
}
