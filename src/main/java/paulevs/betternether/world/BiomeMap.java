package paulevs.betternether.world;

import java.util.HashMap;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkRandom;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.noise.OpenSimplexNoise;

public class BiomeMap
{
	private static final HashMap<ChunkPos, BiomeChunk> MAPS = new HashMap<ChunkPos, BiomeChunk>();
	private static final ChunkRandom RANDOM = new ChunkRandom();
	
	private final int sizeXZ;
	private final int sizeY;
	protected final int maxHeight;
	private final int depth;
	private final int size;
	private final OpenSimplexNoise noiseX;
	private final OpenSimplexNoise noiseY;
	private final OpenSimplexNoise noiseZ;
	
	public BiomeMap(long seed, int sizeXZ, int sizeY)
	{
		RANDOM.setSeed(seed);
		noiseX = new OpenSimplexNoise(RANDOM.nextLong());
		noiseY = new OpenSimplexNoise(RANDOM.nextLong());
		noiseZ = new OpenSimplexNoise(RANDOM.nextLong());
		this.sizeXZ = sizeXZ;
		this.sizeY = sizeY;
		maxHeight = 128 / sizeY;
		
		depth = (int) Math.ceil(Math.log(Math.max(sizeXZ, sizeY)) / Math.log(2)) - 1;
		size = 1 << depth;
	}
	
	public void clearCache()
	{
		System.out.println("Maps: " + MAPS.size());
		MAPS.clear();
	}
	
	public NetherBiome getBiome(int bx, int by, int bz)
	{
		int x = bx * size / sizeXZ;
		int y = by * size / sizeY;
		int z = bz * size / sizeXZ;
		int nx = x;
		int ny = y;
		int nz = z;
		
		for (int i = 0; i < depth; i++)
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
			chunk = new BiomeChunk(this, RANDOM);
			MAPS.put(cpos, chunk);
		}
		
		return chunk.getBiome(x, y, z);
	}

	public NetherBiome getBiome(BlockPos pos)
	{
		return getBiome(pos.getX(), pos.getY(), pos.getZ());
	}
}
