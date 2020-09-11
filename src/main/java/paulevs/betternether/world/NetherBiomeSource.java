package paulevs.betternether.world;

import java.util.Collections;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.registry.BiomesRegistry;

public class NetherBiomeSource extends BiomeSource
{
	public static final Codec<NetherBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((theEndBiomeSource) -> {
			return theEndBiomeSource.biomeRegistry;
		}), Codec.LONG.fieldOf("seed").stable().forGetter((theEndBiomeSource) -> {
			return theEndBiomeSource.seed;
		})).apply(instance, instance.stable(NetherBiomeSource::new));
	});
	private BiomeMap map;
	private final long seed;
	private final Registry<Biome> biomeRegistry;

	public NetherBiomeSource(Registry<Biome> biomeRegistry, long seed)
	{
		super(Collections.emptyList());
		this.seed = seed;
		this.map = new BiomeMap(seed, BNWorldGenerator.biomeSizeXZ, BNWorldGenerator.biomeSizeY, BNWorldGenerator.volumetric);
		this.biomeRegistry = biomeRegistry;
		
		for (NetherBiome netherBiome: BiomesRegistry.getAllBiomes())
			BiomesRegistry.MUTABLE.put(biomeRegistry.getOrThrow(BiomesRegistry.getBiomeKey(netherBiome)), netherBiome);
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ)
	{
		NetherBiome netherBiome = map.getBiome(biomeX << 2, biomeY << 2, biomeZ << 2);
		if (biomeX == 0 && biomeZ == 0)
		{
			map.clearCache();
		}
		return biomeRegistry.getOrThrow(BiomesRegistry.getBiomeKey(netherBiome));
	}

	@Override
	public BiomeSource withSeed(long seed)
	{
		return new NetherBiomeSource(biomeRegistry, seed);
	}

	@Override
	protected Codec<? extends BiomeSource> getCodec()
	{
		return CODEC;
	}
}
