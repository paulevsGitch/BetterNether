package paulevs.betternether.mixin.common;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.NetherFortressFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.world.CityHelper;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin {
	@Inject(method = "isFeatureChunk", at = @At("HEAD"), cancellable = true)
	private void checkCity(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, WorldgenRandom chunkRandom, ChunkPos chunkPos, ChunkPos chunkPos2, NoneFeatureConfiguration defaultFeatureConfig,
						   LevelHeightAccessor heightLimitView, CallbackInfoReturnable<Boolean> info) {
		if (CityHelper.stopStructGen(chunkPos.x, chunkPos.z, chunkGenerator, worldSeed, chunkRandom)) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
