package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.model.ModelEmpty;

public class RenderChair extends MobEntityRenderer<EntityChair, AnimalModel<EntityChair>>
{
	private static final Identifier TEXTURE = new Identifier("minecraft:textures/block/stone.png");
	
	public RenderChair(EntityRenderDispatcher renderManager)
	{
		super(renderManager, new ModelEmpty(), 0);
	}

	@Override
	public Identifier getTexture(EntityChair entity)
	{
		return TEXTURE;
	}
}
