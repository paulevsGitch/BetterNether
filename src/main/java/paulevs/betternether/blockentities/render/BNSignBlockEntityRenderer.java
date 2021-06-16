package paulevs.betternether.blockentities.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer.SignModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.OrderedText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNSignBlockEntity;
import paulevs.betternether.blocks.BNSign;
import paulevs.betternether.registry.BlocksRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



public class BNSignBlockEntityRenderer implements BlockEntityRenderer<BNSignBlockEntity> {
	private static final HashMap<Block, RenderLayer> LAYERS = Maps.newHashMap();
	private static RenderLayer defaultLayer;
	private final Map<SignType, SignModel> typeToModel;
	private final TextRenderer textRenderer;
	private static final int RENDER_DISTANCE = MathHelper.square(16);
	private static final int GLOWING_BLACK_COLOR = -988212;

	public BNSignBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.typeToModel = (Map)SignType.stream().collect(ImmutableMap.toImmutableMap((signType) -> {
			return signType;
		}, (signType) -> {
			return new SignBlockEntityRenderer.SignModel(ctx.getLayerModelPart(EntityModelLayers.createSign(signType)));
		}));
		this.textRenderer = ctx.getTextRenderer();
	}

	public void render(BNSignBlockEntity signBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider provider, int light, int overlay) {
		BlockState state = signBlockEntity.getCachedState();
		SignType signType = getSignType(state.getBlock());
		matrixStack.push();

		matrixStack.translate(0.5D, 0.5D, 0.5D);
		float angle = -((float) ((Integer) state.get(SignBlock.ROTATION) * 360) / 16.0F);

		SignBlockEntityRenderer.SignModel model = (SignBlockEntityRenderer.SignModel) this.typeToModel
				.get(signType);
		BlockState blockState = signBlockEntity.getCachedState();
		if (blockState.get(BNSign.FLOOR)) {
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angle));
			model.stick.visible = true;
		}
		else {
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angle + 180));
			matrixStack.translate(0.0D, -0.3125D, -0.4375D);
			model.stick.visible = false;
		}

		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getSignTextureId(signType);
		Objects.requireNonNull(model);
		VertexConsumer vertexConsumer = getConsumer(provider, state.getBlock());
		model.root.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.pop();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int color = getColor(signBlockEntity);

		OrderedText[] orderedTexts = signBlockEntity.updateSign(MinecraftClient.getInstance().shouldFilterText(),
				(text) -> {
					List<OrderedText> list = this.textRenderer.wrapLines(text, 90);
					OrderedText o = list.isEmpty() ? OrderedText.EMPTY : (OrderedText) list.get(0);
					return o==null?OrderedText.EMPTY:o;
				});
		int textColor;
		boolean sldRender;
		int lightLevel;
		if (signBlockEntity.isGlowingText()) {
			textColor = signBlockEntity.getTextColor().getSignColor();
			sldRender = shouldRender(signBlockEntity, textColor);
			lightLevel = 15728880;
		} else {
			textColor = color;
			sldRender = false;
			lightLevel = light;
		}

		for (int y = 0; y < 4; ++y) {
			OrderedText orderedText = orderedTexts[y];
			if (orderedText==null) {
				orderedText = OrderedText.EMPTY;
			}
			float x = (float) (-this.textRenderer.getWidth(orderedText) / 2);
			if (sldRender) {
				this.textRenderer.drawWithOutline(orderedText, x, (float) (y * 10 - 20), textColor, color,
						matrixStack.peek().getModel(), provider, lightLevel);
			} else {
				this.textRenderer.draw((OrderedText) orderedText, x, (float) (y * 10 - 20), textColor, false,
						matrixStack.peek().getModel(), provider, false, 0, lightLevel);
			}
		}

		for (int s = 0; s < 4; ++s) {
			OrderedText orderedText = signBlockEntity.getTextBeingEditedOnRow(s, (text) -> {
				List<OrderedText> list = textRenderer.wrapLines(text, 90);
				return list.isEmpty() ? OrderedText.EMPTY : (OrderedText) list.get(0);
			});
			if (orderedText != null) {
				float t = (float) (-textRenderer.getWidth(orderedText) / 2);
				textRenderer.draw((OrderedText) orderedText, t, (float) (s * 10 - 20), textColor, false, matrixStack.peek().getModel(), provider, false, 0, light);
			}
		}

		matrixStack.pop();
	}

	public static SignBlockEntityRenderer.SignModel createSignModel(EntityModelLoader entityModelLoader, SignType type) {
		return new SignBlockEntityRenderer.SignModel(
				entityModelLoader.getModelPart(EntityModelLayers.createSign(type)));
	}

	private static boolean shouldRender(BNSignBlockEntity sign, int signColor) {
		if (signColor == DyeColor.BLACK.getSignColor()) {
			return true;
		} else {
			MinecraftClient minecraftClient = MinecraftClient.getInstance();
			ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
			if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson()
					&& clientPlayerEntity.isUsingSpyglass()) {
				return true;
			} else {
				Entity entity = minecraftClient.getCameraEntity();
				return entity != null
						&& entity.squaredDistanceTo(Vec3d.ofCenter(sign.getPos())) < (double) RENDER_DISTANCE;
			}
		}
	}

	public static SignType getSignType(Block block) {
		SignType signType2;
		if (block instanceof AbstractSignBlock) {
			signType2 = ((AbstractSignBlock) block).getSignType();
		} else {
			signType2 = SignType.OAK;
		}

		return signType2;
	}

	private static int getColor(BNSignBlockEntity sign) {
		final int color = sign.getTextColor().getSignColor();
		final double k = 0.4D;
		int r = (int) ((double) NativeImage.getRed(color) * k);
		int g = (int) ((double) NativeImage.getGreen(color) * k);
		int b = (int) ((double) NativeImage.getBlue(color) * k);
		return (color == DyeColor.BLACK.getSignColor()) && sign.isGlowingText() ? GLOWING_BLACK_COLOR
				: NativeImage.getAbgrColor(0, r, g, b);
	}

	public static SpriteIdentifier getModelTexture(Block block) {
		SignType signType2;
		if (block instanceof AbstractSignBlock) {
			signType2 = ((AbstractSignBlock) block).getSignType();
		}
		else {
			signType2 = SignType.OAK;
		}

		return TexturedRenderLayers.getSignTextureId(signType2);
	}

	static {
		defaultLayer = RenderLayer.getEntitySolid(new Identifier("textures/entity/signs/oak.png"));
		BlocksRegistry.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new Identifier(BetterNether.MOD_ID, name));
			if (block instanceof BNSign) {
				RenderLayer layer = RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/signs/" + name + ".png"));
				LAYERS.put(block, layer);
			}
		});
	}

	public static VertexConsumer getConsumer(VertexConsumerProvider provider, Block block) {
		return provider.getBuffer(LAYERS.getOrDefault(block, defaultLayer));
	}
}
