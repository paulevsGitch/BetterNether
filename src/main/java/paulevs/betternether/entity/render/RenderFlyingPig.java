package paulevs.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFlyingPig;
import paulevs.betternether.entity.model.ModelEntityFlyingPig;
import paulevs.betternether.registry.EntityRenderRegistry;

public class RenderFlyingPig extends MobRenderer<EntityFlyingPig, AgeableListModel<EntityFlyingPig>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/flying_pig.png");
	private static final ResourceLocation TEXTURE_WARTED = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/flying_pig_warted.png");

	public RenderFlyingPig(EntityRendererProvider.Context ctx) {
		super(ctx, new ModelEntityFlyingPig(ctx.bakeLayer(EntityRenderRegistry.FLYING_PIG_MODEL)), 0.6F);
	}

	@Override
	public ResourceLocation getTexture(EntityFlyingPig entity) {
		return entity.isWarted() ? TEXTURE_WARTED : TEXTURE;
	}
}