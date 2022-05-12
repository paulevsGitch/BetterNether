package paulevs.betternether.world.structures.decorations;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class StructureGeyser implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, RandomSource random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (BlocksHelper.isNetherrack(world.getBlockState(pos.below())))
			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.GEYSER.defaultBlockState());
	}
}
