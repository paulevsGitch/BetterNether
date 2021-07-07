package paulevs.betternether.mixin.client;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LayerDefinition.class)
public interface TexturedModelDataMixin {
    @Accessor("data")
    public MeshDefinition getData();
}
