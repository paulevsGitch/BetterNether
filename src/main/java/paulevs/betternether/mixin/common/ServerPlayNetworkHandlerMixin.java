package paulevs.betternether.mixin.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import paulevs.betternether.blockentities.BNSignBlockEntity;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin
{
	@Shadow
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "onSignUpdate", at = @At(value = "HEAD"), cancellable = true)
	private void signUpdate(UpdateSignC2SPacket packet, CallbackInfo info)
	{
		NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, (ServerWorld) this.player.getServerWorld());
		this.player.updateLastActionTime();
		ServerWorld serverWorld = this.player.getServerWorld();
		BlockPos blockPos = packet.getPos();
		if (serverWorld.isChunkLoaded(blockPos))
		{
			BlockState blockState = serverWorld.getBlockState(blockPos);
			BlockEntity blockEntity = serverWorld.getBlockEntity(blockPos);
			if (blockEntity instanceof BNSignBlockEntity)
			{
				BNSignBlockEntity signBlockEntity = (BNSignBlockEntity) blockEntity;
				if (!signBlockEntity.isEditable() || signBlockEntity.getEditor() != this.player)
				{
					LOGGER.warn("Player {} just tried to change non-editable sign", this.player.getName().getString());
					return;
				}

				String[] strings = packet.getText();

				for (int i = 0; i < strings.length; ++i)
				{
					signBlockEntity.setTextOnRow(i, new LiteralText(Formatting.strip(strings[i])));
				}

				signBlockEntity.markDirty();
				serverWorld.updateListeners(blockPos, blockState, blockState, 3);
				
				info.cancel();
			}
		}
	}
}
