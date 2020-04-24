package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.Dynamic;

import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorOptions;
import net.minecraft.world.level.LevelGeneratorType;
import paulevs.betternether.world.NetherBiomeSource;
import paulevs.betternether.world.NetherBiomeSourceConfig;

@Mixin(LevelGeneratorOptions.class)
public class LevelGeneratorOptionsMixin
{
	@Inject(method = "createFlat", at = @At(value = "HEAD"), cancellable = true)
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
	}
}
