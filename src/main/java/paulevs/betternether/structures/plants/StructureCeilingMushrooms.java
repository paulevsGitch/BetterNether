package paulevs.betternether.structures.plants;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

import java.util.Random;

public class StructureCeilingMushrooms implements IStructure {
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (pos.getY() < 90) return;
		pos = pos.up();
		if (canPlace(world, pos)) BlocksHelper.setWithUpdate(world, pos, BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.north())) BlocksHelper.setWithUpdate(world, pos.north(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.south())) BlocksHelper.setWithUpdate(world, pos.south(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.east())) BlocksHelper.setWithUpdate(world, pos.east(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.west())) BlocksHelper.setWithUpdate(world, pos.west(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
	}

	private boolean canPlace(ServerWorldAccess world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos)) && world.getBlockState(pos.down()).getMaterial().isReplaceable();
	}
}