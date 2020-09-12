package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import paulevs.betternether.config.Config;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin
{
	@Inject(method = "addNetherMineables", at = @At("TAIL"))
	private static void addNetherMineables(GenerationSettings.Builder builder, CallbackInfo info)
	{
		if (Config.getBoolean("generator.world.cities", "generate", true))
		{
			builder.structureFeature(BNWorldGenerator.CITY_CONFIGURED);
		}
	}
}
