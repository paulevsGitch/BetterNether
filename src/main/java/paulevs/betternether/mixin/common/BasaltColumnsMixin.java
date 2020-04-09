package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.class_5153;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

@Mixin(class_5153.class)
public class BasaltColumnsMixin
{
	@Inject(method = "method_27096", at = @At("HEAD"), cancellable = true)
	private void canGenerate(IWorld iWorld, int i, BlockPos blockPos, int j, int k, CallbackInfoReturnable<Boolean> info)
	{
		if (blockPos.getY() > 42)
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
}
