package org.betterx.betternether.mixin.common;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.features.TreeFeatures;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MushroomBlock.class)
public abstract class MushroomMixin {

    @Inject(method = "canSurvive", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void canStay(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (BlocksHelper.isNetherMycelium(world.getBlockState(pos.below())))
            info.setReturnValue(true);
    }

    @Inject(method = "performBonemeal", at = @At(value = "HEAD"), cancellable = true)
    private void growStructure(
            ServerLevel world,
            RandomSource random,
            BlockPos pos,
            BlockState state,
            CallbackInfo info
    ) {
        if (BlocksHelper.isNetherMycelium(world.getBlockState(pos.below()))) {
            if (state.getBlock() == Blocks.RED_MUSHROOM) {
                TreeFeatures.BIG_RED_MUSHROOM_CLUSTER.place(world, pos, random);
                info.cancel();
            } else if (state.getBlock() == Blocks.BROWN_MUSHROOM) {
                TreeFeatures.BIG_BROWN_MUSHROOM.place(world, pos, random);
                info.cancel();
            }
        }
    }
}
