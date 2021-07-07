package paulevs.betternether.mixin.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.registry.BrewingRegistry;

@Mixin(PotionBrewing.class)
public class BrewingRecipeRegistryMixin {
	@Inject(method = "isValidIngredient", at = @At("HEAD"), cancellable = true)
	private static void isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (BrewingRegistry.isValidIngridient(stack)) {
			info.setReturnValue(true);
			info.cancel();
		}
	}
}
