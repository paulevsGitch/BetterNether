package paulevs.betternether.mixin.client;


import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.class)
public interface RenderLayerMixin {
    @Invoker("of")
    public static RenderType.CompositeRenderType callOf(String string, VertexFormat vertexFormat, VertexFormat.Mode drawMode, int i, boolean bl, boolean bl2, RenderType.CompositeState multiPhaseParameters) {
        throw new AssertionError();
    }
}