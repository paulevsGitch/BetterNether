package org.betterx.betternether.mixin.common;

import org.betterx.betternether.blockentities.ChangebleCookTime;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "getTotalCookTime", at = @At("RETURN"), cancellable = true)
    private static void betternether$getTotalCookTime(
            Level level,
            AbstractFurnaceBlockEntity inventory,
            CallbackInfoReturnable<Integer> cir
    ) {
        if (inventory instanceof ChangebleCookTime) {
            ChangebleCookTime cct = (ChangebleCookTime) inventory;
            int val = cir.getReturnValue();
            cir.setReturnValue(cct.changeCookTime(val));
        }
    }
}
