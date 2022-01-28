package paulevs.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.model.ModelEmpty;

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
