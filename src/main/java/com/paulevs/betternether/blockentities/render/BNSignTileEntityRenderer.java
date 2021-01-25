package com.paulevs.betternether.blockentities.render;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.paulevs.betternether.BetterNether;
import com.paulevs.betternether.blockentities.BNSignTileEntity;
import com.paulevs.betternether.blocks.BNSign;
import com.paulevs.betternether.registry.RegistryHandler;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer.SignModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.registries.ForgeRegistries;


public class BNSignTileEntityRenderer extends TileEntityRenderer<BNSignTileEntity> {
	private static final HashMap<Block, RenderType> LAYERS = Maps.newHashMap();
	private static RenderType defaultLayer;
	private final SignModel model = new SignTileEntityRenderer.SignModel();

	public BNSignTileEntityRenderer(TileEntityRendererDispatcher renderDispatcher) {
		super(renderDispatcher);
	}

	public void render(BNSignTileEntity signTileEntity, float tickDelta, MatrixStack matrixStack, IRenderTypeBuffer provider, int light, int overlay) {
		BlockState state = signTileEntity.getBlockState();
		matrixStack.push();

		matrixStack.translate(0.5D, 0.5D, 0.5D);
		float angle = -((float) ((Integer) state.get(StandingSignBlock.ROTATION) * 360) / 16.0F);

		BlockState blockState = signTileEntity.getBlockState();
		if (blockState.get(BNSign.FLOOR)) {
			matrixStack.rotate(Vector3f.YP.rotationDegrees(angle));
			this.model.signStick.showModel = true;
		}
		else {
			matrixStack.rotate(Vector3f.YP.rotationDegrees(angle + 180));
			matrixStack.translate(0.0D, -0.3125D, -0.4375D);
			this.model.signStick.showModel = false;
		}

		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		IVertexBuilder vertexConsumer = getConsumer(provider, state.getBlock());
		model.signBoard.render(matrixStack, vertexConsumer, light, overlay);
		model.signStick.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.pop();
		FontRenderer textRenderer = renderDispatcher.getFontRenderer();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int m = signTileEntity.getTextColor().getTextColor();
		int n = (int) (NativeImage.getRed(m) * 0.4D);
		int o = (int) (NativeImage.getGreen(m) * 0.4D);
		int p = (int) (NativeImage.getBlue(m) * 0.4D);
		int q = NativeImage.getCombined(0, p, o, n);

		for (int s = 0; s < 4; ++s) {
			IReorderingProcessor orderedText = signTileEntity.getTextBeingEditedOnRow(s, (text) -> {
				List<IReorderingProcessor> list = textRenderer.trimStringToWidth(text, 90);
				return list.isEmpty() ? IReorderingProcessor.field_242232_a : (IReorderingProcessor) list.get(0);
			});
			if (orderedText != null) {
				float t = (float) (-textRenderer.func_243245_a(orderedText) / 2);
				textRenderer.func_238416_a_((IReorderingProcessor) orderedText, t, (float) (s * 10 - 20), q, false, matrixStack.getLast().getMatrix(), provider, false, 0, light);
			}
		}

		matrixStack.pop();
	}

	public static RenderMaterial getModelTexture(Block block) {
		WoodType signType2;
		if (block instanceof AbstractSignBlock) {
			signType2 = ((AbstractSignBlock) block).getWoodType();
		}
		else {
			signType2 = WoodType.OAK;
		}

		return Atlases.getSignMaterial(signType2);
	}

	static {
		defaultLayer = RenderType.getEntitySolid(new ResourceLocation("textures/entity/signs/oak.png"));
		RegistryHandler.getPossibleBlocks().forEach((source) -> {
			Block block = ForgeRegistries.BLOCKS.getValue(source.getRegistryName());
			if (block instanceof BNSign) {
				RenderType layer = RenderType.getEntitySolid(new ResourceLocation(BetterNether.MOD_ID, "textures/entity/signs/" + source.getRegistryName().getPath() + ".png"));
				LAYERS.put(block, layer);
			}
		});
	}

	public static IVertexBuilder getConsumer(IRenderTypeBuffer provider, Block block) {
		return provider.getBuffer(LAYERS.getOrDefault(block, defaultLayer));
	}
}
