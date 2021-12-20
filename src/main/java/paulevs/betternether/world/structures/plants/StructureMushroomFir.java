package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockMushroomFir;
import paulevs.betternether.blocks.BlockMushroomFir.MushroomFirShape;
import paulevs.betternether.blocks.BlockNetherMycelium;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureMushroomFir implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(5*scale_factor);
		if (world.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM) {
			int h = 3 + random.nextInt(RANDOM_BOUND);
			for (int y = 1; y < h; y++)
				if (!world.isEmptyBlock(pos.above(y))) {
					h = y;
					break;
				}
			if (h < 3)
				return;

			BlocksHelper.setWithUpdate(world, pos, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
					.defaultBlockState()
					.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.BOTTOM));
			int h2 = (h + 1) >> 1;
			h += pos.getY();
			h2 += pos.getY();
			context.POS.set(pos);
			for (int y = pos.getY() + 1; y < h2; y++) {
				context.POS.setY(y);
				BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
						.defaultBlockState()
						.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.MIDDLE));
			}
			for (int y = h2; y < h; y++) {
				context.POS.setY(y);
				BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
						.defaultBlockState()
						.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.TOP));
			}
			int h3 = (h2 + h) >> 1;
			for (int y = h2 - 1; y < h3; y++) {
				context.POS.setY(y);
				BlockPos branch;
				if (random.nextBoolean()) {
					branch = context.POS.north();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch,NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_S));
				}
				if (random.nextBoolean()) {
					branch = context.POS.south();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_N));
				}
				if (random.nextBoolean()) {
					branch = context.POS.east();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_W));
				}
				if (random.nextBoolean()) {
					branch = context.POS.west();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch,NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_E));
				}
			}
			for (int y = h3; y < h; y++) {
				context.POS.setY(y);
				BlockPos branch;
				if (random.nextBoolean()) {
					branch = context.POS.north();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_S));
				}
				if (random.nextBoolean()) {
					branch = context.POS.south();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_N));
				}
				if (random.nextBoolean()) {
					branch = context.POS.east();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_W));
				}
				if (random.nextBoolean()) {
					branch = context.POS.west();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_E));
				}
			}
			context.POS.setY(h);
			if (world.isEmptyBlock(context.POS))
				BlocksHelper.setWithUpdate(world, context.POS, NetherBlocks.MAT_MUSHROOM_FIR.geTrunk()
						.defaultBlockState()
						.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.END));

			BlocksHelper.cover(world,
					pos.below(),
					NetherBlocks.NETHER_MYCELIUM,
					NetherBlocks.NETHER_MYCELIUM.defaultBlockState().setValue(BlockNetherMycelium.IS_BLUE, true),
					5,
					random);
		}
	}
}