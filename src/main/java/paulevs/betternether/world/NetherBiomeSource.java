package paulevs.betternether.world;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import paulevs.betternether.BetterNether;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.mixin.common.GenerationSettingsAccessor;
import paulevs.betternether.registry.BiomesRegistry;

public class NetherBiomeSource extends BiomeSource {
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

	public NetherBiomeSource(Registry<Biome> biomeRegistry, long seed) {
		super(getBiomes(biomeRegistry));
		this.seed = seed;
		this.map = new BiomeMap(seed, BNWorldGenerator.biomeSizeXZ, BNWorldGenerator.biomeSizeY, BNWorldGenerator.volumetric);
		this.biomeRegistry = biomeRegistry;
		BiomesRegistry.mutateRegistry(biomeRegistry);
		this.biomes.forEach((biome) -> {
			GenerationSettingsAccessor accessor = (GenerationSettingsAccessor) biome.getGenerationSettings();
			List<Supplier<ConfiguredStructureFeature<?, ?>>> structures = Lists.newArrayList(accessor.getStructureFeatures());
			structures.add(() -> { return BNWorldGenerator.CITY_CONFIGURED; });
			accessor.setStructureFeatures(structures);
		});
	}
	
	private static List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
		List<Biome> result = Lists.newArrayList();
		biomeRegistry.forEach((biome) -> {
			if (biome.getCategory() == Category.NETHER) {
				result.add(biome);
			}
		});
		return result;
	}

	@Override
	public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
		NetherBiome netherBiome = map.getBiome(biomeX << 2, biomeY << 2, biomeZ << 2);
		if (biomeX == 0 && biomeZ == 0) {
			map.clearCache();
		}
		return netherBiome.getActualBiome();
	}

	@Override
	public BiomeSource withSeed(long seed) {
		return new NetherBiomeSource(biomeRegistry, seed);
	}

	@Override
	protected Codec<? extends BiomeSource> getCodec() {
		return CODEC;
	}

	public static void register() {
		Registry.register(Registry.BIOME_SOURCE, new Identifier(BetterNether.MOD_ID, "nether_biome_source"), CODEC);
	}
}
