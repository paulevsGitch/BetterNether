package paulevs.betternether.mixin.client;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.BetterNether;

@Environment(EnvType.CLIENT)
@Mixin(LavaFluid.class)
public class LavaFluidMixin
{
	@Environment(EnvType.CLIENT)
	@Inject(method = "randomDisplayTick", at = @At(value = "HEAD"))
	private void displayTick(World world, BlockPos blockPos, FluidState fluidState, Random random, CallbackInfo info)
	{
		if (BetterNether.hasLavafallParticles() && !fluidState.isStill())
		{
			FluidState state = world.getFluidState(blockPos.down());
			if (state.isEmpty() || state.isStill())
			{
				state = world.getFluidState(blockPos.up(3));
				if (!state.isEmpty() && !state.isStill())
				{
					for (int i = 0; i < 10; i++)
					{
						spawnParticle(ParticleTypes.LARGE_SMOKE, world, random, blockPos);
						spawnParticle(ParticleTypes.SMOKE, world, random, blockPos);
						spawnParticle(ParticleTypes.LAVA, world, random, blockPos);
					}
				}
			}
		}
	}
	
	private void spawnParticle(ParticleEffect effect, World world, Random random, BlockPos pos)
	{
		double angle = random.nextDouble() * Math.PI * 2;
		world.addParticle(ParticleTypes.LARGE_SMOKE,
				pos.getX() + random.nextDouble(),
				pos.getY() + random.nextDouble(),
				pos.getZ() + random.nextDouble(),
				Math.sin(angle) * 0.1, 0.0D, Math.cos(angle) * 0.1);
	}
}
