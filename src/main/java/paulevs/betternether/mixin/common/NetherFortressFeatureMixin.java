package paulevs.betternether.mixin.common;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.NetherFortressFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier.Context;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.world.structures.city.CityHelper;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin {
	@Inject(method = "checkLocation", at = @At("HEAD"), cancellable = true)
	private static void checkCity(Context<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
		final ChunkPos chunkPos = context.chunkPos();
		final long seed = context.seed();
		final WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(0L));
		chunkRandom.setLargeFeatureSeed(seed, chunkPos.x, chunkPos.z);
		
		if (CityHelper.stopStructGen(chunkPos.x, chunkPos.z, context.chunkGenerator(), seed, chunkRandom)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
