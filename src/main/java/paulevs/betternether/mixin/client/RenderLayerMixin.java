package paulevs.betternether.mixin.client;


import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderLayer.class)
public interface RenderLayerMixin {
    @Invoker("of")
    public static RenderLayer.MultiPhase callOf(String string, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int i, boolean bl, boolean bl2, RenderLayer.MultiPhaseParameters multiPhaseParameters) {
        throw new AssertionError();
    }
}