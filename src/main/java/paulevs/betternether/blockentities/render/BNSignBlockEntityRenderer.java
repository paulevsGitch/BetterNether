package paulevs.betternether.blockentities.render;

import java.util.List;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer.SignModel;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.StringRenderable;
import net.minecraft.text.Style;
import net.minecraft.util.SignType;
import paulevs.betternether.blockentities.BNSignBlockEntity;

public class BNSignBlockEntityRenderer extends BlockEntityRenderer<BNSignBlockEntity>
{
	private final SignModel model = new SignBlockEntityRenderer.SignModel();

	public BNSignBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	public void render(BNSignBlockEntity signBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider provider, int light, int overlay)
	{
		BlockState state = signBlockEntity.getCachedState();
		matrixStack.push();

		matrixStack.translate(0.5D, 0.5D, 0.5D);
		float angle = -((float) ((Integer) state.get(SignBlock.ROTATION) * 360) / 16.0F);
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(angle));
		this.model.foot.visible = true;

		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		SpriteIdentifier spriteIdentifier = getModelTexture(state.getBlock());
		SignBlockEntityRenderer.SignModel var10002 = this.model;
		var10002.getClass();
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(provider, var10002::getLayer);
		this.model.field.render(matrixStack, vertexConsumer, light, overlay);
		this.model.foot.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.pop();
		TextRenderer textRenderer = this.dispatcher.getTextRenderer();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int m = signBlockEntity.getTextColor().getSignColor();
		int n = (int) ((double) NativeImage.getRed(m) * 0.4D);
		int o = (int) ((double) NativeImage.getGreen(m) * 0.4D);
		int p = (int) ((double) NativeImage.getBlue(m) * 0.4D);
		int q = NativeImage.getAbgrColor(0, p, o, n);

		for (int s = 0; s < 4; ++s)
		{
			StringRenderable stringRenderable = signBlockEntity.getTextBeingEditedOnRow(s, (stringRenderablex) -> {
				List<StringRenderable> list = textRenderer.getTextHandler().wrapLines((StringRenderable) stringRenderablex, 90, Style.EMPTY);
				return list.isEmpty() ? StringRenderable.EMPTY : (StringRenderable) list.get(0);
			});
			if (stringRenderable != null)
			{
				float t = (float) (-textRenderer.getWidth(stringRenderable) / 2);
				textRenderer.draw((StringRenderable) stringRenderable, t, (float) (s * 10 - 20), q, false, matrixStack.peek().getModel(), provider, false, 0, light);
			}
		}

		matrixStack.pop();
	}

	public static SpriteIdentifier getModelTexture(Block block)
	{
		SignType signType2;
		if (block instanceof AbstractSignBlock)
		{
			signType2 = ((AbstractSignBlock) block).getSignType();
		}
		else
		{
			signType2 = SignType.OAK;
		}

		return TexturedRenderLayers.getSignTextureId(signType2);
	}
}
