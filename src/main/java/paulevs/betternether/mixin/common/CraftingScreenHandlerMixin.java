package paulevs.betternether.mixin.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.block.CraftingTableBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingMenu.class)
public abstract class CraftingScreenHandlerMixin {
	@Shadow
	@Final
	private ContainerLevelAccess access;

	@Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
	private void canUse(Player player, CallbackInfoReturnable<Boolean> info) {
		if (access.evaluate((world, pos) -> {
			return world.getBlockState(pos).getBlock() instanceof CraftingTableBlock;
		}, true)) {
			info.setReturnValue(true);
		}
	}
}
