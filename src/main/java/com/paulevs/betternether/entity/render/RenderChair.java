package com.paulevs.betternether.entity.render;

import com.paulevs.betternether.entity.EntityChair;
import com.paulevs.betternether.entity.model.ModelEmpty;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.util.ResourceLocation;

public class RenderChair extends MobRenderer<EntityChair, AgeableModel<EntityChair>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft:textures/block/stone.png");

	public RenderChair(EntityRendererManager renderManager) {
		super(renderManager, new ModelEmpty(), 0);
	}

	@Override
	public ResourceLocation getEntityTexture(EntityChair entity) {
		return TEXTURE;
	}
}
