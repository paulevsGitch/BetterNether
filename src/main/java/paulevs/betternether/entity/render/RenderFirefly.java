package paulevs.betternether.entity.render;

import java.nio.FloatBuffer;
import java.util.Iterator;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.model.ModelEmpty;
import paulevs.betternether.entity.model.ModelEntityFirefly;
import paulevs.betternether.registry.EntityRenderRegistry;


class FireflyGlowFeatureRenderer extends FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	public FireflyGlowFeatureRenderer(FeatureRendererContext<EntityFirefly, AnimalModel<EntityFirefly>> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, EntityFirefly livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EntityModel<EntityFirefly> model = this.getContextModel();
		Identifier identifier = this.getTexture(livingEntity);
		RenderLayer renderLayer =  RenderPhaseAccessor.getFirefly(identifier);
		VertexConsumer vertexConsumer = vertices.getBuffer(renderLayer);

		if (model instanceof ModelEntityFirefly){
			float red = livingEntity.getRed();
			float green = livingEntity.getGreen();
			float blue = livingEntity.getBlue();

			((ModelEntityFirefly)model).getGlowPart().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		}
	}
}

class FireflyCamAlignedGlowFeatureRenderer extends FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	private static final int LIT = 15728880;
	private static final ModelEmpty emptyModel = new ModelEmpty();

	private final RenderFirefly fireflyRenderer;

	public FireflyCamAlignedGlowFeatureRenderer(RenderFirefly fireflyRenderer) {
		super(fireflyRenderer);
		this.fireflyRenderer = fireflyRenderer;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, EntityFirefly entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		Identifier identifier = this.getTexture(entity);
		RenderLayer renderLayer = RenderPhaseAccessor.getFirefly(identifier);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);

		float red = entity.getRed();
		float green = entity.getGreen();
		float blue = entity.getBlue();

		matrixStack.push();

		/* //Original transform
		matrixStack.translate(0, 0.125, 0);
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));*/

		matrixStack.translate(0, 1.25, 0);

		//Get inverse rotation to make view-aligned
		Matrix3f normalMatrix = matrixStack.peek().getNormal();
		normalMatrix.transpose();
		Triple<Quaternion, Vec3f, Quaternion> trip = normalMatrix.decomposeLinearTransformation();
		matrixStack.multiply(trip.getLeft());

		MatrixStack.Entry entry = matrixStack.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();

		addVertex(matrix4f, matrix3f, vertexConsumer, -1, -1, 0F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, -1, 1F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, 1, 1F, 1F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, -1, 1, 0F, 1F, red, green, blue);

		emptyModel.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		matrixStack.pop();
	}


	public void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float posX, float posY, float u, float v, float red, float green, float blue) {
		vertexConsumer
				.vertex(matrix4f, posX, posY, 0)
				.color(red, green, blue, 1F)
				.texture(u, v)
				.overlay(OverlayTexture.DEFAULT_UV)
				.light(LIT)
				.normal(matrix3f, 0, 1, 0).next();
	}
}

public class RenderFirefly extends MobEntityRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/firefly.png");

	public RenderFirefly(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelEntityFirefly(ctx.getPart(EntityRenderRegistry.FIREFLY_LAYER)), 0);

		this.addFeature(new FireflyGlowFeatureRenderer( this));
		this.addFeature(new FireflyCamAlignedGlowFeatureRenderer( this));
	}

	@Override
	public Identifier getTexture(EntityFirefly entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLight(EntityFirefly entity, BlockPos blockPos) {
		return 15;
	}
}
