package org.betterx.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntitySkull;
import org.betterx.betternether.entity.model.ModelSkull;
import org.betterx.betternether.registry.EntityRenderRegistry;

public class RenderSkull extends MobRenderer<EntitySkull, AgeableListModel<EntitySkull>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID,
                                                                         "textures/entity/skull.png");

    public RenderSkull(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelSkull(ctx.bakeLayer(EntityRenderRegistry.SKULL_MODEL)), 0.7F);
        this.addLayer(new GlowFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySkull entity) {
        return TEXTURE;
    }

    static class GlowFeatureRenderer extends EyesLayer<EntitySkull, AgeableListModel<EntitySkull>> {
        private static final RenderType SKIN = RenderType.entityTranslucent(new ResourceLocation(BetterNether.MOD_ID,
                                                                                                 "textures/entity/skull_glow.png"));

        public GlowFeatureRenderer(RenderLayerParent<EntitySkull, AgeableListModel<EntitySkull>> featureRendererContext) {
            super(featureRendererContext);
        }

        public RenderType renderType() {
            return SKIN;
        }
    }

    @Override
    protected int getBlockLightLevel(EntitySkull entity, BlockPos pos) {
        return Mth.clamp(super.getBlockLightLevel(entity, pos), 7, 15);
    }
}