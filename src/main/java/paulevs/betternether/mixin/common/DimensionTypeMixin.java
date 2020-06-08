package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.source.BiomeAccessType;
import net.minecraft.world.dimension.DimensionType;

@Mixin(DimensionType.class)
public class DimensionTypeMixin
{
	//@Shadow
	//@Final
	//private static DimensionType OVERWORLD;

	@Shadow
	@Final
	private static DimensionType THE_NETHER;

	@Inject(method = "getBiomeAccessType", at = @At("HEAD"), cancellable = true)
	private void biomeAccessType(CallbackInfoReturnable<BiomeAccessType> info)
	{
		/*DimensionType self = (DimensionType) (Object) this;
		if (self == THE_NETHER)
		{
			info.setReturnValue(NetherBiomeAccessType.INSTANCE);
			info.cancel();
		}*/
		/*else if (self == OVERWORLD)
		{
			info.setReturnValue(TYPE);
			info.cancel();
		}*/
	}
}
