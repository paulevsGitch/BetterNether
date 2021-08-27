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
import ru.bclib.world.biomes.BCLBiome;
import ru.bclib.world.generator.BCLibNetherBiomeSource;
import ru.bclib.world.generator.BiomeMap;

public class NetherBiomeSource extends BCLibNetherBiomeSource {
	public static final Codec<NetherBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((theEndBiomeSource) -> {
			return theEndBiomeSource.biomeRegistry;
		}), Codec.LONG.fieldOf("seed").stable().forGetter((theEndBiomeSource) -> {
			return theEndBiomeSource.seed;
		})).apply(instance, instance.stable(NetherBiomeSource::new));
	});
	
	private final Registry<Biome> biomeRegistry;
	private final long seed;
	
	public NetherBiomeSource(Registry<Biome> biomeRegistry, long seed) {
		super(biomeRegistry, seed);
		
		this.biomeRegistry = biomeRegistry;
		this.seed = seed;
	}
	
	public static void register() {
		Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(BetterNether.MOD_ID, "nether_biome_source"), NetherBiomeSource.CODEC);
	}
}
