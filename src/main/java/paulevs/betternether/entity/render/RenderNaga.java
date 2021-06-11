package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityNaga;
import paulevs.betternether.entity.model.ModelNaga;
import paulevs.betternether.registry.EntityRegistry;

public class RenderNaga extends MobEntityRenderer<EntityNaga, AnimalModel<EntityNaga>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/naga.png");

	public RenderNaga(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelNaga(ctx.getPart(EntityRegistry.NAGA_LAYER)), 0.7F);
	}

	@Override
	public Identifier getTexture(EntityNaga entity) {
		return TEXTURE;
	}
}
