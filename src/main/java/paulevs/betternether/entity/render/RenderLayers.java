package paulevs.betternether.entity.render;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.impl.client.indigo.renderer.RenderMaterialImpl;
import net.minecraft.client.render.*;
import net.minecraft.client.render.RenderPhase.Layering;
import net.minecraft.client.render.RenderPhase.Target;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import paulevs.betternether.mixin.client.RenderLayerMixin;

abstract class RenderPhaseAccessor extends RenderPhase{
	public RenderPhaseAccessor(String name, Runnable beginAction, Runnable endAction) {
		super(name, beginAction, endAction);
	}

	private static final java.util.function.Function<Identifier, RenderLayer> FIREFLY_RENDER_LAYER = Util.memoize((texture) -> {

		RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
				.shader(RenderPhase.EYES_SHADER)
				.texture(new RenderPhase.Texture(texture, false, false))
				.writeMaskState(COLOR_MASK)
				.cull(DISABLE_CULLING)
				.transparency(RenderPhase.ADDITIVE_TRANSPARENCY)
				.overlay(ENABLE_OVERLAY_COLOR)
				.lightmap(ENABLE_LIGHTMAP)
				.build(false);
		return RenderLayerMixin.callOf("firefly", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, multiPhaseParameters);
	});
	public static RenderLayer getFirefly(Identifier texture) {
		return FIREFLY_RENDER_LAYER.apply(texture);
	}
}

public class RenderLayers {
	public static RenderPhase.Transparency translucentTransparency;
	public static RenderPhase.WriteMaskState colorMask;
	//public static RenderPhase.Fog fog;
	public static RenderPhase.DepthTest lEqualDepthTest;
	public static Layering polygonZLayering;
	public static Target translucentTarget;
}
