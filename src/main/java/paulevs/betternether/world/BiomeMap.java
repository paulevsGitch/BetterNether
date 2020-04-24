package paulevs.betternether.world;

import java.util.HashMap;

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
	private final boolean volumetric;
	
	public BiomeMap(long seed, int sizeXZ, int sizeY, boolean volumetric)
	{
		RANDOM.setSeed(seed);
		noiseX = new OpenSimplexNoise(RANDOM.nextLong());
		noiseY = new OpenSimplexNoise(RANDOM.nextLong());
		noiseZ = new OpenSimplexNoise(RANDOM.nextLong());
		this.sizeXZ = sizeXZ;
		this.sizeY = sizeY;
		this.volumetric = volumetric;
		maxHeight = volumetric ? (int) Math.ceil(128F / sizeY) : 1;
		
		depth = (int) Math.ceil(Math.log(Math.max(sizeXZ, sizeY)) / Math.log(2)) - 2;
		size = 1 << depth;
	}
	
	public void clearCache()
	{
		if (MAPS.size() > 16)
			MAPS.clear();
	}
	
	private NetherBiome getRawBiome(int bx, int by, int bz)
	{
		double x = bx * size / sizeXZ;
		double y = volumetric ? by * size / sizeY : 0;
		double z = bz * size / sizeXZ;
		double nx = x;
		double ny = y;
		double nz = z;
		
		for (int i = 0; i < depth; i++)
		{
			if (volumetric)
			{
				nx = (x + noiseX.eval(y + i, z + i)) / 2;
				nz = (z + noiseZ.eval(x + i, y + i)) / 2;
				ny = (y + noiseY.eval(x + i, z + i)) / 2;
				y = ny;
			}
			else
			{
				nx = (x + noiseX.eval(x + i, z + i)) / 2;
				nz = (z + noiseZ.eval(z + i, x + i)) / 2;
			}
			
			x = nx;
			z = nz;
		}
		
		ChunkPos cpos = new ChunkPos(
				(int) Math.floor((double) x / BiomeChunk.WIDTH),
				(int) Math.floor((double) z / BiomeChunk.WIDTH));
		BiomeChunk chunk = MAPS.get(cpos);
		if (chunk == null)
		{
			RANDOM.setTerrainSeed(cpos.x, cpos.z);
			chunk = new BiomeChunk(this, RANDOM);
			MAPS.put(cpos, chunk);
		}
		
		return chunk.getBiome((int) x, (int) y, (int) z);
	}
	
	public NetherBiome getBiome(int x, int y, int z)
	{
		NetherBiome biome = getRawBiome(x, y > 30 ? y : 30, z);
		
		if (biome.hasEdge() || (biome.hasParrent() && biome.getParrentBiome().hasEdge()))
		{
			NetherBiome search = biome;
			if (biome.hasParrent())
				search = biome.getParrentBiome();
			int d = (int) Math.ceil(search.getEdgeSize() / 4F) << 2;
			
			boolean edge = !search.isSame(getRawBiome(x + d, y, z));
			edge = edge || !search.isSame(getRawBiome(x - d, y, z));
			edge = edge || !search.isSame(getRawBiome(x, y, z + d));
			edge = edge || !search.isSame(getRawBiome(x, y, z - d));
			edge = edge || !search.isSame(getRawBiome(x - 1, y, z - 1));
			edge = edge || !search.isSame(getRawBiome(x - 1, y, z + 1));
			edge = edge || !search.isSame(getRawBiome(x + 1, y, z - 1));
			edge = edge || !search.isSame(getRawBiome(x + 1, y, z + 1));
			if (!edge && volumetric)
			{
				edge = edge || !search.isSame(getRawBiome(x, y + d, z));
				edge = edge || !search.isSame(getRawBiome(x, y - d, z));
				edge = edge || !search.isSame(getRawBiome(x - d, y - d, z - d));
				edge = edge || !search.isSame(getRawBiome(x + d, y - d, z - d));
				edge = edge || !search.isSame(getRawBiome(x - d, y - d, z + d));
				edge = edge || !search.isSame(getRawBiome(x + d, y - d, z + d));
				edge = edge || !search.isSame(getRawBiome(x - d, y + d, z - d));
				edge = edge || !search.isSame(getRawBiome(x + d, y + d, z - d));
				edge = edge || !search.isSame(getRawBiome(x - d, y + d, z + d));
				edge = edge || !search.isSame(getRawBiome(x + d, y + d, z + d));
			}
			
			if (edge)
			{
				biome = search.getEdge();
			}
		}
		
		return biome;
	}
}
