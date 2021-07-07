package paulevs.betternether.structures;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;

public interface IStructure {
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random);
}
