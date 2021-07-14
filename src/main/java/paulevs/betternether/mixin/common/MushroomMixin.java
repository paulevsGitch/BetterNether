package paulevs.betternether.mixin.common;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;

@Mixin(MushroomBlock.class)
public abstract class MushroomMixin {
	StructureMedRedMushroom redStucture = new StructureMedRedMushroom();
	StructureMedBrownMushroom brownStructure = new StructureMedBrownMushroom();

	@Inject(method = "canSurvive", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void canStay(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (BlocksHelper.isNetherMycelium(world.getBlockState(pos.below())))
			info.setReturnValue(true);
	}

	@Inject(method = "performBonemeal", at = @At(value = "HEAD"), cancellable = true)
	private void growStructure(ServerLevel world, Random random, BlockPos pos, BlockState state, CallbackInfo info) {
		if (BlocksHelper.isNetherMycelium(world.getBlockState(pos.below()))) {
			if (state.getBlock() == Blocks.RED_MUSHROOM) {
				redStucture.grow(world, pos, random);
				info.cancel();
			}
			else if (state.getBlock() == Blocks.BROWN_MUSHROOM) {
				brownStructure.grow(world, pos, random);
				info.cancel();
			}
		}
	}
}
