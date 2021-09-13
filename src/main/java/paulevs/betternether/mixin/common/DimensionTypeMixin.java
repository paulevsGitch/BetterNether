package paulevs.betternether.mixin.common;

import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = DimensionType.class, priority = 100)
public class DimensionTypeMixin {
	/*@Inject(method = "defaultNetherGenerator", at = @At("HEAD"), cancellable = true)
	private static void replaceGenerator(Registry<Biome> biomeRegistry, Registry<NoiseGeneratorSettings> chunkGeneratorSettingsRegistry, long seed, CallbackInfoReturnable<ChunkGenerator> info) {
		info.setReturnValue(new NoiseBasedChunkGenerator(new NetherBiomeSource(biomeRegistry, seed), seed, () -> {
			return (NoiseGeneratorSettings) chunkGeneratorSettingsRegistry.getOrThrow(NoiseGeneratorSettings.NETHER);
		}));
		info.cancel();
	}*/
}