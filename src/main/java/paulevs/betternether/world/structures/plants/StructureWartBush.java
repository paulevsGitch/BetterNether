package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockWartSeed;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureWartBush implements IStructure {
	private static final Direction[] DIRS = new Direction[] { Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST };

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (world.isEmptyBlock(pos)) {
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.NETHER_WART_BLOCK.defaultBlockState());
			for (Direction dir : DIRS)
				setSeed(world, pos, dir);
		}
	}

	private void setSeed(ServerLevelAccessor world, BlockPos pos, Direction dir) {
		BlockPos p = pos.relative(dir);
		if (world.isEmptyBlock(p))
			BlocksHelper.setWithoutUpdate(world, p, NetherBlocks.MAT_WART.getSeed().defaultBlockState().setValue(BlockWartSeed.FACING, dir));
	}
}