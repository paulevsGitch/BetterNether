package org.betterx.betternether.mixin.common;

//@Mixin(BastionFeature.class)
public class BastionRemnantFeatureMixin {
    //TODO: 1.18.2 Find an alternative
	/*@Inject(method = "checkLocation", at = @At("HEAD"), cancellable = true)
	private static void checkCity(Context<JigsawConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
		final ChunkPos chunkPos = context.chunkPos();
		final long seed = context.seed();
		final WorldgenRandom chunkRandom = new WorldgenRandom(new LegacyRandomSource(0L));
		chunkRandom.setLargeFeatureSeed(seed, chunkPos.x, chunkPos.z);
		if (CityHelper.stopStructGen(chunkPos.x, chunkPos.z, context.chunkGenerator(), seed, chunkRandom)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}*/
}
