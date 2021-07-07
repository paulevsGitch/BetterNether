package paulevs.betternether.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.BNSignEditScreen;
import paulevs.betternether.blockentities.BNSignBlockEntity;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
	@Shadow
	private Minecraft minecraft;

	@Shadow
	private ClientLevel level;

	@Inject(method = "handleOpenSignEditor", at = @At(value = "HEAD"), cancellable = true)
	public void openSignEditor(ClientboundOpenSignEditorPacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener) (Object) this, (BlockableEventLoop<?>) minecraft);
		BlockEntity blockEntity = this.level.getBlockEntity(packet.getPos());
		if (blockEntity instanceof BNSignBlockEntity) {
			BNSignBlockEntity sign = (BNSignBlockEntity) blockEntity;
			minecraft.setScreen(new BNSignEditScreen(sign, this.minecraft.isTextFilteringEnabled()));
			info.cancel();
		}
	}

	@Inject(method = "handleBlockEntityData", at = @At(value = "HEAD"), cancellable = true)
	public void onEntityUpdate(ClientboundBlockEntityDataPacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener) (Object) this, (BlockableEventLoop<?>) minecraft);
		BlockPos blockPos = packet.getPos();
		BlockEntity blockEntity = this.minecraft.level.getBlockEntity(blockPos);
		if (blockEntity instanceof BNSignBlockEntity) {
			blockEntity.load(packet.getTag());
			info.cancel();
		}
	}
}
