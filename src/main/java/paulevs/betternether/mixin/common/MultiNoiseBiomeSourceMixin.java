package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.biome.source.MultiNoiseBiomeSource;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin
{
	/*@Inject(method = "method_28467", at = @At("HEAD"), cancellable = true)
	private static void replaceGenerator(long seed, CallbackInfoReturnable<MultiNoiseBiomeSource> info)
	{
		List<Biome> newList = new ArrayList<Biome>();
		for (NetherBiome nb: BiomesRegistry.getRegisteredBiomes())
		{
			newList.add(nb.getBiome());
		}
			
		ImmutableList<Pair<MixedNoisePoint, Biome>> biomes = newList.stream().flatMap((biome) -> biome.streamNoises().map((point) -> Pair.of(point, biome))).collect(ImmutableList.toImmutableList());
		Optional<Preset> optional = Optional.of(Preset.NETHER);
		info.setReturnValue(new NetherBiomeSource(seed, biomes, optional, true));
		info.cancel();
	}*/
}