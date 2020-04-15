package paulevs.betternether.world;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceConfig;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.BiomesRegistry;

public class NetherBiomeSource extends BiomeSource
{
	private BiomeMap map;
	
	protected NetherBiomeSource(MultiNoiseBiomeSourceConfig config)
	{
		super(buildBiomes());
		int sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		int sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		map = new BiomeMap(config.getSeed(), sizeXZ, sizeY);
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ)
	{
		return map.getBiome(biomeX, biomeY, biomeZ);
	}
	
	private static Set<Biome> buildBiomes()
	{
		Set<Biome> biomes = new HashSet<Biome>();
		for (NetherBiome nb: BiomesRegistry.getRegisteredBiomes())
		{
			biomes.add(nb.getBiome());
		}
		return biomes;
	}
}
