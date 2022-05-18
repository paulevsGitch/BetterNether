package org.betterx.betternether.mixin.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;

import org.betterx.betternether.registry.BrewingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public class BrewingRecipeRegistryMixin {
    @Inject(method = "isIngredient", at = @At("HEAD"), cancellable = true)
    private static void betternether$isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (BrewingRegistry.isValidIngridient(stack)) {
            info.setReturnValue(true);
            info.cancel();
        }
    }
}
