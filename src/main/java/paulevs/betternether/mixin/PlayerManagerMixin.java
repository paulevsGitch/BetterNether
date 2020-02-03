package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.dimension.DimensionType;
import paulevs.betternether.IDimensionable;
import paulevs.betternether.registers.BlocksRegister;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin
{
	@Inject(method = "respawnPlayer", at = @At("RETURN"))
	private void dimRespawn(ServerPlayerEntity player, DimensionType dimension, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> info)
	{
		if (player.isSpawnForced())
		{
			ServerPlayerEntity newPlayer = info.getReturnValue();
			IDimensionable dimensionable = (IDimensionable) newPlayer;
			if (dimensionable.usedStatue())
			{
				DimensionType dim = dimensionable.getSpawnDimension();
				if (dim != null)
				{
					ServerWorld world = player.getServer().getWorld(dim);
					BlockPos pos = player.getSpawnPosition();
					if (nearStatue(world, pos))
					{
						if (dim != newPlayer.dimension)
							newPlayer.changeDimension(dim);
						newPlayer.teleport(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					}
					else
					{
						dimensionable.setUsedStatue(false);
						pos = newPlayer.world.getSpawnPos();
						newPlayer.setPlayerSpawn(pos, false, false);
						newPlayer.teleport(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
					}
				}
			}
		}
	}
	
	private boolean nearStatue(ServerWorld world, BlockPos pos)
	{
		for (Direction dir: HorizontalFacingBlock.FACING.getValues())
		{
			if (world.getBlockState(pos.offset(dir)).getBlock() == BlocksRegister.PIG_STATUE_RESPAWNER)
				return true;
		}
		return false;
	}
}