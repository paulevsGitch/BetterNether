package paulevs.betternether.entity.render;

import java.util.Iterator;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
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

class FireflyGlowFeatureRenderer extends FeatureRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	public FireflyGlowFeatureRenderer(FeatureRendererContext<EntityFirefly, AnimalModel<EntityFirefly>> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, EntityFirefly livingEntity, float f, float g, float h, float j, float k, float l) {
		EntityModel<EntityFirefly> model = this.getContextModel();
		Identifier identifier = this.getTexture(livingEntity);
		RenderLayer renderLayer =  RenderPhaseAccessor.getFirefly(identifier);
		VertexConsumer vertexConsumer = vertices.getBuffer(renderLayer);

		if (model instanceof ModelEntityFirefly){
			float red = livingEntity.getRed();
			float green = livingEntity.getGreen();
			float blue = livingEntity.getBlue();

			((ModelEntityFirefly)model).getGlowPart().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  2f);
			//((ModelEntityFirefly)model).getGlowPart().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		}
	}

}

public class RenderFirefly extends MobEntityRenderer<EntityFirefly, AnimalModel<EntityFirefly>> {
	private static final Identifier TEXTURE = new Identifier(BetterNether.MOD_ID, "textures/entity/firefly.png");
	private static final int LIT = 15728880;
	private static final ModelEmpty emptyModel = new ModelEmpty();

	public RenderFirefly(EntityRendererFactory.Context ctx) {
		super(ctx, new ModelEntityFirefly(ctx.getPart(EntityRenderRegistry.FIREFLY_LAYER)), 0);

		this.addFeature(new FireflyGlowFeatureRenderer( this));
	}

	@Override
	public Identifier getTexture(EntityFirefly entity) {
		return TEXTURE;
	}

	void renderGlowModel(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha){
		EntityModel<EntityFirefly> model = this.getModel();
		if (model instanceof ModelEntityFirefly){
			((ModelEntityFirefly)model).getGlowPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
		} else {
			emptyModel.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}
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
			emptyModel.render(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue,  1f);
		}
		matrixStack.pop();
	}

	boolean renderOverlay = false;
	@Override
	public void render(EntityFirefly entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		renderGlow(entity, f, g, matrixStack, vertexConsumerProvider, i);
		renderOverlay = false;
		super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
		renderOverlay = true;
		//super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	/**
	 * Gets the render layer appropriate for rendering the passed entity. Returns
	 * null if the entity should not be rendered.
	 */
	@Nullable
	@Override
	protected RenderLayer getRenderLayer(EntityFirefly entity, boolean showBody, boolean translucent, boolean showOutline) {
		if (renderOverlay) return this.getRenderLayerTranslucent(entity,  showBody,  translucent,  showOutline);
		return super.getRenderLayer(entity, showBody, translucent, showOutline);
	}

	@Nullable
	protected RenderLayer getRenderLayerTranslucent(EntityFirefly entity, boolean showBody, boolean translucent, boolean showOutline) {
		Identifier identifier = this.getTexture(entity);
		return RenderPhaseAccessor.getFirefly(identifier);
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

	@Override
	protected int getBlockLight(EntityFirefly entity, BlockPos blockPos) {
		return 15;
	}
}
