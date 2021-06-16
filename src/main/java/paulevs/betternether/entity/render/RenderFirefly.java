package paulevs.betternether.entity.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.apache.commons.lang3.tuple.Triple;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.model.ModelEntityFirefly;
import paulevs.betternether.registry.EntityRenderRegistry;


class FireflyGlowFeatureRenderer extends FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	private static final int LIT = 15728880;
	//static final ModelEmpty emptyModel = new ModelEmpty();

	public FireflyGlowFeatureRenderer(FeatureRendererContext<EntityFirefly, AnimalModel<EntityFirefly>> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, EntityFirefly livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EntityModel<EntityFirefly> model = this.getContextModel();


		if (model instanceof ModelEntityFirefly){
			Identifier identifier = this.getTexture(livingEntity);
			RenderLayer renderLayer =  RenderPhaseAccessor.getFirefly(identifier);
			VertexConsumer vertexConsumer = vertices.getBuffer(renderLayer);

			float red = livingEntity.getRed();
			float green = livingEntity.getGreen();
			float blue = livingEntity.getBlue();

			addViewAlignedGlow(matrices, vertexConsumer, red, green, blue);

			((ModelEntityFirefly)model).getGlowPart().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		}
	}

	private void addViewAlignedGlow(MatrixStack matrices, VertexConsumer vertexConsumer, float red, float green, float blue) {
		matrices.push();

			/* //Original transform
			matrixStack.translate(0, 0.125, 0);
			matrixStack.multiply(this.dispatcher.getRotation());
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));*/

		matrices.translate(0, 1.25, 0);

		//Get inverse rotation to make view-aligned
		Matrix3f normalMatrix = matrices.peek().getNormal();
		normalMatrix.transpose();
		Triple<Quaternion, Vec3f, Quaternion> trip = normalMatrix.decomposeLinearTransformation();
		matrices.multiply(trip.getLeft());

		MatrixStack.Entry entry = matrices.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();

		addVertex(matrix4f, matrix3f, vertexConsumer, -1, -1, 0F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, -1, 1F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, 1, 1F, 1F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, -1, 1, 0F, 1F, red, green, blue);

		//emptyModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		matrices.pop();
	}

	public static void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float posX, float posY, float u, float v, float red, float green, float blue) {
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
		super(ctx, new ModelEntityFirefly(ctx.getPart(EntityRenderRegistry.FIREFLY_MODEL)), 0);

		this.addFeature(new FireflyGlowFeatureRenderer( this));
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
