package org.betterx.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityFlyingPig;
import org.betterx.betternether.entity.model.ModelEntityFlyingPig;
import org.betterx.betternether.registry.EntityRenderRegistry;

public class RenderFlyingPig extends MobRenderer<EntityFlyingPig, AgeableListModel<EntityFlyingPig>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID,
                                                                         "textures/entity/flying_pig.png");
    private static final ResourceLocation TEXTURE_WARTED = new ResourceLocation(BetterNether.MOD_ID,
                                                                                "textures/entity/flying_pig_warted.png");

    public RenderFlyingPig(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelEntityFlyingPig(ctx.bakeLayer(EntityRenderRegistry.FLYING_PIG_MODEL)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityFlyingPig entity) {
        return entity.isWarted() ? TEXTURE_WARTED : TEXTURE;
    }
}