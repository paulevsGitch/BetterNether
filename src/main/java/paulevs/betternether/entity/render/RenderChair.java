package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.model.ModelEmpty;

public class RenderChair extends MobEntityRenderer<EntityChair, AnimalModel<EntityChair>> {
	private static final Identifier TEXTURE = new Identifier("minecraft:textures/block/stone.png");

	public RenderChair(EntityRendererFactory.Context context) {
		super(context, new ModelEmpty(), 0);
	}

	@Override
	public Identifier getTexture(EntityChair entity) {
		return TEXTURE;
	}
}
