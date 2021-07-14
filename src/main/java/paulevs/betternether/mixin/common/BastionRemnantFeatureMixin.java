package paulevs.betternether.mixin.common;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.BastionFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.world.CityHelper;

@Mixin(BastionFeature.class)
public class BastionRemnantFeatureMixin {
	@Inject(method = "isFeatureChunk", at = @At("HEAD"), cancellable = true)
	private void checkCity(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, WorldgenRandom chunkRandom, ChunkPos pos, Biome biome, ChunkPos chunkPos, JigsawConfiguration structurePoolFeatureConfig,
		 LevelHeightAccessor heightLimitView,CallbackInfoReturnable<Boolean> info) {
		if (CityHelper.stopStructGen(pos.x, pos.z, chunkGenerator, worldSeed, chunkRandom)) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
