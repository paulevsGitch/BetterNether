package org.betterx.betternether.mixin.common;

import org.betterx.betternether.advancements.BNCriterion;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import com.google.common.collect.UnmodifiableIterator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
    // A: Original Redirect Code
    // *******************************************
//	@Redirect(method="shouldSpreadLiquid", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
//	boolean bn_shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState blockState){
//		if (blockState.is(Blocks.OBSIDIAN)){
//			final BlockState belowState = level.getBlockState(blockPos.below());
//			if (belowState.is(Blocks.SOUL_SOIL) ||belowState.is(Blocks.SOUL_SAND)) {
//				blockState = NetherBlocks.BLUE_OBSIDIAN.defaultBlockState();
//
//				final int x = blockPos.getX();
//				final int y = blockPos.getY();
//				final int z = blockPos.getZ();
//				Iterator nearbyPlayer = level.getEntitiesOfClass(ServerPlayer.class, (new AABB(x, y, z, x, y - 4, z)).inflate(10.0D, 5.0D, 10.0D)).iterator();
//
//				while(nearbyPlayer.hasNext()) {
//					final ServerPlayer serverPlayer = (ServerPlayer)nearbyPlayer.next();
//					BNCriterion.BREW_BLUE.trigger(serverPlayer);
//				}
//
//			}
//		}
//		return level.setBlockAndUpdate(blockPos, blockState);
//	}

    // B: Inject with local capture and replicated code for fizz
    // *******************************************
    @Shadow
    protected abstract void fizz(LevelAccessor levelAccessor, BlockPos blockPos);

    @Inject(method = "shouldSpreadLiquid", locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0))
    void bn_shouldSpreadLiquid(
            Level level,
            BlockPos blockPos,
            BlockState arg2,
            CallbackInfoReturnable<Boolean> cir,
            boolean bl,
            UnmodifiableIterator var5,
            Direction direction,
            BlockPos blockPos2,
            Block block
    ) {
        if (block == Blocks.OBSIDIAN) {
            final BlockState belowState = level.getBlockState(blockPos.below());
            if (belowState.is(Blocks.SOUL_SOIL) || belowState.is(Blocks.SOUL_SAND)) {
                level.setBlockAndUpdate(blockPos, NetherBlocks.BLUE_OBSIDIAN.defaultBlockState());
                this.fizz(level, blockPos);

                final int x = blockPos.getX();
                final int y = blockPos.getY();
                final int z = blockPos.getZ();
                Iterator nearbyPlayer = level.getEntitiesOfClass(
                                                     ServerPlayer.class,
                                                     (new AABB(x, y, z, x, y - 4, z)).inflate(
                                                             10.0D,
                                                             5.0D,
                                                             10.0D
                                                     )
                                             )
                                             .iterator();

                while (nearbyPlayer.hasNext()) {
                    final ServerPlayer serverPlayer = (ServerPlayer) nearbyPlayer.next();
                    BNCriterion.BREW_BLUE.trigger(serverPlayer);
                }

                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    // C: Inject after original setState call (will change state twice)
    // *******************************************
//	@Inject(method="shouldSpreadLiquid", locals=LocalCapture.CAPTURE_FAILSOFT, at=@At(value="INVOKE", target="Lnet/minecraft/world/level/block/LiquidBlock;fizz(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V"))
//	void bn_shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState arg2, CallbackInfoReturnable<Boolean> cir, Block block) {
//		if (block == Blocks.OBSIDIAN){
//			final BlockState belowState = level.getBlockState(blockPos.below());
//			if (belowState.is(Blocks.SOUL_SOIL) ||belowState.is(Blocks.SOUL_SAND)) {
//				level.setBlockAndUpdate(blockPos, NetherBlocks.BLUE_OBSIDIAN.defaultBlockState());
//
//				final int x = blockPos.getX();
//				final int y = blockPos.getY();
//				final int z = blockPos.getZ();
//				Iterator nearbyPlayer = level.getEntitiesOfClass(ServerPlayer.class, (new AABB(x, y, z, x, y - 4, z)).inflate(10.0D, 5.0D, 10.0D)).iterator();
//
//				while(nearbyPlayer.hasNext()) {
//					final ServerPlayer serverPlayer = (ServerPlayer)nearbyPlayer.next();
//					BNCriterion.BREW_BLUE.trigger(serverPlayer);
//				}
//			}
//		}
//	}


    // D: Changing the call Arguments and capturing the current level.
    // Might have issues if the game concurently accesses mutiple levels. This is not Thread-safe
    // *******************************************

//	private Level lastLevel;
//	@Inject(method="shouldSpreadLiquid", at=@At(value="HEAD"))
//	public void bn_shouldSpreadLiquidPrepare(Level level, BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<Boolean> cir){
//		lastLevel = level;
//	}
//
//	@ModifyArg(method="shouldSpreadLiquid", at=@At(value="INVOKE", target="Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z" ))
//	public BlockState bn_shouldSpreadLiquid(BlockPos blockPos, BlockState blockState){
//		final Level level = lastLevel;
//		if (level != null && blockState.is(Blocks.OBSIDIAN)){
//			final BlockState belowState = level.getBlockState(blockPos.below());
//			if (belowState.is(Blocks.SOUL_SOIL) ||belowState.is(Blocks.SOUL_SAND)) {
//				blockState = NetherBlocks.BLUE_OBSIDIAN.defaultBlockState();
//
//				final int x = blockPos.getX();
//				final int y = blockPos.getY();
//				final int z = blockPos.getZ();
//				Iterator nearbyPlayer = level.getEntitiesOfClass(ServerPlayer.class, (new AABB(x, y, z, x, y - 4, z)).inflate(10.0D, 5.0D, 10.0D)).iterator();
//
//				while(nearbyPlayer.hasNext()) {
//					final ServerPlayer serverPlayer = (ServerPlayer)nearbyPlayer.next();
//					BNCriterion.BREW_BLUE.trigger(serverPlayer);
//				}
//
//			}
//		}
//		return blockState;
//	}
}