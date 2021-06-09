package paulevs.betternether.entity.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntitySkull;
import paulevs.betternether.entity.model.ModelSkull;
import paulevs.betternether.entity.render.RenderSkull.GlowFeatureRenderer;

public class RenderSkull extends MobEntityRenderer<EntitySkull, AnimalModel<EntitySkull>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/skull.png");

	public RenderSkull(EntityRenderDispatcher renderManager) {
		super(renderManager, new ModelSkull(), 0.7F);
		this.addFeature(new GlowFeatureRenderer(this));
	}

	@Override
	public Identifier getTexture(EntitySkull entity) {
		return TEXTURE;
	}

	static class GlowFeatureRenderer extends EyesFeatureRenderer<EntitySkull, AnimalModel<EntitySkull>> {
		private static final RenderLayer SKIN = RenderLayer.getEntityTranslucent(new Identifier(BetterNether.MOD_ID, "textures/entity/skull_glow.png"));

		public GlowFeatureRenderer(FeatureRendererContext<EntitySkull, AnimalModel<EntitySkull>> featureRendererContext) {
			super(featureRendererContext);
		}

		public RenderLayer getEyesTexture() {
			return SKIN;
		}
	}

	@Override
	protected int getBlockLight(EntitySkull entity, BlockPos pos) {
		return MathHelper.clamp(super.getBlockLight(entity, pos), 7, 15);
	}
}