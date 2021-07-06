package paulevs.betternether.structures.plants;

import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.BlockProperties.WillowBranchShape;
import paulevs.betternether.blocks.BlockWillowBranch;
import paulevs.betternether.blocks.BlockWillowLeaves;
import paulevs.betternether.blocks.BlockWillowTrunk;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

import java.util.Random;

public class StructureWillow implements IStructure {
	private static final Direction[] HOR = HorizontalFacingBlock.FACING.getValues().toArray(new Direction[] {});

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (!BlocksHelper.isNetherGround(world.getBlockState(pos.down())))
			return;

		int h2 = 5 + random.nextInt(3);

		int mh = BlocksHelper.upRay(world, pos.up(), h2);
		if (mh < 5)
			return;

		h2 = Math.min(h2, mh);

		BlocksHelper.setWithUpdate(world, pos, BlocksRegistry.WILLOW_TRUNK.getDefaultState().with(BlockWillowTrunk.SHAPE, TripleShape.BOTTOM));
		for (int h = 1; h < h2; h++)
			if (world.isAir(pos.up(h)))
				BlocksHelper.setWithUpdate(world, pos.up(h), BlocksRegistry.WILLOW_TRUNK.getDefaultState().with(BlockWillowTrunk.SHAPE, TripleShape.MIDDLE));
		if (world.isAir(pos.up(h2)))
			BlocksHelper.setWithUpdate(world, pos.up(h2), BlocksRegistry.WILLOW_TRUNK.getDefaultState().with(BlockWillowTrunk.SHAPE, TripleShape.TOP));

		for (int i = 0; i < 4; i++)
			branch(world, pos.up(h2).offset(HOR[i]), 3 + random.nextInt(2), random, HOR[i], pos.up(h2), 0);

		BlocksHelper.setWithUpdate(world, pos.up(h2 + 1), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.UP));
		for (int i = 0; i < 4; i++)
			BlocksHelper.setWithUpdate(world, pos.up(h2 + 1).offset(HOR[i]), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, HOR[i]));
	}

	private void branch(ServerWorldAccess world, BlockPos pos, int length, Random random, Direction direction, BlockPos center, int level) {
		if (level > 5)
			return;
		Mutable bpos = new Mutable().set(pos);
		BlocksHelper.setWithUpdate(world, bpos, BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, direction));
		vine(world, pos.down(), 1 + random.nextInt(1));
		Direction preDir = direction;
		int l2 = length * length;
		for (int i = 0; i < l2; i++) {
			Direction dir = random.nextInt(3) > 0 ? preDir : random.nextBoolean() ? preDir.rotateYClockwise() : preDir.rotateYCounterclockwise();// HOR[random.nextInt(4)];
			BlockPos p = bpos.offset(dir);
			if (world.isAir(p)) {
				bpos.set(p);
				if (bpos.getManhattanDistance(center) > length)
					break;
				BlocksHelper.setWithUpdate(world, bpos, BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, dir));

				if (random.nextBoolean()) {
					BlocksHelper.setWithUpdate(world, bpos.up(), BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.UP));
				}

				if (random.nextInt(3) == 0) {
					bpos.setY(bpos.getY() - 1);
					BlocksHelper.setWithUpdate(world, bpos, BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, Direction.DOWN));
				}

				if (random.nextBoolean())
					vine(world, bpos.down(), 1 + random.nextInt(4));

				if (random.nextBoolean()) {
					Direction right = dir.rotateYClockwise();
					BlockPos p2 = bpos.offset(right);
					if (world.isAir(p2))
						branch(world, p2, length, random, right, center, level + 1);
					right = right.getOpposite();
					p2 = bpos.offset(right);
					if (world.isAir(p2))
						branch(world, p2, length, random, right, center, level + 1);
				}

				Direction dir2 = HOR[random.nextInt(4)];
				BlockPos p2 = bpos.offset(dir2);
				if (world.isAir(p2))
					BlocksHelper.setWithUpdate(world, p2, BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, dir2));

				preDir = dir;
			}
		}

		if (random.nextBoolean()) {
			if (world.isAir(bpos))
				BlocksHelper.setWithUpdate(world, bpos, BlocksRegistry.WILLOW_LEAVES.getDefaultState().with(BlockWillowLeaves.FACING, preDir));
		}
	}

	private void vine(ServerWorldAccess world, BlockPos pos, int length) {
		if (!world.isAir(pos))
			return;

		for (int i = 0; i < length; i++) {
			BlockPos p = pos.down(i);
			if (world.isAir(p.down()))
				BlocksHelper.setWithUpdate(world, p, BlocksRegistry.WILLOW_BRANCH.getDefaultState().with(BlockWillowBranch.SHAPE, WillowBranchShape.MIDDLE));
			else {
				BlocksHelper.setWithUpdate(world, p, BlocksRegistry.WILLOW_BRANCH.getDefaultState().with(BlockWillowBranch.SHAPE, WillowBranchShape.END));
				return;
			}
		}
		BlocksHelper.setWithUpdate(world, pos.down(length), BlocksRegistry.WILLOW_BRANCH.getDefaultState().with(BlockWillowBranch.SHAPE, WillowBranchShape.END));
	}
}
