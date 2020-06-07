package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.dimension.DimensionType;

@Mixin(DimensionType.class)
public abstract class TheNetherDimensionMixin
{
	/*@Inject(method = "createChunkGenerator", at = @At("HEAD"), cancellable = true)
	private void makeChunkGenerator(CallbackInfoReturnable<ChunkGenerator<? extends ChunkGeneratorConfig>> info)
	{
		CavesChunkGeneratorConfig cavesChunkGeneratorConfig = (CavesChunkGeneratorConfig) ChunkGeneratorType.CAVES.createConfig();
		cavesChunkGeneratorConfig.setDefaultBlock(Blocks.NETHERRACK.getDefaultState());
		cavesChunkGeneratorConfig.setDefaultFluid(Blocks.LAVA.getDefaultState());
		NetherBiomeSourceConfig biomeConfig = new NetherBiomeSourceConfig(world);
		info.setReturnValue(ChunkGeneratorType.CAVES.create(world, new NetherBiomeSource(biomeConfig), cavesChunkGeneratorConfig));
		info.cancel();
	}*/
	
/*private static ChunkGenerator method_28535(long l) {
	      return new SurfaceChunkGenerator(MultiNoiseBiomeSource.class_5305.field_24723.method_28469(l), l, ChunkGeneratorType.Preset.NETHER.getChunkGeneratorType());
	   }*/
	
	/*@Inject(method = "method_28535", at = @At("HEAD"), cancellable = true)
	private static void makeGenerator(long seed, CallbackInfoReturnable<ChunkGenerator> info)
	{
		info.setReturnValue(returnValue);
		info.cancel();
	}*/
}