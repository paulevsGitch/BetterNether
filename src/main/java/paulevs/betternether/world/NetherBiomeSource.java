package paulevs.betternether.world;

import java.util.Collections;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import paulevs.betternether.config.Config;

public class NetherBiomeSource extends MultiNoiseBiomeSource
{
	private BiomeMap map;
	
	public NetherBiomeSource(long seed, boolean volumetric)
	{
		//super(buildBiomes());
		super(seed, Collections.emptyList(), null);
		int sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		int sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		this.map = new BiomeMap(seed, sizeXZ, sizeY, volumetric);
	}
	
	private NetherBiomeSource(long seed, ImmutableList<Pair<MixedNoisePoint, Biome>> biomes, Optional<class_5305> optional, BiomeMap map)
	{
		//super(biomes);
		super(seed, biomes, optional);
		this.map = map;
	}

	public NetherBiomeSource(long seed, ImmutableList<Pair<MixedNoisePoint, Biome>> biomes, Optional<class_5305> optional, boolean volumetric)
	{
		super(seed, biomes, optional);
		int sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		int sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		this.map = new BiomeMap(seed, sizeXZ, sizeY, volumetric);
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ)
	{
		Biome biome = map.getBiome(biomeX << 2, biomeY << 2, biomeZ << 2).getBiome();
		if (biomeX == 0 && biomeY == 0 && biomeZ == 0)
			map.clearCache();
		return biome;
	}
	
	/*private static List<Biome> buildBiomes()
	{
		List<Biome> biomes = new ArrayList<Biome>();
		for (NetherBiome nb: BiomesRegistry.getRegisteredBiomes())
		{
			biomes.add(nb.getBiome());
		}
		return biomes;
	}*/

	/*@Override
	protected Codec<? extends BiomeSource> method_28442()
	{
		return MultiNoiseBiomeSource.CODEC;
	}*/

	@Override
	public BiomeSource withSeed(long seed)
	{
		ImmutableList<Pair<MixedNoisePoint, Biome>> biomes = this.biomes.stream().flatMap((biome) -> biome.streamNoises().map((point) -> Pair.of(point, biome))).collect(ImmutableList.toImmutableList());
		Optional<class_5305> optional = Optional.of(MultiNoiseBiomeSource.class_5305.field_24723);
		return new NetherBiomeSource(seed, biomes, optional, this.map);
	}
}