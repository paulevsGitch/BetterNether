package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.entity.model.ModelJungleSkeleton;
import paulevs.betternether.registry.EntityRegistry;

public class RenderJungleSkeleton extends MobEntityRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/jungle_skeleton.png");

	public RenderJungleSkeleton(EntityRendererFactory.Context ctx){
		this(ctx, EntityRegistry.JUNGLE_SKELETON_LAYER, EntityModelLayers.SKELETON_INNER_ARMOR, EntityModelLayers.SKELETON_OUTER_ARMOR);
	}
	public RenderJungleSkeleton(EntityRendererFactory.Context ctx, EntityModelLayer layer, EntityModelLayer legArmorLayer, EntityModelLayer bodyArmorLayer) {
		super(ctx, new ModelJungleSkeleton(ctx.getPart(layer)), 0.4F);
		final SkeletonEntityModel<EntityJungleSkeleton> afm_sem_body = new SkeletonEntityModel<EntityJungleSkeleton>(ctx.getPart(bodyArmorLayer));
		final SkeletonEntityModel<EntityJungleSkeleton> afm_sem_legins = new SkeletonEntityModel<EntityJungleSkeleton>(ctx.getPart(legArmorLayer));
		final ArmorFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>, SkeletonEntityModel<EntityJungleSkeleton>> afm;
		afm = new ArmorFeatureRenderer<>(this, afm_sem_legins, afm_sem_body);
		this.addFeature(afm);

		this.addFeature(new HeldItemFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>(this));
		this.addFeature(new ElytraFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>(this, ctx.getModelLoader()));
		this.addFeature(new HeadFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>(this, ctx.getModelLoader()));
	}

	@Override
	public Identifier getTexture(EntityJungleSkeleton entity) {
		return TEXTURE;
	}
}