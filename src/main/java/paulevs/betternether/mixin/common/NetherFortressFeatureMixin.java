package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.NetherFortressFeature;
import paulevs.betternether.world.CityHelper;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin {
	@Inject(method = "shouldStartAt", at = @At("HEAD"), cancellable = true)
	private void checkCity(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig,
			CallbackInfoReturnable<Boolean> info) {
		if (CityHelper.stopStructGen(chunkX, chunkZ, chunkGenerator, worldSeed, chunkRandom)) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
