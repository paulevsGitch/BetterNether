package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.interfaces.SurvivesOnSouldGround;

import java.util.Random;

public class BlockSoulGrass extends BaseBlockNetherGrass implements SurvivesOnSouldGround {
	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canSurviveOnTop(state, world, pos);
	}

	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		if (random.nextInt(4) == 0) {
			world.addParticle(
					ParticleTypes.PORTAL,
					pos.getX() + random.nextDouble(),
					pos.getY() + random.nextDouble() * 2,
					pos.getZ() + random.nextDouble(),
					random.nextDouble() * 0.05,
					-1,
					random.nextDouble() * 0.05);
		}
	}
}
