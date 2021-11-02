package paulevs.betternether.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import paulevs.betternether.advancements.BNCriterion;
import paulevs.betternether.registry.NetherBlocks;

import java.util.Iterator;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
	
	@Redirect(method="shouldSpreadLiquid", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
	boolean bn_shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState blockState){
		if (blockState.is(Blocks.OBSIDIAN)){
			final BlockState belowState = level.getBlockState(blockPos.below());
			if (belowState.is(Blocks.SOUL_SOIL) ||belowState.is(Blocks.SOUL_SAND)) {
				blockState = NetherBlocks.BLUE_OBSIDIAN.defaultBlockState();
				
				final int x = blockPos.getX();
				final int y = blockPos.getY();
				final int z = blockPos.getZ();
				Iterator nearbyPlayer = level.getEntitiesOfClass(ServerPlayer.class, (new AABB(x, y, z, x, y - 4, z)).inflate(10.0D, 5.0D, 10.0D)).iterator();
				
				while(nearbyPlayer.hasNext()) {
					final ServerPlayer serverPlayer = (ServerPlayer)nearbyPlayer.next();
					BNCriterion.BREW_BLUE.trigger(serverPlayer);
				}
				
			}
		}
		return level.setBlockAndUpdate(blockPos, blockState);
	}
}