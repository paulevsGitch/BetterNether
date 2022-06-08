package org.betterx.betternether.mixin.common;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartMixin extends BushBlock {
    protected NetherWartMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "mayPlaceOn", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void canStay(BlockState floor, BlockGetter view, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (floor.getBlock() == NetherBlocks.FARMLAND)
            info.setReturnValue(true);
    }

    @Inject(method = "randomTick", at = @At(value = "HEAD"), cancellable = true)
    private void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo info) {
        super.tick(state, world, pos, random);

        int i = state.getValue(NetherWartBlock.AGE);
        if (i < 3) {
            int chance = BlocksHelper.isFertile(world.getBlockState(pos.below())) ? 3 : 10;
            if (random.nextInt(chance) == 0) {
                state = state.setValue(NetherWartBlock.AGE, i + 1);
                world.setBlock(pos, state, 2);
            }
        }

        info.cancel();
    }
}
