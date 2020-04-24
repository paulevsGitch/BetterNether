package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.source.BiomeAccessType;
import net.minecraft.world.dimension.DimensionType;
import paulevs.betternether.world.NetherBiomeAccessType;

@Mixin(DimensionType.class)
public class DimensionTypeMixin
{
	private static final BiomeAccessType TYPE = NetherBiomeAccessType.INSTANCE;
	
	@Inject(method = "getBiomeAccessType", at = @At("HEAD"), cancellable = true)
	private void biomeAccessType(CallbackInfoReturnable<BiomeAccessType> info)
	{
		DimensionType self = (DimensionType) (Object) this;
		if (self == DimensionType.THE_NETHER)
		{
			info.setReturnValue(TYPE);
			info.cancel();
		}
		else if (self == DimensionType.OVERWORLD)
		{
			info.setReturnValue(TYPE);
			info.cancel();
		}
	}
}
