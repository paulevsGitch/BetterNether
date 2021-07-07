package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureCeilingMushrooms implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (pos.getY() < 90) return;
		pos = pos.above();
		if (canPlace(world, pos)) BlocksHelper.setWithUpdate(world, pos, BlocksRegistry.CEILING_MUSHROOMS.defaultBlockState());
		if (canPlace(world, pos.north())) BlocksHelper.setWithUpdate(world, pos.north(), BlocksRegistry.CEILING_MUSHROOMS.defaultBlockState());
		if (canPlace(world, pos.south())) BlocksHelper.setWithUpdate(world, pos.south(), BlocksRegistry.CEILING_MUSHROOMS.defaultBlockState());
		if (canPlace(world, pos.east())) BlocksHelper.setWithUpdate(world, pos.east(), BlocksRegistry.CEILING_MUSHROOMS.defaultBlockState());
		if (canPlace(world, pos.west())) BlocksHelper.setWithUpdate(world, pos.west(), BlocksRegistry.CEILING_MUSHROOMS.defaultBlockState());
	}

	private boolean canPlace(ServerLevelAccessor world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos)) && world.getBlockState(pos.below()).getMaterial().isReplaceable();
	}
}