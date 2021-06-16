package paulevs.betternether.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.plants.StructureMedBrownMushroom;
import paulevs.betternether.structures.plants.StructureMedRedMushroom;

import java.util.Random;

@Mixin(NetherWartBlock.class)
public abstract class NetherWartMixin extends PlantBlock {
	protected NetherWartMixin(Settings settings) {
		super(settings);
	}

	StructureMedRedMushroom redStucture = new StructureMedRedMushroom();
	StructureMedBrownMushroom brownStructure = new StructureMedBrownMushroom();

	@Inject(method = "canPlantOnTop", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void canStay(BlockState floor, BlockView view, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (floor.getBlock() == BlocksRegistry.FARMLAND)
			info.setReturnValue(true);
	}

	@Inject(method = "randomTick", at = @At(value = "HEAD"), cancellable = true)
	private void tick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
		super.scheduledTick(state, world, pos, random);

		int i = (Integer) state.get(NetherWartBlock.AGE);
		if (i < 3) {
			int chance = BlocksHelper.isFertile(world.getBlockState(pos.down())) ? 3 : 10;
			if (random.nextInt(chance) == 0) {
				state = (BlockState) state.with(NetherWartBlock.AGE, i + 1);
				world.setBlockState(pos, state, 2);
			}
		}

		info.cancel();
	}
}
