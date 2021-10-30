package paulevs.betternether.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import paulevs.betternether.registry.NetherBlocks;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
	
	@Redirect(method="shouldSpreadLiquid", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
	boolean bn_shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState blockState){
		if (blockState.is(Blocks.OBSIDIAN)){
			final BlockState belowState = level.getBlockState(blockPos.below());
			if (belowState.is(Blocks.SOUL_SOIL) ||belowState.is(Blocks.SOUL_SAND)) {
				blockState = NetherBlocks.BLUE_OBSIDIAN.defaultBlockState();
			}
		}
		return level.setBlockAndUpdate(blockPos, blockState);
	}
}