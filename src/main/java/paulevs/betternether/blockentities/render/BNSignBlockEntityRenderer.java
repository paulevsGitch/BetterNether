package paulevs.betternether.blockentities.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer.SignModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNSignBlockEntity;
import paulevs.betternether.blocks.BNSign;
import paulevs.betternether.registry.NetherBlocks;



public class BNSignBlockEntityRenderer implements BlockEntityRenderer<BNSignBlockEntity> {
	private static final HashMap<Block, RenderType> LAYERS = Maps.newHashMap();
	private static RenderType defaultLayer;
	private final Map<WoodType, SignModel> typeToModel;
	private final Font textRenderer;
	private static final int RENDER_DISTANCE = Mth.square(16);
	private static final int GLOWING_BLACK_COLOR = -988212;

	public BNSignBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		this.typeToModel = (Map)WoodType.values().collect(ImmutableMap.toImmutableMap((signType) -> {
			return signType;
		}, (signType) -> {
			return new SignRenderer.SignModel(ctx.bakeLayer(ModelLayers.createSignModelName(signType)));
		}));
		this.textRenderer = ctx.getFont();
	}

	public void render(BNSignBlockEntity signBlockEntity, float tickDelta, PoseStack matrixStack, MultiBufferSource provider, int light, int overlay) {
		BlockState state = signBlockEntity.getBlockState();
		WoodType signType = getSignType(state.getBlock());
		matrixStack.pushPose();

		matrixStack.translate(0.5D, 0.5D, 0.5D);
		float angle = -((float) ((Integer) state.getValue(StandingSignBlock.ROTATION) * 360) / 16.0F);

		SignRenderer.SignModel model = (SignRenderer.SignModel) this.typeToModel
				.get(signType);
		BlockState blockState = signBlockEntity.getBlockState();
		if (blockState.getValue(BNSign.FLOOR)) {
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(angle));
			model.stick.visible = true;
		}
		else {
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(angle + 180));
			matrixStack.translate(0.0D, -0.3125D, -0.4375D);
			model.stick.visible = false;
		}

		matrixStack.pushPose();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		Material spriteIdentifier = Sheets.getSignMaterial(signType);
		Objects.requireNonNull(model);
		VertexConsumer vertexConsumer = getConsumer(provider, state.getBlock());
		model.root.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.popPose();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int color = getColor(signBlockEntity);

		FormattedCharSequence[] orderedTexts = signBlockEntity.updateSign(Minecraft.getInstance().isTextFilteringEnabled(),
				(text) -> {
					List<FormattedCharSequence> list = this.textRenderer.split(text, 90);
					FormattedCharSequence o = list.isEmpty() ? FormattedCharSequence.EMPTY : (FormattedCharSequence) list.get(0);
					return o==null?FormattedCharSequence.EMPTY:o;
				});
		int textColor;
		boolean sldRender;
		int lightLevel;
		if (signBlockEntity.isGlowingText()) {
			textColor = signBlockEntity.getTextColor().getTextColor();
			sldRender = shouldRender(signBlockEntity, textColor);
			lightLevel = 15728880;
		} else {
			textColor = color;
			sldRender = false;
			lightLevel = light;
		}

		for (int y = 0; y < 4; ++y) {
			FormattedCharSequence orderedText = orderedTexts[y];
			if (orderedText==null) {
				orderedText = FormattedCharSequence.EMPTY;
			}
			float x = (float) (-this.textRenderer.width(orderedText) / 2);
			if (sldRender) {
				this.textRenderer.drawInBatch8xOutline(orderedText, x, (float) (y * 10 - 20), textColor, color,
						matrixStack.last().pose(), provider, lightLevel);
			} else {
				this.textRenderer.drawInBatch((FormattedCharSequence) orderedText, x, (float) (y * 10 - 20), textColor, false,
						matrixStack.last().pose(), provider, false, 0, lightLevel);
			}
		}

		for (int s = 0; s < 4; ++s) {
			FormattedCharSequence orderedText = signBlockEntity.getTextBeingEditedOnRow(s, (text) -> {
				List<FormattedCharSequence> list = textRenderer.split(text, 90);
				return list.isEmpty() ? FormattedCharSequence.EMPTY : (FormattedCharSequence) list.get(0);
			});
			if (orderedText != null) {
				float t = (float) (-textRenderer.width(orderedText) / 2);
				textRenderer.drawInBatch((FormattedCharSequence) orderedText, t, (float) (s * 10 - 20), textColor, false, matrixStack.last().pose(), provider, false, 0, light);
			}
		}

		matrixStack.popPose();
	}

	public static SignRenderer.SignModel createSignModel(EntityModelSet entityModelLoader, WoodType type) {
		return new SignRenderer.SignModel(
				entityModelLoader.bakeLayer(ModelLayers.createSignModelName(type)));
	}

	private static boolean shouldRender(BNSignBlockEntity sign, int signColor) {
		if (signColor == DyeColor.BLACK.getTextColor()) {
			return true;
		} else {
			Minecraft minecraftClient = Minecraft.getInstance();
			LocalPlayer clientPlayerEntity = minecraftClient.player;
			if (clientPlayerEntity != null && minecraftClient.options.getCameraType().isFirstPerson()
					&& clientPlayerEntity.isScoping()) {
				return true;
			} else {
				Entity entity = minecraftClient.getCameraEntity();
				return entity != null
						&& entity.distanceToSqr(Vec3.atCenterOf(sign.getBlockPos())) < (double) RENDER_DISTANCE;
			}
		}
	}

	public static WoodType getSignType(Block block) {
		WoodType signType2;
		if (block instanceof SignBlock) {
			signType2 = ((SignBlock) block).type();
		} else {
			signType2 = WoodType.OAK;
		}

		return signType2;
	}

	private static int getColor(BNSignBlockEntity sign) {
		final int color = sign.getTextColor().getTextColor();
		final double k = 0.4D;
		int r = (int) ((double) NativeImage.getR(color) * k);
		int g = (int) ((double) NativeImage.getG(color) * k);
		int b = (int) ((double) NativeImage.getB(color) * k);
		return (color == DyeColor.BLACK.getTextColor()) && sign.isGlowingText() ? GLOWING_BLACK_COLOR
				: NativeImage.combine(0, r, g, b);
	}

	public static Material getModelTexture(Block block) {
		WoodType signType2;
		if (block instanceof SignBlock) {
			signType2 = ((SignBlock) block).type();
		}
		else {
			signType2 = WoodType.OAK;
		}

		return Sheets.getSignMaterial(signType2);
	}

	static {
		defaultLayer = RenderType.entitySolid(new ResourceLocation("textures/entity/signs/oak.png"));
		NetherBlocks.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			if (block instanceof BNSign) {
				RenderType layer = RenderType.entitySolid(new ResourceLocation(BetterNether.MOD_ID, "textures/entity/signs/" + name + ".png"));
				LAYERS.put(block, layer);
			}
		});
	}

	public static VertexConsumer getConsumer(MultiBufferSource provider, Block block) {
		return provider.getBuffer(LAYERS.getOrDefault(block, defaultLayer));
	}
}
