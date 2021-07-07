package paulevs.betternether.mixin.client;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.BetterNether;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {
	@Inject(method = "animateTick", at = @At(value = "HEAD"))
	private void displayTick(Level world, BlockPos blockPos, FluidState fluidState, Random random, CallbackInfo info) {
		if (BetterNether.hasLavafallParticles() && !fluidState.isSource()) {
			FluidState state = world.getFluidState(blockPos.below());
			if (state.isEmpty() || state.isSource()) {
				state = world.getFluidState(blockPos.above(3));
				if (!state.isEmpty() && !state.isSource()) {
					for (int i = 0; i < 10; i++) {
						spawnParticle(ParticleTypes.LARGE_SMOKE, world, random, blockPos);
						spawnParticle(ParticleTypes.SMOKE, world, random, blockPos);
						spawnParticle(ParticleTypes.LAVA, world, random, blockPos);
					}
				}
			}
		}
	}

	private void spawnParticle(ParticleOptions effect, Level world, Random random, BlockPos pos) {
		double angle = random.nextDouble() * Math.PI * 2;
		world.addParticle(ParticleTypes.LARGE_SMOKE,
				pos.getX() + random.nextDouble(),
				pos.getY() + random.nextDouble(),
				pos.getZ() + random.nextDouble(),
				Math.sin(angle) * 0.1, 0.0D, Math.cos(angle) * 0.1);
	}
}
