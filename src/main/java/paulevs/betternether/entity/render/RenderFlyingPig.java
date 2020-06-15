package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFlyingPig;
import paulevs.betternether.entity.model.ModelEntityFlyingPig;

public class RenderFlyingPig extends MobEntityRenderer<EntityFlyingPig, AnimalModel<EntityFlyingPig>>
{
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/flying_pig.png");
	private static final Identifier TEXTURE_WARTED = new Identifier(BetterNether.MOD_ID, "textures/entity/flying_pig_warted.png");

	public RenderFlyingPig(EntityRenderDispatcher renderManager)
	{
		super(renderManager, new ModelEntityFlyingPig(), 1);
	}

	@Override
	public Identifier getTexture(EntityFlyingPig entity)
	{
		return entity.isWarted() ? TEXTURE_WARTED : TEXTURE;
	}
}