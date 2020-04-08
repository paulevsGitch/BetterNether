package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.model.ModelEntityHydrogenJellyfish;

public class RenderHydrogenJellyfish extends MobEntityRenderer<EntityHydrogenJellyfish, AnimalModel<EntityHydrogenJellyfish>>
{
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/jellyfish.png");

	public RenderHydrogenJellyfish(EntityRenderDispatcher renderManager)
	{
		super(renderManager, new ModelEntityHydrogenJellyfish(), 1);
	}

	@Override
	public Identifier getTexture(EntityHydrogenJellyfish entity)
	{
		return TEXTURE;
	}

	@Override
	protected int getBlockLight(EntityHydrogenJellyfish entity, float tickDelta)
	{
		return 15;
	}

	@Override
	protected void scale(EntityHydrogenJellyfish entity, MatrixStack matrixStack, float f)
	{
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
}
