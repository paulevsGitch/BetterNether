package paulevs.betternether.entity.render;

import java.util.Iterator;

import com.mojang.blaze3d.systems.RenderSystem;

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
import net.minecraft.util.math.BlockPos;
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
	private static final RenderLayer LAYER = RenderLayers.getFirefly(TEXTURE); // getEntityTranslucent getEntityNoOutline getBeaconBeam getEntityShadow
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
	public void render(EntityFirefly entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i)
	{
		RenderSystem.enableDepthTest();
		RenderSystem.enableBlend();
		
		float red = entity.getRed();
		float green = entity.getGreen();
		float blue = entity.getBlue();

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
		this.model.handSwingProgress = this.getHandSwingProgress(entity, g);
		this.model.riding = entity.hasVehicle();
		this.model.child = entity.isBaby();
		float h = MathHelper.lerpAngleDegrees(g, entity.prevBodyYaw, entity.bodyYaw);
		float j = MathHelper.lerpAngleDegrees(g, entity.prevHeadYaw, entity.headYaw);
		float k = j - h;
		float o;
		if (entity.hasVehicle() && entity.getVehicle() instanceof LivingEntity)
		{
			LivingEntity mobEntity2 = (LivingEntity) entity.getVehicle();
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

		float m = MathHelper.lerp(g, entity.prevPitch, entity.pitch);
		float p;
		if (entity.getPose() == EntityPose.SLEEPING)
		{
			Direction direction = entity.getSleepingDirection();
			if (direction != null)
			{
				p = entity.getEyeHeight(EntityPose.STANDING) - 0.1F;
				matrixStack.translate((double) ((float) (-direction.getOffsetX()) * p), 0.0D,
						(double) ((float) (-direction.getOffsetZ()) * p));
			}
		}

		o = this.getAnimationProgress(entity, g);
		this.setupTransforms(entity, matrixStack, o, h, g);
		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		this.scale(entity, matrixStack, g);
		matrixStack.translate(0.0D, -1.5010000467300415D, 0.0D);
		p = 0.0F;
		float q = 0.0F;
		if (!entity.hasVehicle() && entity.isAlive())
		{
			p = MathHelper.lerp(g, entity.lastLimbDistance, entity.limbDistance);
			q = entity.limbAngle - entity.limbDistance * (1.0F - g);
			if (entity.isBaby())
			{
				q *= 3.0F;
			}

			if (p > 1.0F)
			{
				p = 1.0F;
			}
		}

		this.model.animateModel(entity, q, p, g);
		this.model.setAngles(entity, q, p, o, k, m);
		boolean visible = this.isVisible(entity);
		boolean ghost = !visible && !entity.isInvisibleTo(MinecraftClient.getInstance().player);
		MinecraftClient client = MinecraftClient.getInstance();
		boolean bl3 = client.method_27022(entity);
		RenderLayer layer = this.getRenderLayer(entity, visible, ghost, bl3);
		if (layer != null)
		{
			int r = getOverlay(entity, 0);
			this.model.render(matrixStack, vertexConsumer, i, r, red, green, blue, ghost ? 0.15F : 1.0F);
			
		}

		if (!entity.isSpectator())
		{
			Iterator<?> var21 = this.features.iterator();
			while (var21.hasNext())
			{
				@SuppressWarnings("unchecked")
				FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>> feature = (FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>>) var21.next();
				feature.render(matrixStack, vertexConsumerProvider, i, entity, q, p, g, o, k, m);
			}
		}

		matrixStack.pop();

		if (this.hasLabel(entity))
		{
			this.renderLabelIfPresent(entity, entity.getDisplayName(), matrixStack, vertexConsumerProvider, i);
		}
	}

	public void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float posX, float posY, float u, float v, float red, float green, float blue)
	{
		vertexConsumer.vertex(matrix4f, posX, posY, 0).color(red, green, blue, 1F).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LIT).normal(matrix3f, 0, 1, 0).next();
	}

	@Override
	protected int getBlockLight(EntityFirefly entity, BlockPos blockPos)
	{
		return 15;
	}
}
