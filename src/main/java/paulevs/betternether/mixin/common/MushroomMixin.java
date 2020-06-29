package paulevs.betternether.mixin.common;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;

@Mixin(MushroomPlantBlock.class)
public abstract class MushroomMixin
{
	StructureMedRedMushroom redStucture = new StructureMedRedMushroom();
	StructureMedBrownMushroom brownStructure = new StructureMedBrownMushroom();
	
	@Inject(method = "canPlaceAt", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void canStay(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info)
	{
		if (BlocksHelper.isNetherMycelium(world.getBlockState(pos.down())))
			info.setReturnValue(true);
    }
	
	@Inject(method = "grow", at = @At(value = "HEAD"), cancellable = true)
	private void growStructure(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo info)
	{
		if (BlocksHelper.isNetherMycelium(world.getBlockState(pos.down())))
		{
			if (state.getBlock() == Blocks.RED_MUSHROOM)
			{
				redStucture.grow(world, pos, random);
				info.cancel();
			}
			else if (state.getBlock() == Blocks.BROWN_MUSHROOM)
			{
				brownStructure.grow(world, pos, random);
				info.cancel();
			}
		}
	}
}
