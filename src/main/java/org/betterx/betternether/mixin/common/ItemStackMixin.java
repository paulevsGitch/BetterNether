package org.betterx.betternether.mixin.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import org.betterx.betternether.interfaces.InitialStackStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;updateEmptyCacheFlag()V"))
    public void bn_init(ItemLike itemLike, int i, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        if (itemLike instanceof InitialStackStateProvider forced) {
            forced.initializeState(stack);
        }
    }
}
