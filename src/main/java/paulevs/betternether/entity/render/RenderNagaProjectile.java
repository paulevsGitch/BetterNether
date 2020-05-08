package paulevs.betternether.entity.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityNagaProjectile;

public class RenderNagaProjectile extends EntityRenderer<EntityNagaProjectile>
{
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/naga_projectile.png");
	private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);
	
	public RenderNagaProjectile(EntityRenderDispatcher renderManager)
	{
		super(renderManager);
	}

	@Override
	public Identifier getTexture(EntityNagaProjectile entity)
	{
		return TEXTURE;
	}

	@Override
	public void render(EntityNagaProjectile dragonFireballEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i)
	{
		int frame = (int) (System.currentTimeMillis() / 150) & 3;
		float start = frame * 0.25F;
		float end = start + 0.25F;
		matrixStack.push();
		matrixStack.scale(2.0F, 2.0F, 2.0F);
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
		MatrixStack.Entry entry = matrixStack.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, end);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, end);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, start);
		vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, start);
		matrixStack.pop();
		super.render(dragonFireballEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float f, int j, float u, float v)
	{
		vertexConsumer.vertex(matrix4f, f - 0.5F, (float)j - 0.25F, 0.0F).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).next();
	}
}