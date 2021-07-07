package paulevs.betternether.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.model.ModelEntityFirefly;
import paulevs.betternether.registry.EntityRenderRegistry;


class FireflyGlowFeatureRenderer extends RenderLayer<EntityFirefly, AgeableListModel<EntityFirefly>> {
	private static final int LIT = 15728880;
	//static final ModelEmpty emptyModel = new ModelEmpty();

	public FireflyGlowFeatureRenderer(RenderLayerParent<EntityFirefly, AgeableListModel<EntityFirefly>> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public void render(PoseStack matrices, MultiBufferSource vertices, int light, EntityFirefly livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		EntityModel<EntityFirefly> model = this.getParentModel();


		if (model instanceof ModelEntityFirefly){
			ResourceLocation identifier = this.getTextureLocation(livingEntity);
			RenderType renderLayer =  RenderPhaseAccessor.getFirefly(identifier);
			VertexConsumer vertexConsumer = vertices.getBuffer(renderLayer);

			float red = livingEntity.getRed();
			float green = livingEntity.getGreen();
			float blue = livingEntity.getBlue();

			addViewAlignedGlow(matrices, vertexConsumer, red, green, blue);

			((ModelEntityFirefly)model).getGlowPart().render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue,  1f);
		}
	}

	private void addViewAlignedGlow(PoseStack matrices, VertexConsumer vertexConsumer, float red, float green, float blue) {
		matrices.pushPose();

			/* //Original transform
			matrixStack.translate(0, 0.125, 0);
			matrixStack.multiply(this.dispatcher.getRotation());
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));*/

		matrices.translate(0, 1.25, 0);

		//Get inverse rotation to make view-aligned
		Matrix3f normalMatrix = matrices.last().normal();
		normalMatrix.transpose();
		Triple<Quaternion, Vector3f, Quaternion> trip = normalMatrix.svdDecompose();
		matrices.mulPose(trip.getLeft());

		PoseStack.Pose entry = matrices.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();

		addVertex(matrix4f, matrix3f, vertexConsumer, -1, -1, 0F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, -1, 1F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, 1, 1F, 1F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, -1, 1, 0F, 1F, red, green, blue);

		//emptyModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		matrices.popPose();
	}

	public static void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float posX, float posY, float u, float v, float red, float green, float blue) {
		vertexConsumer
				.vertex(matrix4f, posX, posY, 0)
				.color(red, green, blue, 1F)
				.uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY)
				.uv2(LIT)
				.normal(matrix3f, 0, 1, 0).endVertex();
	}
}

public class RenderFirefly extends MobRenderer<EntityFirefly, AgeableListModel<EntityFirefly>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/firefly.png");

	public RenderFirefly(EntityRendererProvider.Context ctx) {
		super(ctx, new ModelEntityFirefly(ctx.bakeLayer(EntityRenderRegistry.FIREFLY_MODEL)), 0);

		this.addLayer(new FireflyGlowFeatureRenderer( this));
	}

	@Override
	public ResourceLocation getTextureLocation(EntityFirefly entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(EntityFirefly entity, BlockPos blockPos) {
		return 15;
	}
}
