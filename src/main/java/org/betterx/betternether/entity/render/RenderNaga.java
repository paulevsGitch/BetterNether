package org.betterx.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.entity.EntityNaga;
import org.betterx.betternether.entity.model.ModelNaga;
import org.betterx.betternether.registry.EntityRenderRegistry;

public class RenderNaga extends MobRenderer<EntityNaga, AgeableListModel<EntityNaga>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID,
                                                                         "textures/entity/naga.png");

    public RenderNaga(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelNaga(ctx.bakeLayer(EntityRenderRegistry.NAGA_MODEL)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityNaga entity) {
        return TEXTURE;
    }
}
