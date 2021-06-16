package paulevs.betternether.mixin.common;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.NetherFortressFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.world.CityHelper;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin {
	@Inject(method = "shouldStartAt", at = @At("HEAD"), cancellable = true)
	private void checkCity(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, DefaultFeatureConfig defaultFeatureConfig,
						   HeightLimitView heightLimitView, CallbackInfoReturnable<Boolean> info) {
		if (CityHelper.stopStructGen(chunkPos.x, chunkPos.z, chunkGenerator, worldSeed, chunkRandom)) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
