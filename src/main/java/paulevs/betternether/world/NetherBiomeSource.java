package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.BiomesRegistry;

public class NetherBiomeSource extends BiomeSource
{
	private BiomeMap map;
	
	public NetherBiomeSource(NetherBiomeSourceConfig config)
	{
		super(buildBiomes());
		int sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		int sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		map = new BiomeMap(config.getSeed(), sizeXZ, sizeY, config.isVolumetric());
	}
	
	private NetherBiomeSource(List<Biome> biomes, BiomeMap map)
	{
		super(biomes);
		this.map = map;
	}
	
	/*public NetherBiomeSource(long seed)
	{
		super(buildBiomes());
		int sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		int sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		map = new BiomeMap(config.getSeed(), sizeXZ, sizeY, config.isVolumetric());
	}*/

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ)
	{
		Biome biome = map.getBiome(biomeX << 2, biomeY << 2, biomeZ << 2).getBiome();
		if (biomeX == 0 && biomeY == 0 && biomeZ == 0)
			map.clearCache();
		return biome;
	}
	
	private static List<Biome> buildBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		for (NetherBiome nb: BiomesRegistry.getRegisteredBiomes())
		{
			biomes.add(nb.getBiome());
		}
		return biomes;
	}
	
	public NetherBiomeSource create(long seed)
	{
		return new NetherBiomeSource(this.biomes, this.map);
	}

	@Override
	protected Codec<? extends BiomeSource> method_28442()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BiomeSource withSeed(long seed)
	{
		// TODO Auto-generated method stub
		return null;
	}
}