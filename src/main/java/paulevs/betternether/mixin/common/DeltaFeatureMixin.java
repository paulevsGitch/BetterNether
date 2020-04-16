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
	
	/*@Inject(method = "method_27103", at = @At("HEAD"), cancellable = true)
	private static void checkBlock(IWorld iWorld, BlockPos blockPos, DeltaFeatureConfig deltaFeatureConfig, CallbackInfoReturnable<Boolean> info)
	{
		//System.out.println(iWorld.getBlockState(blockPos));
		//if (!isValidBlock(iWorld.getBlockState(blockPos)))
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
	
	private static boolean isValidBlock(BlockState state)
	{
		return BlocksHelper.isNetherGround(state) || state.getBlock() == Blocks.BASALT;
	}*/
}
