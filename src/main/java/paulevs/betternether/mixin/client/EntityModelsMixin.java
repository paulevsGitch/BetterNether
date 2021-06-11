package paulevs.betternether.mixin.client;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.registry.EntityRegistry;


import java.util.Map;

@Mixin(EntityModels.class)
public abstract class EntityModelsMixin{
    @Inject(method = "getModels", at = @At("RETURN"))
    private static void betternether$getModels(CallbackInfoReturnable<Map<EntityModelLayer, TexturedModelData>> cir) {
        Map<EntityModelLayer, TexturedModelData> builder = cir.getReturnValue();
        EntityRegistry.addModels(builder);
    }
}