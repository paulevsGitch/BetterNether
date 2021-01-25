package com.paulevs.betternether.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.paulevs.betternether.blockentities.BNSignTileEntity;
import com.paulevs.betternether.blockentities.render.BNSignTileEntityRenderer;
import com.paulevs.betternether.blocks.BNSign;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.fonts.TextInputUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;

public class SignEditScreen extends Screen {
    private final SignTileEntityRenderer.SignModel model = new SignTileEntityRenderer.SignModel();
    private final BNSignTileEntity sign;
    private int ticksSinceOpened;
    private int currentRow;
    private TextInputUtil selectionManager;
    private final String[] text = (String[]) Util.make(new String[4], (strings) -> {
        Arrays.fill(strings, "");
    });

    public SignEditScreen(BNSignTileEntity sign) {
        super(new TranslationTextComponent("sign.edit"));
        this.sign = sign;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, DialogTexts.GUI_DONE,
                (buttonWidget) -> {
                    this.finishEditing();
                }));
        this.sign.setEditable(false);
        this.selectionManager = new TextInputUtil(() -> {
            return this.text[this.currentRow];
        }, (string) -> {
            this.text[this.currentRow] = string;
            this.sign.setTextOnRow(this.currentRow, new StringTextComponent(string));
        }, TextInputUtil.getClipboardTextSupplier(this.minecraft), TextInputUtil.getClipboardTextSetter(this.minecraft),
                (string) -> {
                    return this.minecraft.fontRenderer.getStringWidth(string) <= 90;
                });
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        ClientPlayNetHandler clientPlayNetworkHandler = this.minecraft.getConnection();
        if (clientPlayNetworkHandler != null) {
            clientPlayNetworkHandler.sendPacket(new CUpdateSignPacket(this.sign.getPos(), this.text[0], this.text[1],
                    this.text[2], this.text[3]));
        }

        this.sign.setEditable(true);
    }

    @Override
    public void tick() {
        ++this.ticksSinceOpened;
        if (!this.sign.getType().isValidBlock(this.sign.getBlockState().getBlock())) {
            this.finishEditing();
        }
    }

    private void finishEditing() {
        this.sign.markDirty();
        this.minecraft.displayGuiScreen((Screen) null);
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        this.selectionManager.putChar(chr);
        return true;
    }

    @Override
    public void closeScreen() {
        this.finishEditing();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) {
            this.currentRow = this.currentRow - 1 & 3;
            this.selectionManager.moveCursorToEnd();
            return true;
        } else if (keyCode != 264 && keyCode != 257 && keyCode != 335) {
            return this.selectionManager.specialKeyPressed(keyCode) ? true
                    : super.keyPressed(keyCode, scanCode, modifiers);
        } else {
            this.currentRow = this.currentRow + 1 & 3;
            this.selectionManager.moveCursorToEnd();
            return true;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderHelper.setupGuiFlatDiffuseLighting();
        this.renderBackground(matrices);
        AbstractGui.drawCenteredString(matrices, this.font, this.title, this.width / 2, 40, 16777215);
        matrices.push();
        matrices.translate((double) (this.width / 2), 0.0D, 50.0D);

        matrices.scale(93.75F, -93.75F, 93.75F);
        matrices.translate(0.0D, -1.3125D, 0.0D);
        BlockState blockState = this.sign.getBlockState();
        boolean bl = blockState.get(BNSign.FLOOR);

        if (!bl) {
            matrices.translate(0.0D, -0.3125D, 0.0D);
        }

        boolean bl2 = this.ticksSinceOpened / 6 % 2 == 0;

        matrices.push();
        matrices.scale(0.6666667F, -0.6666667F, -0.6666667F);
        IRenderTypeBuffer.Impl immediate = this.minecraft.getRenderTypeBuffers().getBufferSource();
        IVertexBuilder vertexConsumer = BNSignTileEntityRenderer.getConsumer(immediate, blockState.getBlock());
        this.model.signBoard.render(matrices, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);

        if (bl) {
            this.model.signStick.render(matrices, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
        }

        matrices.pop();

        matrices.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
        matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = this.sign.getTextColor().getTextColor();
        int j = this.selectionManager.getEndIndex();
        int k = this.selectionManager.getStartIndex();
        int l = this.currentRow * 10 - this.text.length * 5;
        Matrix4f matrix4f = matrices.getLast().getMatrix();

        int m;
        String string2;
        int s;
        int t;
        for (m = 0; m < this.text.length; ++m) {
            string2 = this.text[m];
            if (string2 != null) {
                if (this.font.getBidiFlag()) {
                    string2 = this.font.bidiReorder(string2);
                }

                float n = (float) (-this.minecraft.fontRenderer.getStringWidth(string2) / 2);
                this.minecraft.fontRenderer.func_238411_a_(string2, n, (float) (m * 10 - this.text.length * 5), i, false, matrix4f,
                        immediate, false, 0, 15728880, false);
                if (m == this.currentRow && j >= 0 && bl2) {
                    s = this.minecraft.fontRenderer
                            .getStringWidth(string2.substring(0, Math.max(Math.min(j, string2.length()), 0)));
                    t = s - this.minecraft.fontRenderer.getStringWidth(string2) / 2;
                    if (j >= string2.length()) {
                        this.minecraft.fontRenderer.func_238411_a_("_", (float) t, (float) l, i, false, matrix4f, immediate, false,
                                0, 15728880, false);
                    }
                }
            }
        }

        immediate.finish();

        for (m = 0; m < this.text.length; ++m) {
            string2 = this.text[m];
            if (string2 != null && m == this.currentRow && j >= 0) {
                int r = this.minecraft.fontRenderer.getStringWidth(string2.substring(0, Math.max(Math.min(j, string2.length()), 0)));
                s = r - this.minecraft.fontRenderer.getStringWidth(string2) / 2;
                if (bl2 && j < string2.length()) {
                    int var31 = l - 1;
                    int var10003 = s + 1;
                    this.minecraft.fontRenderer.getClass();
                    fill(matrices, s, var31, var10003, l + 9, -16777216 | i);
                }

                if (k != j) {
                    t = Math.min(j, k);
                    int u = Math.max(j, k);
                    int v = this.minecraft.fontRenderer.getStringWidth(string2.substring(0, t))
                            - this.minecraft.fontRenderer.getStringWidth(string2) / 2;
                    int w = this.minecraft.fontRenderer.getStringWidth(string2.substring(0, u))
                            - this.minecraft.fontRenderer.getStringWidth(string2) / 2;
                    int x = Math.min(v, w);
                    int y = Math.max(v, w);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferBuilder = tessellator.getBuffer();
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                    bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    float var32 = (float) x;
                    this.minecraft.fontRenderer.getClass();
                    bufferBuilder.pos(matrix4f, var32, (float) (l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    var32 = (float) y;
                    this.minecraft.fontRenderer.getClass();
                    bufferBuilder.pos(matrix4f, var32, (float) (l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferBuilder.pos(matrix4f, (float) y, (float) l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferBuilder.pos(matrix4f, (float) x, (float) l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferBuilder.finishDrawing();
                    WorldVertexBufferUploader.draw(bufferBuilder);
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }

        matrices.pop();
        RenderHelper.setupGui3DDiffuseLighting();
        super.render(matrices, mouseX, mouseY, delta);
    }
}