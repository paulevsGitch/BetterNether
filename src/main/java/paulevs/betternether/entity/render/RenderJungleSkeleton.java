package paulevs.betternether.entity.render;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.entity.model.ModelJungleSkeleton;
import paulevs.betternether.registry.EntityRenderRegistry;

public class RenderJungleSkeleton extends MobRenderer<EntityJungleSkeleton, SkeletonModel<EntityJungleSkeleton>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/jungle_skeleton.png");

	public RenderJungleSkeleton(EntityRendererProvider.Context ctx){
		this(ctx, EntityRenderRegistry.JUNGLE_SKELETON_MODEL, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
	}
	public RenderJungleSkeleton(EntityRendererProvider.Context ctx, ModelLayerLocation layer, ModelLayerLocation legArmorLayer, ModelLayerLocation bodyArmorLayer) {
		super(ctx, new ModelJungleSkeleton(ctx.bakeLayer(layer)), 0.4F);
		final SkeletonModel<EntityJungleSkeleton> afm_sem_body = new SkeletonModel<EntityJungleSkeleton>(ctx.bakeLayer(bodyArmorLayer));
		final SkeletonModel<EntityJungleSkeleton> afm_sem_legins = new SkeletonModel<EntityJungleSkeleton>(ctx.bakeLayer(legArmorLayer));
		final HumanoidArmorLayer<EntityJungleSkeleton, SkeletonModel<EntityJungleSkeleton>, SkeletonModel<EntityJungleSkeleton>> afm;
		afm = new HumanoidArmorLayer<>(this, afm_sem_legins, afm_sem_body);
		this.addLayer(afm);

		final ItemInHandRenderer itemInHandRenderer = ctx.getItemInHandRenderer();

		this.addLayer(new ItemInHandLayer<EntityJungleSkeleton, SkeletonModel<EntityJungleSkeleton>>(this,itemInHandRenderer ));
		this.addLayer(new ElytraLayer<>(this, ctx.getModelSet()));
		this.addLayer(new CustomHeadLayer<EntityJungleSkeleton, SkeletonModel<EntityJungleSkeleton>>(this, ctx.getModelSet(), itemInHandRenderer));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityJungleSkeleton entity) {
		return TEXTURE;
	}
}