package org.betterx.betternether.mixin.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;

import org.betterx.betternether.registry.NetherEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultAttributes.class)
public class DefaultAttributeRegistryMixin {
    @Inject(method = "getSupplier", at = @At("HEAD"), cancellable = true)
    private static void getAttribute(EntityType<? extends LivingEntity> type,
                                     CallbackInfoReturnable<AttributeSupplier> info) {
        AttributeSupplier container = NetherEntities.ATTRIBUTES.get(type);
        if (container != null) {
            info.setReturnValue(container);
            info.cancel();
        }
    }

    @Inject(method = "hasSupplier", at = @At("HEAD"), cancellable = true)
    private static void hasDefinition(EntityType<?> type, CallbackInfoReturnable<Boolean> info) {
        if (NetherEntities.ATTRIBUTES.containsKey(type)) {
            info.setReturnValue(true);
            info.cancel();
        }
    }
}