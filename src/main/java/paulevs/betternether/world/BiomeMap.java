package paulevs.betternether.world;

import java.util.HashMap;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkRandom;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.noise.OpenSimplexNoise;

public class BiomeMap
{
	protected static final int DEPTH = 6;
	
	private static final HashMap<ChunkPos, BiomeChunk> MAPS = new HashMap<ChunkPos, BiomeChunk>();
	
	private static final ChunkRandom RANDOM = new ChunkRandom();
	
	private OpenSimplexNoise noiseX;
	private OpenSimplexNoise noiseY;
	private OpenSimplexNoise noiseZ;
	
	public BiomeMap(long seed)
	{
		RANDOM.setSeed(seed);
		noiseX = new OpenSimplexNoise(RANDOM.nextLong());
		noiseY = new OpenSimplexNoise(RANDOM.nextLong());
		noiseZ = new OpenSimplexNoise(RANDOM.nextLong());
	}
	
	public void clearCache()
	{
		System.out.println("Maps: " + MAPS.size());
		MAPS.clear();
	}
	
	public NetherBiome getBiome(int bx, int by, int bz)
	{
		int x = bx;
		int y = by;
		int z = bz;
		int nx = x;
		int ny = y;
		int nz = z;
		
		for (int i = 0; i < DEPTH; i++)
		{
			nx = (int) Math.round(x + noiseX.eval(x, y, z) * 0.5 + 0.5) >> 1;
			ny = (int) Math.round(y + noiseY.eval(x, y, z) * 0.5 + 0.5) >> 1;
			nz = (int) Math.round(z + noiseZ.eval(x, y, z) * 0.5 + 0.5) >> 1;
			
			x = nx;
			y = ny;
			z = nz;
		}
		
		ChunkPos cpos = new ChunkPos(
				(int) Math.floor((double) x / BiomeChunk.WIDTH),
				(int) Math.floor((double) z / BiomeChunk.WIDTH));
		BiomeChunk chunk = MAPS.get(cpos);
		if (chunk == null)
		{
			RANDOM.setSeed(cpos.x, cpos.z);
			chunk = new BiomeChunk(RANDOM);
			MAPS.put(cpos, chunk);
		}
		
		return chunk.getBiome(x, y, z);
	}

	public NetherBiome getBiome(BlockPos pos)
	{
		return getBiome(pos.getX(), pos.getY(), pos.getZ());
	}
}
