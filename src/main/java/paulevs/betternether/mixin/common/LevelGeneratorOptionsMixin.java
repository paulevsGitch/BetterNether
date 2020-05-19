package paulevs.betternether.mixin.common;

//@Mixin(LevelGeneratorOptions.class)
public class LevelGeneratorOptionsMixin
{
	/*@Inject(method = "createFlat", at = @At(value = "HEAD"), cancellable = true)
	private static void flat(LevelGeneratorType generatorType, Dynamic<?> dynamic, CallbackInfoReturnable<LevelGeneratorOptions> info)
	{
		FlatChunkGeneratorConfig flatChunkGeneratorConfig = FlatChunkGeneratorConfig.fromDynamic(dynamic);
		if (flatChunkGeneratorConfig.getBiome() == Biomes.NETHER_WASTES)
		{
			LevelGeneratorOptions options = new LevelGeneratorOptions(generatorType, dynamic, (iWorld) -> {
				NetherBiomeSourceConfig biomeConfig = new NetherBiomeSourceConfig(iWorld, LevelGeneratorType.FLAT);
				return ChunkGeneratorType.FLAT.create(iWorld, new NetherBiomeSource(biomeConfig), flatChunkGeneratorConfig);
			});
			info.setReturnValue(options);
			info.cancel();
		}
	}*/
}
