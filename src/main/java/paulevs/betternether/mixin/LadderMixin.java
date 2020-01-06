package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import paulevs.betternether.blocks.BlockBase;

@Mixin(LivingEntity.class)
public abstract class LadderMixin
{
	@Inject(method = "isClimbing", at = @At(value = "RETURN", ordinal = 2), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void canClimb(CallbackInfoReturnable<Boolean> info, BlockState state, Block block)
	{
		if (block instanceof BlockBase && (Object) this instanceof PlayerEntity)
			info.setReturnValue(((BlockBase) block).isClimmable());
    }
}
