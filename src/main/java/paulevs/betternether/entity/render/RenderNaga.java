package paulevs.betternether.entity.render;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.entity.model.ModelNaga;
import paulevs.betternether.registry.EntityRenderRegistry;

public class RenderNaga extends MobRenderer<EntityNaga, AgeableListModel<EntityNaga>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/naga.png");

	public RenderNaga(EntityRendererProvider.Context ctx) {
		super(ctx, new ModelNaga(ctx.bakeLayer(EntityRenderRegistry.NAGA_MODEL)), 0.7F);
	}

	@Override
	public ResourceLocation getTexture(EntityNaga entity) {
		return TEXTURE;
	}
}
