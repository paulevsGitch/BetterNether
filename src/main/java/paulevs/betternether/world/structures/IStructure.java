package paulevs.betternether.world.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;
import net.minecraft.util.RandomSource;

public interface IStructure {
	public void generate(ServerLevelAccessor world, BlockPos pos, RandomSource random, final int MAX_HEIGHT, StructureGeneratorThreadContext context);
}
