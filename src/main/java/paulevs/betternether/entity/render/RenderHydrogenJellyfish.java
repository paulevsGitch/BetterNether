package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.model.ModelEntityHydrogenJellyfish;

public class RenderHydrogenJellyfish extends MobEntityRenderer<EntityHydrogenJellyfish, AnimalModel<EntityHydrogenJellyfish>>
{
	private static final Identifier TEXTURE = new Identifier("textures/block/stone.png");//new Identifier(BetterNether.MOD_ID, "textures/entity/firefly.png");
	
	public RenderHydrogenJellyfish(EntityRenderDispatcher renderManager)
	{
		super(renderManager, new ModelEntityHydrogenJellyfish(), 0);
	}

	@Override
	public Identifier getTexture(EntityHydrogenJellyfish entity)
	{
		return TEXTURE;
	}
}
