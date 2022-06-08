package org.betterx.betternether.entity.render;

import org.betterx.betternether.mixin.client.RenderLayerMixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;


public abstract class RenderPhaseAccessor extends RenderStateShard { //extends ShaderDebugHelper{
    public RenderPhaseAccessor(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    protected static final RenderStateShard.TransparencyStateShard ALPHA_ADD_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            "alpha_transparency",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                );
                //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            },
            () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }
    );

    private static final RenderType getFireflySetup(ResourceLocation texture) {
        //initDebugShader();

        RenderType.CompositeState multiPhaseParameters = RenderType.CompositeState.builder()
                                                                                  //.shader(MY_DEBUG_SHADER)
                                                                                  //.setShaderState(RENDERTYPE_EYES_SHADER)
                                                                                  .setShaderState(RenderStateShard.RENDERTYPE_TRANSLUCENT_SHADER)
                                                                                  .setTextureState(new RenderStateShard.TextureStateShard(
                                                                                          texture,
                                                                                          false,
                                                                                          false
                                                                                  ))
                                                                                  .setWriteMaskState(COLOR_WRITE)
                                                                                  .setCullState(NO_CULL)
                                                                                  .setOverlayState(OVERLAY)
                                                                                  .setLightmapState(LIGHTMAP)
                                                                                  .setTransparencyState(
                                                                                          ALPHA_ADD_TRANSPARENCY)
                                                                                  .createCompositeState(false);
        return RenderLayerMixin.callCreate(
                "firefly",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                multiPhaseParameters
        );
    }

    private static final java.util.function.Function<ResourceLocation, RenderType> FIREFLY_RENDER_LAYER = Util.memoize(
            RenderPhaseAccessor::getFireflySetup);

    public static RenderType getFirefly(ResourceLocation texture) {
        //return getDebugLayer(texture, RenderPhaseAccessor::getFireflySetup);
        return FIREFLY_RENDER_LAYER.apply(texture);
    }
}
