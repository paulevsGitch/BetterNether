package paulevs.betternether.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.blocks.BlockStatueRespawner;

import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

	@Inject(method = "findRespawnPositionAndUseSpawnBlock", at = @At(value = "HEAD"), cancellable = true)
	private static void statueRespawn(ServerLevel world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3>> info) {
		BlockState blockState = world.getBlockState(pos);
		Block block = blockState.getBlock();
		if (block instanceof BlockStatueRespawner) {
			info.setReturnValue(findRespawnPosition(world, pos, blockState));
			info.cancel();
		}
	}

	private static Optional<Vec3> findRespawnPosition(ServerLevel world, BlockPos pos, BlockState state) {
		if (state.getValue(BlockStatueRespawner.TOP))
			pos = pos.below();
		pos = pos.relative(state.getValue(BlockStatueRespawner.FACING));
		BlockState state2 = world.getBlockState(pos);
		if (!state2.getMaterial().blocksMotion() && state2.getCollisionShape(world, pos).isEmpty())
			return Optional.of(Vec3.atLowerCornerOf(pos).add(0.5, 0, 0.5));
		else
			return Optional.empty();
	}
}