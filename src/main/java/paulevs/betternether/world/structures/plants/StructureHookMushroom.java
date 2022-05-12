package paulevs.betternether.world.structures.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class StructureHookMushroom implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, RandomSource random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		if (pos.getY() < (30 + random.nextInt((int)(10*scale_factor))) || !BlocksHelper.isNetherrack(world.getBlockState(pos.above()))) return;
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.HOOK_MUSHROOM.defaultBlockState());
	}
}