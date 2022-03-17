package paulevs.betternether.world;

import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.world.biomes.BCLBiome;

import java.time.Instant;
import java.util.HashMap;

public class BiomeMap {
	private static final HashMap<ChunkPos, BiomeChunk> MAPS = new HashMap<ChunkPos, BiomeChunk>();
	private static final WorldgenRandom RANDOM = new WorldgenRandom(new LegacyRandomSource(Instant.now().getEpochSecond()));

	private final int sizeXZ;
	private final int sizeY;
	protected final int maxHeight;
	private final int depth;
	private final int size;
	private final OpenSimplexNoise noiseX;
	private final OpenSimplexNoise noiseY;
	private final OpenSimplexNoise noiseZ;
	private final boolean volumetric;

	public BiomeMap(long seed, int sizeXZ, int sizeY, boolean volumetric) {
		MAPS.clear();
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

	public void clearCache() {
		if (MAPS.size() > 16)
			MAPS.clear();
	}

	private BCLBiome getRawBiome(int bx, int by, int bz) {
		double x = bx * size / sizeXZ;
		double y = volumetric ? by * size / sizeY : 0;
		double z = bz * size / sizeXZ;
		double nx = x;
		double ny = y;
		double nz = z;

		double px = bx * 0.2;
		double py = by * 0.2;
		double pz = bz * 0.2;

		for (int i = 0; i < depth; i++) {
			nx = (x + noiseX.eval(px, pz)) / 2F;
			nz = (z + noiseZ.eval(px, pz)) / 2F;

			if (volumetric) {
				nz = (z + noiseY.eval(px, pz)) / 2F;

				y = ny;
				py = py / 2 + i;
			}

			x = nx;
			z = nz;

			px = px / 2 + i;
			pz = pz / 2 + i;
		}

		ChunkPos cpos = new ChunkPos(
				(int) Math.floor((double) x / BiomeChunk.WIDTH),
				(int) Math.floor((double) z / BiomeChunk.WIDTH));
		BiomeChunk chunk = MAPS.get(cpos);
		if (chunk == null) {
			RANDOM.setLargeFeatureWithSalt(0, cpos.x, cpos.z, 0);
			chunk = new BiomeChunk(this, RANDOM);
			MAPS.put(cpos, chunk);
		}

		return chunk.getBiome((int) x, Mth.clamp((int) y, 0, maxHeight - 1), (int) z);
	}

	public BCLBiome getBiome(int x, int y, int z) {
		BCLBiome biome = getRawBiome(x, y > 30 ? y : 30, z);

		if (biome.getEdge()!=null || (biome.getParentBiome()!=null && biome.getParentBiome().getEdge()!=null)) {
			BCLBiome search = biome;
			if (biome.getParentBiome()!=null) {
				search = biome.getParentBiome();
			}
			int d = (int) Math.ceil(search.getEdgeSize() / 4F) << 2;

			boolean edge = !search.isSame(getRawBiome(x + d, y, z));
			edge = edge || !search.isSame(getRawBiome(x - d, y, z));
			edge = edge || !search.isSame(getRawBiome(x, y, z + d));
			edge = edge || !search.isSame(getRawBiome(x, y, z - d));
			edge = edge || !search.isSame(getRawBiome(x - 1, y, z - 1));
			edge = edge || !search.isSame(getRawBiome(x - 1, y, z + 1));
			edge = edge || !search.isSame(getRawBiome(x + 1, y, z - 1));
			edge = edge || !search.isSame(getRawBiome(x + 1, y, z + 1));

			if (edge) {
				biome = search.getEdge();
			}
		}

		return biome;
	}
}
