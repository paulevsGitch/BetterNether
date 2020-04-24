package paulevs.betternether.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess.Storage;
import net.minecraft.world.biome.source.BiomeAccessType;
import paulevs.betternether.noise.OpenSimplexNoise;

public enum NetherBiomeAccessType implements BiomeAccessType
{
	INSTANCE;
	
	private static final OpenSimplexNoise NOISE_X = new OpenSimplexNoise(0);
	private static final OpenSimplexNoise NOISE_Y = new OpenSimplexNoise(1);
	private static final OpenSimplexNoise NOISE_Z = new OpenSimplexNoise(2);
	
	@Override
	public Biome getBiome(long seed, int x, int y, int z, Storage storage)
	{
		double px = x * 0.2;
		double py = y * 0.2;
		double pz = z * 0.2;
		int nx = (int) (NOISE_X.eval(pz, py) * 6 + x);
		int ny = (int) (NOISE_Y.eval(px, pz) * 6 + y);
		int nz = (int) (NOISE_Z.eval(py, px) * 6 + z);
		return storage.getBiomeForNoiseGen(nx >> 2, ny >> 2, nz >> 2);
		//return storage.getBiomeForNoiseGen(x >> 2, y >> 2, z >> 2);
	}
}
