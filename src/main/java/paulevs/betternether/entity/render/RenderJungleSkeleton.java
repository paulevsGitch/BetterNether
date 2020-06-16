package paulevs.betternether.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.Identifier;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityJungleSkeleton;
import paulevs.betternether.entity.model.ModelJungleSkeleton;

public class RenderJungleSkeleton extends MobEntityRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>
{
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/jungle_skeleton.png");
	
	public RenderJungleSkeleton(EntityRenderDispatcher renderManager)
	{
		super(renderManager, new ModelJungleSkeleton(), 0.4F);
		this.addFeature(new ArmorFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>, SkeletonEntityModel<EntityJungleSkeleton>>(this, new SkeletonEntityModel<EntityJungleSkeleton>(0.5F, true), new SkeletonEntityModel<EntityJungleSkeleton>(1.0F, true)));
		this.addFeature(new HeldItemFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>(this));
		this.addFeature(new ElytraFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>(this));
		this.addFeature(new HeadFeatureRenderer<EntityJungleSkeleton, SkeletonEntityModel<EntityJungleSkeleton>>(this));
	}

	@Override
	public Identifier getTexture(EntityJungleSkeleton entity)
	{
		return TEXTURE;
	}
}