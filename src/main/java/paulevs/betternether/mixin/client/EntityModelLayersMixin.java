package paulevs.betternether.mixin.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(EntityModelLayers.class)
public interface EntityModelLayersMixin{
    @Invoker("registerMain")
    public static EntityModelLayer callRegisterMain(String string){
        throw new AssertionError();
    }
}
