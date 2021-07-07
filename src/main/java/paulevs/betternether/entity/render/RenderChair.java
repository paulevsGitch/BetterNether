package paulevs.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.model.ModelEmpty;

public class RenderChair extends MobRenderer<EntityChair, AgeableListModel<EntityChair>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft:textures/block/stone.png");

	public RenderChair(EntityRendererProvider.Context context) {
		super(context, new ModelEmpty(), 0);
	}

	@Override
	public ResourceLocation getTexture(EntityChair entity) {
		return TEXTURE;
	}
}
