package paulevs.betternether.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import paulevs.betternether.blocks.BlockStatueRespawner;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
	@Inject(method = "findRespawnPosition", at = @At(value = "HEAD"), cancellable = true)
	private static void statueRespawn(ServerWorld world, BlockPos pos, boolean bl,  CallbackInfoReturnable<Optional<Vec3d>> info)
	{
		BlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		if (block instanceof BlockStatueRespawner)
		{
			info.setReturnValue(findRespawnPosition(world, pos, blockState));
			info.cancel();
		}
	}

	private static Optional<Vec3d> findRespawnPosition(ServerWorld world, BlockPos pos, BlockState state)
	{
		if (state.get(BlockStatueRespawner.TOP))
			pos = pos.down();
		pos = pos.offset(state.get(BlockStatueRespawner.FACING));
		
		if (world.getBlockState(pos).getCollisionShape(world, pos).isEmpty())
			return Optional.of(Vec3d.method_24955(pos));
		else
			return Optional.empty();
	}
}