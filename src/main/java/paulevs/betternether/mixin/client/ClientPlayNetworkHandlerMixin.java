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
	private Minecraft client;

	@Shadow
	private ClientLevel world;

	@Inject(method = "onSignEditorOpen", at = @At(value = "HEAD"), cancellable = true)
	public void openSignEditor(ClientboundOpenSignEditorPacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener) (Object) this, (BlockableEventLoop<?>) client);
		BlockEntity blockEntity = this.world.getBlockEntity(packet.getPos());
		if (blockEntity instanceof BNSignBlockEntity) {
			BNSignBlockEntity sign = (BNSignBlockEntity) blockEntity;
			client.setScreen(new BNSignEditScreen(sign, this.client.isTextFilteringEnabled()));
			info.cancel();
		}
	}

	@Inject(method = "onBlockEntityUpdate", at = @At(value = "HEAD"), cancellable = true)
	public void onEntityUpdate(ClientboundBlockEntityDataPacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener) (Object) this, (BlockableEventLoop<?>) client);
		BlockPos blockPos = packet.getPos();
		BlockEntity blockEntity = this.client.level.getBlockEntity(blockPos);
		if (blockEntity instanceof BNSignBlockEntity) {
			blockEntity.load(packet.getTag());
			info.cancel();
		}
	}
}
