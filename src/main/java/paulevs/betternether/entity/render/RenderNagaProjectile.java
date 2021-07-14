package paulevs.betternether.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityNagaProjectile;

public class RenderNagaProjectile extends EntityRenderer<EntityNagaProjectile> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterNether.MOD_ID, "textures/entity/naga_projectile.png");
	private static final RenderType LAYER = RenderType.entityCutoutNoCull(TEXTURE);

	public RenderNagaProjectile(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityNagaProjectile entity) {
		return TEXTURE;
	}

	@Override
	public void render(EntityNagaProjectile dragonFireballEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		int frame = (int) (System.currentTimeMillis() / 150) & 3;
		float start = frame * 0.25F;
		float end = start + 0.25F;
		matrixStack.pushPose();
		matrixStack.scale(2.0F, 2.0F, 2.0F);
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		PoseStack.Pose entry = matrixStack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, end);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, end);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, start);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, start);
		matrixStack.popPose();
		super.render(dragonFireballEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float f, int j, float u, float v) {
		vertexConsumer.vertex(matrix4f, f - 0.5F, (float) j - 0.25F, 0.0F).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}
}