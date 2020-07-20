package paulevs.betternether;

import java.util.Arrays;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer.SignModel;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.Matrix4f;
import paulevs.betternether.blockentities.BNSignBlockEntity;
import paulevs.betternether.blockentities.render.BNSignBlockEntityRenderer;
import paulevs.betternether.blocks.BNSign;

@Environment(EnvType.CLIENT)
public class BNSignEditScreen extends Screen
{
	private final SignModel model = new SignModel();
	private final BNSignBlockEntity sign;
	private int ticksSinceOpened;
	private int currentRow;
	private SelectionManager selectionManager;
	private final String[] text = (String[]) Util.make(new String[4], (strings) -> {
		Arrays.fill(strings, "");
	});

	public BNSignEditScreen(BNSignBlockEntity sign)
	{
		super(new TranslatableText("sign.edit"));
		this.sign = sign;
	}

	protected void init()
	{
		this.client.keyboard.enableRepeatEvents(true);
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, ScreenTexts.DONE,
				(buttonWidget) -> {
					this.finishEditing();
				}));
		this.sign.setEditable(false);
		this.selectionManager = new SelectionManager(() -> {
			return this.text[this.currentRow];
		}, (string) -> {
			this.text[this.currentRow] = string;
			this.sign.setTextOnRow(this.currentRow, new LiteralText(string));
		}, SelectionManager.makeClipboardGetter(this.client), SelectionManager.makeClipboardSetter(this.client), (string) -> {
			return this.client.textRenderer.getWidth(string) <= 90;
		});
	}

	public void removed()
	{
		this.client.keyboard.enableRepeatEvents(false);
		ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.getNetworkHandler();
		if (clientPlayNetworkHandler != null)
		{
			clientPlayNetworkHandler.sendPacket(new UpdateSignC2SPacket(this.sign.getPos(), this.text[0], this.text[1], this.text[2], this.text[3]));
		}

		this.sign.setEditable(true);
	}

	public void tick()
	{
		++this.ticksSinceOpened;
		if (!this.sign.getType().supports(this.sign.getCachedState().getBlock()))
		{
			this.finishEditing();
			System.out.println(this.sign.getType());
		}
	}

	private void finishEditing()
	{
		this.sign.markDirty();
		this.client.openScreen((Screen) null);
	}

	public boolean charTyped(char chr, int keyCode)
	{
		this.selectionManager.insert(chr);
		return true;
	}

	public void onClose()
	{
		this.finishEditing();
	}

	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if (keyCode == 265)
		{
			this.currentRow = this.currentRow - 1 & 3;
			this.selectionManager.moveCaretToEnd();
			return true;
		}
		else if (keyCode != 264 && keyCode != 257 && keyCode != 335)
		{
			return this.selectionManager.handleSpecialKey(keyCode) ? true
					: super.keyPressed(keyCode, scanCode, modifiers);
		}
		else
		{
			this.currentRow = this.currentRow + 1 & 3;
			this.selectionManager.moveCaretToEnd();
			return true;
		}
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		DiffuseLighting.disableGuiDepthLighting();
		this.renderBackground(matrices);
		this.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 16777215);
		matrices.push();
		matrices.translate((double) (this.width / 2), 0.0D, 50.0D);
		
		matrices.scale(93.75F, -93.75F, 93.75F);
		matrices.translate(0.0D, -1.3125D, 0.0D);
		BlockState blockState = this.sign.getCachedState();
		boolean bl = blockState.get(BNSign.FLOOR);
		
		if (!bl)
		{
			matrices.translate(0.0D, -0.3125D, 0.0D);
		}

		boolean bl2 = this.ticksSinceOpened / 6 % 2 == 0;
		
		matrices.push();
		matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
		VertexConsumerProvider.Immediate immediate = this.client.getBufferBuilders().getEntityVertexConsumers();
		VertexConsumer vertexConsumer = BNSignBlockEntityRenderer.getConsumer(immediate, blockState.getBlock());
		this.model.field.render(matrices, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV);
		
		if (bl)
		{
			this.model.foot.render(matrices, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV);
		}

		matrices.pop();
		
		matrices.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int i = this.sign.getTextColor().getSignColor();
		int j = this.selectionManager.getSelectionStart();
		int k = this.selectionManager.getSelectionEnd();
		int l = this.currentRow * 10 - this.text.length * 5;
		Matrix4f matrix4f = matrices.peek().getModel();

		int m;
		String string2;
		int s;
		int t;
		for (m = 0; m < this.text.length; ++m)
		{
			string2 = this.text[m];
			if (string2 != null)
			{
				if (this.textRenderer.isRightToLeft())
				{
					string2 = this.textRenderer.mirror(string2);
				}

				float n = (float) (-this.client.textRenderer.getWidth(string2) / 2);
				this.client.textRenderer.draw(string2, n, (float) (m * 10 - this.text.length * 5), i, false,
						matrix4f, immediate, false, 0, 15728880, false);
				if (m == this.currentRow && j >= 0 && bl2)
				{
					s = this.client.textRenderer
							.getWidth(string2.substring(0, Math.max(Math.min(j, string2.length()), 0)));
					t = s - this.client.textRenderer.getWidth(string2) / 2;
					if (j >= string2.length())
					{
						this.client.textRenderer.draw("_", (float) t, (float) l, i, false, matrix4f, immediate, false,
								0, 15728880, false);
					}
				}
			}
		}

		immediate.draw();

		for (m = 0; m < this.text.length; ++m)
		{
			string2 = this.text[m];
			if (string2 != null && m == this.currentRow && j >= 0)
			{
				int r = this.client.textRenderer
						.getWidth(string2.substring(0, Math.max(Math.min(j, string2.length()), 0)));
				s = r - this.client.textRenderer.getWidth(string2) / 2;
				if (bl2 && j < string2.length())
				{
					int var31 = l - 1;
					int var10003 = s + 1;
					this.client.textRenderer.getClass();
					fill(matrices, s, var31, var10003, l + 9, -16777216 | i);
				}

				if (k != j)
				{
					t = Math.min(j, k);
					int u = Math.max(j, k);
					int v = this.client.textRenderer.getWidth(string2.substring(0, t))
							- this.client.textRenderer.getWidth(string2) / 2;
					int w = this.client.textRenderer.getWidth(string2.substring(0, u))
							- this.client.textRenderer.getWidth(string2) / 2;
					int x = Math.min(v, w);
					int y = Math.max(v, w);
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferBuilder = tessellator.getBuffer();
					RenderSystem.disableTexture();
					RenderSystem.enableColorLogicOp();
					RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
					bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
					float var32 = (float) x;
					this.client.textRenderer.getClass();
					bufferBuilder.vertex(matrix4f, var32, (float) (l + 9), 0.0F).color(0, 0, 255, 255).next();
					var32 = (float) y;
					this.client.textRenderer.getClass();
					bufferBuilder.vertex(matrix4f, var32, (float) (l + 9), 0.0F).color(0, 0, 255, 255).next();
					bufferBuilder.vertex(matrix4f, (float) y, (float) l, 0.0F).color(0, 0, 255, 255).next();
					bufferBuilder.vertex(matrix4f, (float) x, (float) l, 0.0F).color(0, 0, 255, 255).next();
					bufferBuilder.end();
					BufferRenderer.draw(bufferBuilder);
					RenderSystem.disableColorLogicOp();
					RenderSystem.enableTexture();
				}
			}
		}

		matrices.pop();
		DiffuseLighting.enableGuiDepthLighting();
		super.render(matrices, mouseX, mouseY, delta);
	}
}
