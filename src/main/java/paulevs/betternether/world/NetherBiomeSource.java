package paulevs.betternether.world;

public class NetherBiomeSource// extends BiomeSource
{
	/*private BiomeMap map;
	
	public NetherBiomeSource(long seed, boolean volumetric)
	{
		super(seed, Collections.emptyList(), null);
		int sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		int sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		this.map = new BiomeMap(seed, sizeXZ, sizeY, volumetric);
	}
	
	private NetherBiomeSource(long seed, ImmutableList<Pair<MixedNoisePoint, Biome>> biomes, Optional<Preset> optional, BiomeMap map)
	{
		super(seed, biomes, optional);
		this.map = map;
	}

	public NetherBiomeSource(long seed, ImmutableList<Pair<MixedNoisePoint, Biome>> biomes, Optional<Preset> optional, boolean volumetric)
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

	@Override
	public BiomeSource withSeed(long seed)
	{
		ImmutableList<Pair<MixedNoisePoint, Biome>> biomes = this.biomes.stream().flatMap((biome) -> biome.streamNoises().map((point) -> Pair.of(point, biome))).collect(ImmutableList.toImmutableList());
		Optional<Preset> optional = Optional.of(Preset.NETHER);
		return new NetherBiomeSource(seed, biomes, optional, this.map);
	}*/
}