package org.betterx.betternether.entity.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.betterx.betternether.entity.EntityChair;

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
