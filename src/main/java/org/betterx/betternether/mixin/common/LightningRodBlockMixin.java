package org.betterx.betternether.mixin.common;

import org.betterx.betternether.blocks.BNObsidian;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningRodBlock.class)
public class LightningRodBlockMixin {
    private final static Direction[] bn_update_directions = {
            Direction.WEST,
            Direction.EAST,
            Direction.DOWN,
            Direction.UP,
            Direction.NORTH,
            Direction.SOUTH
    };

    @Inject(method = "onLightningStrike", at = @At("TAIL"))
    void bn_onLightningStrike(BlockState blockState, Level level, BlockPos blockPos, CallbackInfo ci) {
        MutableBlockPos mutableBlockPos = new MutableBlockPos();
        for (Direction dir : bn_update_directions) {
            mutableBlockPos.setWithOffset(blockPos, dir);
            BlockState state = level.getBlockState(mutableBlockPos);
            if (state.is(Blocks.OBSIDIAN)) {
                BNObsidian.onLightningUpdate(level, mutableBlockPos, Blocks.CRYING_OBSIDIAN);
            } else if (state.is(Blocks.CRYING_OBSIDIAN)) {
                BNObsidian.onLightningUpdate(level, mutableBlockPos, NetherBlocks.WEEPING_OBSIDIAN);
            }
        }
    }
}
