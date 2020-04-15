package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.gen.feature.DeltaFeature;

@Mixin(DeltaFeature.class)
public class DeltaFeatureMixin
{
	/*@Inject(method = "method_27096", at = @At("HEAD"), cancellable = true)
	private void canGenerate(IWorld iWorld, int i, BlockPos blockPos, int j, int k, CallbackInfoReturnable<Boolean> info)
	{
		if (blockPos.getY() > 42)
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}*/
}
