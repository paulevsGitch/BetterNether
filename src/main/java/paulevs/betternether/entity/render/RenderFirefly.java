package paulevs.betternether.entity.render;

import java.util.Iterator;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityFirefly;
import paulevs.betternether.entity.model.ModelEmpty;
import paulevs.betternether.entity.model.ModelEntityFirefly;
import paulevs.betternether.registry.EntityRenderRegistry;

public class RenderFirefly extends MobEntityRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/firefly.png");
	private static final int LIT = 15728880;
	private static final ModelEmpty emptyModel = new ModelEmpty();

	public RenderFirefly(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelEntityFirefly(ctx.getPart(EntityRenderRegistry.FIREFLY_LAYER)), 0);
	}

	@Override
	public Identifier getTexture(EntityFirefly entity) {
		return TEXTURE;
	}

	protected void renderGlow(EntityFirefly entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light){
		RenderLayer renderLayer = this.getRenderLayerTranslucent(entity, true, true, false);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);

		float red = entity.getRed();
		float green = entity.getGreen();
		float blue = entity.getBlue();

		matrixStack.push();
		matrixStack.translate(0, 0.125, 0);
		matrixStack.multiply(this.dispatcher.getRotation());
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
		MatrixStack.Entry entry = matrixStack.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();

		addVertex(matrix4f, matrix3f, vertexConsumer, -1, -1, 0F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, -1, 1F, 0.5F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, 1, 1, 1F, 1F, red, green, blue);
		addVertex(matrix4f, matrix3f, vertexConsumer, -1, 1, 0F, 1F, red, green, blue);

		if (renderLayer != null) {
			ModelEntityFirefly mdl = (ModelEntityFirefly)this.model;

			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

			RenderSystem.setShaderColor(red, green, blue, 0.1F);
			this.emptyModel.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  0.1f);
		}
		matrixStack.pop();
	}
	@Override
	public void render(EntityFirefly entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
		renderGlow(entity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	/**
	 * Gets the render layer appropriate for rendering the passed entity. Returns
	 * null if the entity should not be rendered.
	 */
	@Nullable
	@Override
	protected RenderLayer getRenderLayer(EntityFirefly entity, boolean showBody, boolean translucent, boolean showOutline) {
		//Identifier identifier = this.getTexture(entity);
		//return RenderPhaseAccessor.getFirefly(identifier);
		//return RenderLayer.getEntityAlpha(identifier);
		return super.getRenderLayer(entity, showBody, translucent, showOutline);
	}

	// getEntityTranslucent
	// getEntityNoOutline
	// getBeaconBeam
	// getEntityShadow
	@Nullable
	protected RenderLayer getRenderLayerTranslucent(EntityFirefly entity, boolean showBody, boolean translucent, boolean showOutline) {
		Identifier identifier = this.getTexture(entity);
		return RenderPhaseAccessor.getFirefly(identifier);
	}

	public void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float posX, float posY, float u, float v, float red, float green, float blue) {
		vertexConsumer
				.vertex(matrix4f, posX, posY, 0)
				.color(red, green, blue, 0.0F)
				.texture(u, v)
				.overlay(OverlayTexture.DEFAULT_UV)
				.light(LIT)
				.normal(matrix3f, 0, 1, 0).next();
	}

	@Override
	protected int getBlockLight(EntityFirefly entity, BlockPos blockPos) {
		return 15;
	}
}
