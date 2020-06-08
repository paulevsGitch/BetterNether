package paulevs.betternether.blockentities.render;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.Texts;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.text.Text;
import net.minecraft.util.SignType;
import paulevs.betternether.blockentities.NetherSignBlockEntity;
import paulevs.betternether.blocks.BNSign;

public class NetherSignBlockEntityRenderer extends BlockEntityRenderer<NetherSignBlockEntity>
{
	private final SignBlockEntityRenderer.SignModel model = new SignBlockEntityRenderer.SignModel();

	public NetherSignBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}

	@Override
	public void render(NetherSignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j)
	{
		BlockState blockState = signBlockEntity.getCachedState();
		matrixStack.push();
		float h = -(blockState.get(BNSign.ROTATION) * 360F / 16.0F);
		
		matrixStack.translate(0.5D, 0.5D, 0.5D);
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(h));
		
		if (blockState.get(BNSign.IS_FLOOR))
		{
			this.model.foot.visible = true;
		}
		else
		{
			matrixStack.translate(0.0D, -0.3125D, -0.4375D);
			this.model.foot.visible = false;
		}

		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		SpriteIdentifier spriteIdentifier = getModelTexture(blockState.getBlock());
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, model::getLayer);
		this.model.field.render(matrixStack, vertexConsumer, i, j);
		this.model.foot.render(matrixStack, vertexConsumer, i, j);
		matrixStack.pop();
		TextRenderer textRenderer = this.dispatcher.getTextRenderer();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int m = signBlockEntity.getTextColor().getSignColor();
		int n = (int) ((double) NativeImage.method_24033(m) * 0.4D);
		int o = (int) ((double) NativeImage.method_24034(m) * 0.4D);
		int p = (int) ((double) NativeImage.method_24035(m) * 0.4D);
		int q = NativeImage.method_24031(0, p, o, n);

		for (int r = 0; r < 4; ++r)
		{
			String string = signBlockEntity.getTextBeingEditedOnRow(r, (text) -> {
				List<Text> list = Texts.wrapLines(text, 90, textRenderer, false, true);
				return list.isEmpty() ? "" : ((Text) list.get(0)).asFormattedString();
			});
			if (string != null)
			{
				float s = (float) (-textRenderer.getStringWidth(string) / 2);
				textRenderer.draw(string, s, (float) (r * 10 - signBlockEntity.text.length * 5), q, false,
						matrixStack.peek().getModel(), vertexConsumerProvider, false, 0, i);
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

	@Environment(EnvType.CLIENT)
	public static final class SignModel extends Model
	{
		public final ModelPart field = new ModelPart(64, 32, 0, 0);
		public final ModelPart foot;

		public SignModel()
		{
			super(RenderLayer::getEntityCutoutNoCull);
			this.field.addCuboid(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
			this.foot = new ModelPart(64, 32, 0, 14);
			this.foot.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
		}

		public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
				float green, float blue, float alpha)
		{
			this.field.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
			this.foot.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		}
	}
}