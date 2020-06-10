package paulevs.betternether.mixin.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.MixedNoisePoint;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource.Preset;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.registry.BiomesRegistry;
import paulevs.betternether.world.NetherBiomeSource;

@Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin
{
	@Inject(method = "method_28467", at = @At("HEAD"), cancellable = true)
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
	}
}