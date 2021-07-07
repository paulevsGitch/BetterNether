package paulevs.betternether.mixin.common;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.blockentities.BNSignBlockEntity;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	private static final Logger LOGGER = LogManager.getLogger();

	@Shadow
	public ServerPlayer player;

	@Inject(method = "onSignUpdate", at = @At(value = "HEAD"), cancellable = true)
	private void signUpdate(ServerboundSignUpdatePacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ServerGamePacketListenerImpl) (Object) this, (ServerLevel) this.player.getLevel());
		this.player.resetLastActionTime();
		ServerLevel serverWorld = this.player.getLevel();
		BlockPos blockPos = packet.getPos();
		if (serverWorld.hasChunkAt(blockPos)) {
			BlockState blockState = serverWorld.getBlockState(blockPos);
			BlockEntity blockEntity = serverWorld.getBlockEntity(blockPos);
			if (blockEntity instanceof BNSignBlockEntity) {
				BNSignBlockEntity signBlockEntity = (BNSignBlockEntity) blockEntity;
				if (!signBlockEntity.isEditable() || signBlockEntity.getEditor() != this.player) {
					LOGGER.warn("Player {} just tried to change non-editable sign", this.player.getName().getString());
					return;
				}

				String[] strings = packet.getLines();

				for (int i = 0; i < strings.length; ++i) {
					signBlockEntity.setTextOnRow(i, new TextComponent(ChatFormatting.stripFormatting(strings[i])));
				}

				signBlockEntity.setChanged();
				serverWorld.sendBlockUpdated(blockPos, blockState, blockState, 3);

				info.cancel();
			}
		}
	}
}
