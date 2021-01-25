package com.paulevs.betternether.mixin;
import com.paulevs.betternether.blockentities.BNSignTileEntity;
import com.paulevs.betternether.screens.SignEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.tileentity.TileEntity;

@Mixin(ClientPlayNetHandler.class)
public class ClientPlayNetHandlerMixin {
    @Shadow
    private Minecraft client;

    @Shadow
    private ClientWorld world;

    @Inject(method = "handleSignEditorOpen", at = @At(value = "HEAD"), cancellable = true, remap = false)
    public void openSignEditor(SOpenSignMenuPacket packet, CallbackInfo info) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, (ClientPlayNetHandler) (Object) this, client);
        TileEntity blockEntity = this.world.getTileEntity(packet.getSignPosition());
        if (blockEntity instanceof BNSignTileEntity) {
            BNSignTileEntity sign = (BNSignTileEntity) blockEntity;
            client.displayGuiScreen(new SignEditScreen(sign));
            info.cancel();
        }
    }
}