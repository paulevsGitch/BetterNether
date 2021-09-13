package paulevs.betternether.world;

import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import paulevs.betternether.BetterNether;
import ru.bclib.world.generator.BCLibNetherBiomeSource;

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
