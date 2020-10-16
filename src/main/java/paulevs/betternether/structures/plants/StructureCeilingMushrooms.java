package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureCeilingMushrooms implements IStructure {
	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (pos.getY() < 90) return;
		pos = pos.up();
		if (canPlace(world, pos)) BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.north())) BlocksHelper.setWithoutUpdate(world, pos.north(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.south())) BlocksHelper.setWithoutUpdate(world, pos.south(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.east())) BlocksHelper.setWithoutUpdate(world, pos.east(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
		if (canPlace(world, pos.west())) BlocksHelper.setWithoutUpdate(world, pos.west(), BlocksRegistry.CEILING_MUSHROOMS.getDefaultState());
	}

	private boolean canPlace(ServerWorldAccess world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos)) && world.getBlockState(pos.down()).getMaterial().isReplaceable();
	}
}