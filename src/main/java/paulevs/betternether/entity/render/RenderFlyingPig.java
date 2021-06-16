package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFlyingPig;
import paulevs.betternether.entity.model.ModelEntityFlyingPig;
import paulevs.betternether.registry.EntityRenderRegistry;

public class RenderFlyingPig extends MobEntityRenderer<EntityFlyingPig, AnimalModel<EntityFlyingPig>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/flying_pig.png");
	private static final Identifier TEXTURE_WARTED = new Identifier(BetterNether.MOD_ID, "textures/entity/flying_pig_warted.png");

	public RenderFlyingPig(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelEntityFlyingPig(ctx.getPart(EntityRenderRegistry.FLYING_PIG_MODEL)), 0.6F);
	}

	@Override
	public Identifier getTexture(EntityFlyingPig entity) {
		return entity.isWarted() ? TEXTURE_WARTED : TEXTURE;
	}
}