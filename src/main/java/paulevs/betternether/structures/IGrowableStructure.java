package paulevs.betternether.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;

public interface IGrowableStructure {
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random);
}
