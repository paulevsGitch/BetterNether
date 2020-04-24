package paulevs.betternether.entity.render;

import java.util.Iterator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.model.ModelEntityFirefly;

public class RenderFirefly extends MobEntityRenderer<EntityFirefly, AnimalModel<EntityFirefly>>
{
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/firefly.png");
	private static final RenderLayer LAYER = RenderLayer.getEntityNoOutline(TEXTURE); // getEntityNoOutline getBeaconBeam
	private static final int LIT = 15728880;

	public RenderFirefly(EntityRenderDispatcher renderManager)
	{
		super(renderManager, new ModelEntityFirefly(), 0);
	}

	@Override
	public Identifier getTexture(EntityFirefly entity)
	{
		return TEXTURE;
	}

	@Override
	public void render(EntityFirefly mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i)
	{
		float red = mobEntity.getRed();
		float green = mobEntity.getGreen();
		float blue = mobEntity.getBlue();

		matrixStack.push();
		matrixStack.translate(0, 0.125, 0);
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
		MatrixStack.Entry entry = matrixStack.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
		addVertex(matrix4f, matrix3f, vertexConsumer, -1, -1, 0F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, -1, 1F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, 1, 1F, 1F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, -1, 1, 0F, 1F, red, green, blue);
		matrixStack.pop();

		matrixStack.push();
		this.model.handSwingProgress = this.getHandSwingProgress(mobEntity, g);
		this.model.riding = mobEntity.hasVehicle();
		this.model.child = mobEntity.isBaby();
		float h = MathHelper.lerpAngleDegrees(g, mobEntity.prevBodyYaw, mobEntity.bodyYaw);
		float j = MathHelper.lerpAngleDegrees(g, mobEntity.prevHeadYaw, mobEntity.headYaw);
		float k = j - h;
		float o;
		if (mobEntity.hasVehicle() && mobEntity.getVehicle() instanceof LivingEntity)
		{
			LivingEntity mobEntity2 = (LivingEntity) mobEntity.getVehicle();
			h = MathHelper.lerpAngleDegrees(g, mobEntity2.prevBodyYaw, mobEntity2.bodyYaw);
			k = j - h;
			o = MathHelper.wrapDegrees(k);
			if (o < -85.0F)
			{
				o = -85.0F;
			}

			if (o >= 85.0F)
			{
				o = 85.0F;
			}

			h = j - o;
			if (o * o > 2500.0F)
			{
				h += o * 0.2F;
			}

			k = j - h;
		}

		float m = MathHelper.lerp(g, mobEntity.prevPitch, mobEntity.pitch);
		float p;
		if (mobEntity.getPose() == EntityPose.SLEEPING)
		{
			Direction direction = mobEntity.getSleepingDirection();
			if (direction != null)
			{
				p = mobEntity.getEyeHeight(EntityPose.STANDING) - 0.1F;
				matrixStack.translate((double) ((float) (-direction.getOffsetX()) * p), 0.0D,
						(double) ((float) (-direction.getOffsetZ()) * p));
			}
		}

		o = this.getAnimationProgress(mobEntity, g);
		this.setupTransforms(mobEntity, matrixStack, o, h, g);
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(mobEntity, matrixStack, g);
		matrixStack.translate(0.0D, -1.5010000467300415D, 0.0D);
		p = 0.0F;
		float q = 0.0F;
		if (!mobEntity.hasVehicle() && mobEntity.isAlive())
		{
			p = MathHelper.lerp(g, mobEntity.lastLimbDistance, mobEntity.limbDistance);
			q = mobEntity.limbAngle - mobEntity.limbDistance * (1.0F - g);
			if (mobEntity.isBaby())
			{
				q *= 3.0F;
			}

			if (p > 1.0F)
			{
				p = 1.0F;
			}
		}

		this.model.animateModel(mobEntity, q, p, g);
		this.model.setAngles(mobEntity, q, p, o, k, m);
		boolean bl = this.isVisible(mobEntity);
		boolean bl2 = !bl && !mobEntity.isInvisibleTo(MinecraftClient.getInstance().player);
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		boolean bl3 = minecraftClient.method_27022(mobEntity);
		RenderLayer renderLayer = this.getRenderLayer(mobEntity, bl, bl2, bl3);
		if (renderLayer != null)
		{
			int r = getOverlay(mobEntity, 0);
			this.model.render(matrixStack, vertexConsumer, i, r, 1.0F, 1.0F, 1.0F, bl2 ? 0.15F : 1.0F);
		}

		if (!mobEntity.isSpectator())
		{
			Iterator<?> var21 = this.features.iterator();
			while (var21.hasNext())
			{
				@SuppressWarnings("unchecked")
				FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>> featureRenderer = (FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>>) var21.next();
				featureRenderer.render(matrixStack, vertexConsumerProvider, i, mobEntity, q, p, g, o, k, m);
			}
		}

		matrixStack.pop();

		if (this.hasLabel(mobEntity))
		{
			this.renderLabelIfPresent(mobEntity, mobEntity.getDisplayName(), matrixStack, vertexConsumerProvider, i);
		}
	}

	public void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float posX, float posY, float u, float v, float red, float green, float blue)
	{
		vertexConsumer.vertex(matrix4f, posX, posY, 0).color(red, green, blue, 1F).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LIT).normal(matrix3f, 0, 1, 0).next();
	}

	@Override
	protected int getBlockLight(EntityFirefly entity, float tickDelta)
	{
		return 15;
	}
}
