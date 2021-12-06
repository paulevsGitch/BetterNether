package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureHookMushroom implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		if (pos.getY() < (MAX_HEIGHT*0.7) || !BlocksHelper.isNetherrack(world.getBlockState(pos.above()))) return;
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.HOOK_MUSHROOM.defaultBlockState());
	}
}