package org.betterx.betternether.entity.render;

import org.betterx.betternether.entity.EntityChair;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderChair extends EntityRenderer<EntityChair> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft:textures/block/stone.png");

    public RenderChair(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityChair entity) {
        return TEXTURE;
    }
}
