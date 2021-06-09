package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockWartSeed;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureWartBush implements IStructure {
	private static final Direction[] DIRS = new Direction[] { Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (world.isAir(pos)) {
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.getDefaultState());
			for (Direction dir : DIRS)
				setSeed(world, pos, dir);
		}
	}

	private void setSeed(ServerWorldAccess world, BlockPos pos, Direction dir) {
		BlockPos p = pos.offset(dir);
		if (world.isAir(p))
			BlocksHelper.setWithoutUpdate(world, p, BlocksRegistry.WART_SEED.getDefaultState().with(BlockWartSeed.FACING, dir));
	}
}