package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityHydrogenJellyfish;
import paulevs.betternether.entity.model.ModelEntityHydrogenJellyfish;
import paulevs.betternether.registry.EntityRegistry;

public class RenderHydrogenJellyfish extends MobEntityRenderer<EntityHydrogenJellyfish, AnimalModel<EntityHydrogenJellyfish>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/jellyfish.png");

	public RenderHydrogenJellyfish(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelEntityHydrogenJellyfish(ctx.getPart(EntityRegistry.HYDROGEN_JELLYFISH_LAYER)), 1);
	}

	@Override
	public Identifier getTexture(EntityHydrogenJellyfish entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLight(EntityHydrogenJellyfish entity, BlockPos pos) {
		return 15;
	}

	@Override
	protected void scale(EntityHydrogenJellyfish entity, MatrixStack matrixStack, float f) {
		float scale = entity.getScale();
		matrixStack.scale(scale, scale, scale);
	}
}
