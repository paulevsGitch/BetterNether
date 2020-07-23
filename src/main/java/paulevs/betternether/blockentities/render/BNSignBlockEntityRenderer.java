package paulevs.betternether.blockentities.render;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Maps;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blockentities.BNSignBlockEntity;
import paulevs.betternether.blocks.BNSign;
import paulevs.betternether.registry.BlocksRegistry;

public class BNSignBlockEntityRenderer extends BlockEntityRenderer<BNSignBlockEntity>
{
	private static final HashMap<Integer, RenderLayer> LAYERS = Maps.newHashMap();
	private static RenderLayer defaultLayer;
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
		
		BlockState blockState = signBlockEntity.getCachedState();
		if (blockState.get(BNSign.FLOOR))
		{
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(angle));
			this.model.foot.visible = true;
		}
		else
		{
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(angle + 180));
			matrixStack.translate(0.0D, -0.3125D, -0.4375D);
			this.model.foot.visible = false;
		}

		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		VertexConsumer vertexConsumer = getConsumer(provider, state.getBlock());
		model.field.render(matrixStack, vertexConsumer, light, overlay);
		model.foot.render(matrixStack, vertexConsumer, light, overlay);
		matrixStack.pop();
		TextRenderer textRenderer = dispatcher.getTextRenderer();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int m = signBlockEntity.getTextColor().getSignColor();
		int n = (int) (NativeImage.getRed(m) * 0.4D);
		int o = (int) (NativeImage.getGreen(m) * 0.4D);
		int p = (int) (NativeImage.getBlue(m) * 0.4D);
		int q = NativeImage.getAbgrColor(0, p, o, n);

		for (int s = 0; s < 4; ++s)
		{
			StringRenderable stringRenderable = signBlockEntity.getTextBeingEditedOnRow(s, (stringRenderablex) -> {
				List<StringRenderable> list = textRenderer.getTextHandler().wrapLines((StringRenderable) stringRenderablex, 90, Style.EMPTY);
				return list.isEmpty() ? StringRenderable.EMPTY : (StringRenderable) list.get(0);
			});
			if (stringRenderable != null)
			{
				float t = -textRenderer.getWidth(stringRenderable) / 2;
				textRenderer.draw(stringRenderable, t, s * 10 - 20, q, false, matrixStack.peek().getModel(), provider, false, 0, light);
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
	
	static
	{
		BlocksRegistry.getPossibleBlocks().forEach((name) -> {
			Block block = Registry.BLOCK.get(new Identifier(BetterNether.MOD_ID, name));
			if (block instanceof BNSign)
			{
				RenderLayer layer = RenderLayer.getEntitySolid(new Identifier(BetterNether.MOD_ID, "textures/entity/signs/" + name + ".png"));
				LAYERS.put(Block.getRawIdFromState(block.getDefaultState()), layer);
				if (defaultLayer == null)
				{
					defaultLayer = layer;
				}
			}
		});
	}
	
	public static VertexConsumer getConsumer(VertexConsumerProvider provider, Block block)
	{
		return provider.getBuffer(LAYERS.getOrDefault(Block.getRawIdFromState(block.getDefaultState()), defaultLayer));
	}
}
