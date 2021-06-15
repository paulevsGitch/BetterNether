package paulevs.betternether.mixin.client;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.TexturedModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TexturedModelData.class)
public interface TexturedModelDataMixin {
    @Accessor("data")
    public ModelData getData();
}
