package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin
{
	@Shadow
	@Final
	private ScreenHandlerContext context;
	
	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> info)
	{
		info.setReturnValue(context.run((world, pos) -> { return world.getBlockState(pos).getBlock() instanceof CraftingTableBlock; }, true));
		info.cancel();
	}
}
