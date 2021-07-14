package paulevs.betternether.world;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import com.google.common.collect.Lists;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import paulevs.betternether.BetterNether;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Configs;
import paulevs.betternether.mixin.common.GenerationSettingsAccessor;
import paulevs.betternether.registry.BiomesRegistry;

public class NetherBiomeSource extends BiomeSource {
	public static final Codec<NetherBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((theEndBiomeSource) -> {
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
		if (Configs.GENERATOR.getBoolean("generator.world.cities", "generate", true)) {
			this.possibleBiomes.forEach((biome) -> {
				GenerationSettingsAccessor accessor = (GenerationSettingsAccessor) biome.getGenerationSettings();
				List<Supplier<ConfiguredStructureFeature<?, ?>>> structures = Lists.newArrayList(accessor.getStructureStarts());
				structures.add(() -> { return BNWorldGenerator.CITY_CONFIGURED; });
				accessor.setStructureStarts(structures);
			});
		}
	}
	
	private static List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
		List<Biome> result = Lists.newArrayList();
		biomeRegistry.forEach((biome) -> {
			if (biome.getBiomeCategory() == BiomeCategory.NETHER) {
				result.add(biome);
			}
		});
		return result;
	}

	@Override
	public Biome getNoiseBiome(int biomeX, int biomeY, int biomeZ) {
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
	protected Codec<? extends BiomeSource> codec() {
		return CODEC;
	}

	public static void register() {
		Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(BetterNether.MOD_ID, "nether_biome_source"), CODEC);
	}
}
