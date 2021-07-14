package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockMushroomFir;
import paulevs.betternether.blocks.BlockMushroomFir.MushroomFirShape;
import paulevs.betternether.blocks.BlockNetherMycelium;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureMushroomFir implements IStructure {
	MutableBlockPos npos = new MutableBlockPos();

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (world.getBlockState(pos.below()).getBlock() == BlocksRegistry.NETHER_MYCELIUM) {
			int h = 3 + random.nextInt(5);
			for (int y = 1; y < h; y++)
				if (!world.isEmptyBlock(pos.above(y))) {
					h = y;
					break;
				}
			if (h < 3)
				return;

			BlocksHelper.setWithUpdate(world, pos, BlocksRegistry.MUSHROOM_FIR
					.defaultBlockState()
					.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.BOTTOM));
			int h2 = (h + 1) >> 1;
			h += pos.getY();
			h2 += pos.getY();
			npos.set(pos);
			for (int y = pos.getY() + 1; y < h2; y++) {
				npos.setY(y);
				BlocksHelper.setWithUpdate(world, npos, BlocksRegistry.MUSHROOM_FIR
						.defaultBlockState()
						.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.MIDDLE));
			}
			for (int y = h2; y < h; y++) {
				npos.setY(y);
				BlocksHelper.setWithUpdate(world, npos, BlocksRegistry.MUSHROOM_FIR
						.defaultBlockState()
						.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.TOP));
			}
			int h3 = (h2 + h) >> 1;
			for (int y = h2 - 1; y < h3; y++) {
				npos.setY(y);
				BlockPos branch;
				if (random.nextBoolean()) {
					branch = npos.north();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_S));
				}
				if (random.nextBoolean()) {
					branch = npos.south();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_N));
				}
				if (random.nextBoolean()) {
					branch = npos.east();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_W));
				}
				if (random.nextBoolean()) {
					branch = npos.west();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_BIG_E));
				}
			}
			for (int y = h3; y < h; y++) {
				npos.setY(y);
				BlockPos branch;
				if (random.nextBoolean()) {
					branch = npos.north();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_S));
				}
				if (random.nextBoolean()) {
					branch = npos.south();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_N));
				}
				if (random.nextBoolean()) {
					branch = npos.east();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_W));
				}
				if (random.nextBoolean()) {
					branch = npos.west();
					if (world.isEmptyBlock(branch))
						BlocksHelper.setWithUpdate(world, branch, BlocksRegistry.MUSHROOM_FIR
								.defaultBlockState()
								.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.SIDE_SMALL_E));
				}
			}
			npos.setY(h);
			if (world.isEmptyBlock(npos))
				BlocksHelper.setWithUpdate(world, npos, BlocksRegistry.MUSHROOM_FIR
						.defaultBlockState()
						.setValue(BlockMushroomFir.SHAPE, MushroomFirShape.END));

			BlocksHelper.cover(world,
					pos.below(),
					BlocksRegistry.NETHER_MYCELIUM,
					BlocksRegistry.NETHER_MYCELIUM.defaultBlockState().setValue(BlockNetherMycelium.IS_BLUE, true),
					5,
					random);
		}
	}
}