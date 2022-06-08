package org.betterx.betternether.mixin.common;

import org.betterx.betternether.enchantments.ObsidianBreaker;
import org.betterx.betternether.registry.NetherEnchantments;

import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {
    @Inject(method = "getDestroySpeed", at = @At(value = "RETURN"), cancellable = true)
    void bn_getDestroySpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        final int obsidianLevel = EnchantmentHelper.getItemEnchantmentLevel(NetherEnchantments.OBSIDIAN_BREAKER, stack);
        if (obsidianLevel > 0) {
            cir.setReturnValue(ObsidianBreaker.getDestroySpeedMultiplier(state, cir.getReturnValue(), obsidianLevel));
        }
    }
}
