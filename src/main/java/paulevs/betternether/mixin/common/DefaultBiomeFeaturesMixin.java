package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import paulevs.betternether.config.Config;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin
{
	@Inject(method = "addNetherMineables", at = @At("RETURN"))
	private static void addNetherMineables(GenerationSettings.Builder builder, CallbackInfo info)
	{
		if (Config.getBoolean("generator.world.cities", "generate", true))
		{
			//builder.structureFeature(BNWorldGenerator.CITY.configure(FeatureConfig.DEFAULT));
			//builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BNWorldGenerator.CITY.configure(FeatureConfig.DEFAULT));
		}
	}
}
