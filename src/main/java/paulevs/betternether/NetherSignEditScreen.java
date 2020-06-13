package paulevs.betternether;

public class NetherSignEditScreen //extends Screen
{
	/*private final NetherSignBlockEntityRenderer.SignModel model = new NetherSignBlockEntityRenderer.SignModel();
	private final NetherSignBlockEntity sign;
	private int ticksSinceOpened;
	private int currentRow;
	private SelectionManager selectionManager;

	public NetherSignEditScreen(NetherSignBlockEntity sign)
	{
		super(new TranslatableText("sign.edit", new Object[0]));
		this.sign = sign;
	}

	protected void init()
	{
		this.minecraft.keyboard.enableRepeatEvents(true);
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20,
				I18n.translate("gui.done"), (buttonWidget) -> {
					this.finishEditing();
				}));
		this.sign.setEditable(false);
		this.selectionManager = new SelectionManager(this.minecraft, () -> {
			return this.sign.getTextOnRow(this.currentRow).getString();
		}, (string) -> {
			this.sign.setTextOnRow(this.currentRow, new LiteralText(string));
		}, 90);
	}

	public void removed()
	{
		this.minecraft.keyboard.enableRepeatEvents(false);
		ClientPlayNetworkHandler clientPlayNetworkHandler = this.minecraft.getNetworkHandler();
		if (clientPlayNetworkHandler != null)
		{
			clientPlayNetworkHandler.sendPacket(new UpdateSignC2SPacket(this.sign.getPos(), this.sign.getTextOnRow(0),
					this.sign.getTextOnRow(1), this.sign.getTextOnRow(2), this.sign.getTextOnRow(3)));
		}

		this.sign.setEditable(true);
	}

	public void tick()
	{
		++this.ticksSinceOpened;
		if (!this.sign.getType().supports(this.sign.getCachedState().getBlock()))
		{
			this.finishEditing();
		}

	}

	private void finishEditing()
	{
		this.sign.markDirty();
		this.minecraft.openScreen((Screen) null);
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

	public void render(int mouseX, int mouseY, float delta)
	{
		DiffuseLighting.disableGuiDepthLighting();
		this.renderBackground();
		this.drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 40, 16777215);
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.push();
		matrixStack.translate((double) (this.width / 2), 0.0D, 50.0D);
		matrixStack.scale(93.75F, -93.75F, 93.75F);
		matrixStack.translate(0.0D, -1.3125D, 0.0D);
		BlockState blockState = this.sign.getCachedState();
		boolean bl = blockState.getBlock() instanceof SignBlock;
		if (!bl)
		{
			matrixStack.translate(0.0D, -0.3125D, 0.0D);
		}

		boolean bl2 = this.ticksSinceOpened / 6 % 2 == 0;
		matrixStack.push();
		matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
		VertexConsumerProvider.Immediate immediate = this.minecraft.getBufferBuilders().getEntityVertexConsumers();
		SpriteIdentifier spriteIdentifier = NetherSignBlockEntityRenderer.getModelTexture(blockState.getBlock());
		NetherSignBlockEntityRenderer.SignModel var10002 = this.model;
		var10002.getClass();
		VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(immediate, var10002::getLayer);
		this.model.field.render(matrixStack, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV);
		if (bl)
		{
			this.model.foot.render(matrixStack, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV);
		}

		matrixStack.pop();
		matrixStack.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
		matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
		int i = this.sign.getTextColor().getSignColor();
		String[] strings = new String[4];

		for (int j = 0; j < strings.length; ++j)
		{
			strings[j] = this.sign.getTextBeingEditedOnRow(j, (text) -> {
				List<Text> list = Texts.wrapLines(text, 90, this.minecraft.textRenderer, false, true);
				return list.isEmpty() ? "" : ((Text) list.get(0)).asFormattedString();
			});
		}

		Matrix4f matrix4f = matrixStack.peek().getModel();
		int k = this.selectionManager.getSelectionStart();
		int l = this.selectionManager.getSelectionEnd();
		int m = this.minecraft.textRenderer.isRightToLeft() ? -1 : 1;
		int n = this.currentRow * 10 - this.sign.text.length * 5;

		int s;
		String string2;
		int u;
		int v;
		for (s = 0; s < strings.length; ++s)
		{
			string2 = strings[s];
			if (string2 != null)
			{
				float p = (float) (-this.minecraft.textRenderer.getStringWidth(string2) / 2);
				this.minecraft.textRenderer.draw(string2, p, (float) (s * 10 - this.sign.text.length * 5), i, false,
						matrix4f, immediate, false, 0, 15728880);
				if (s == this.currentRow && k >= 0 && bl2)
				{
					u = this.minecraft.textRenderer
							.getStringWidth(string2.substring(0, Math.max(Math.min(k, string2.length()), 0)));
					v = (u - this.minecraft.textRenderer.getStringWidth(string2) / 2) * m;
					if (k >= string2.length())
					{
						this.minecraft.textRenderer.draw("_", (float) v, (float) n, i, false, matrix4f, immediate,
								false, 0, 15728880);
					}
				}
			}
		}

		immediate.draw();

		for (s = 0; s < strings.length; ++s)
		{
			string2 = strings[s];
			if (string2 != null && s == this.currentRow && k >= 0)
			{
				int t = this.minecraft.textRenderer
						.getStringWidth(string2.substring(0, Math.max(Math.min(k, string2.length()), 0)));
				u = (t - this.minecraft.textRenderer.getStringWidth(string2) / 2) * m;
				if (bl2 && k < string2.length())
				{
					int var34 = n - 1;
					int var10003 = u + 1;
					this.minecraft.textRenderer.getClass();
					fill(matrix4f, u, var34, var10003, n + 9, -16777216 | i);
				}

				if (l != k)
				{
					v = Math.min(k, l);
					int w = Math.max(k, l);
					int x = (this.minecraft.textRenderer.getStringWidth(string2.substring(0, v))
							- this.minecraft.textRenderer.getStringWidth(string2) / 2) * m;
					int y = (this.minecraft.textRenderer.getStringWidth(string2.substring(0, w))
							- this.minecraft.textRenderer.getStringWidth(string2) / 2) * m;
					int z = Math.min(x, y);
					int aa = Math.max(x, y);
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferBuilder = tessellator.getBuffer();
					RenderSystem.disableTexture();
					RenderSystem.enableColorLogicOp();
					RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
					bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
					float var35 = (float) z;
					this.minecraft.textRenderer.getClass();
					bufferBuilder.vertex(matrix4f, var35, (float) (n + 9), 0.0F).color(0, 0, 255, 255).next();
					var35 = (float) aa;
					this.minecraft.textRenderer.getClass();
					bufferBuilder.vertex(matrix4f, var35, (float) (n + 9), 0.0F).color(0, 0, 255, 255).next();
					bufferBuilder.vertex(matrix4f, (float) aa, (float) n, 0.0F).color(0, 0, 255, 255).next();
					bufferBuilder.vertex(matrix4f, (float) z, (float) n, 0.0F).color(0, 0, 255, 255).next();
					bufferBuilder.end();
					BufferRenderer.draw(bufferBuilder);
					RenderSystem.disableColorLogicOp();
					RenderSystem.enableTexture();
				}
			}
		}

		matrixStack.pop();
		DiffuseLighting.enableGuiDepthLighting();
		super.render(mouseX, mouseY, delta);
	}*/
}
