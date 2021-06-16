package paulevs.betternether.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import paulevs.betternether.mixin.client.RenderLayerMixin;




public abstract class RenderPhaseAccessor extends RenderPhase { //extends ShaderDebugHelper{
	public RenderPhaseAccessor(String name, Runnable beginAction, Runnable endAction) {
		super(name, beginAction, endAction);
	}

	protected static final RenderPhase.Transparency ALPHA_ADD_TRANSPARENCY = new RenderPhase.Transparency("alpha_transparency", () -> {
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
	}, () -> {
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	});

	private static final RenderLayer getFireflySetup(Identifier texture){
		//initDebugShader();

		RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
				//.shader(MY_DEBUG_SHADER)
				.shader(EYES_SHADER)
				.texture(new RenderPhase.Texture(texture, false, false))
				.writeMaskState(COLOR_MASK)
				.cull(DISABLE_CULLING)
				.overlay(ENABLE_OVERLAY_COLOR)
				.lightmap(ENABLE_LIGHTMAP)
				.transparency(ALPHA_ADD_TRANSPARENCY)
				.build(false);
		return RenderLayerMixin.callOf("firefly", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, multiPhaseParameters);
	}

	private static final java.util.function.Function<Identifier, RenderLayer> FIREFLY_RENDER_LAYER = Util.memoize(RenderPhaseAccessor::getFireflySetup);

	public static RenderLayer getFirefly(Identifier texture) {
		//return getDebugLayer(texture, RenderPhaseAccessor::getFireflySetup);
		return FIREFLY_RENDER_LAYER.apply(texture);
	}
}
