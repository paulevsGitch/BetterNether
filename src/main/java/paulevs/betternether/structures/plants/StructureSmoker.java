package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.BlockSmoker;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureSmoker implements IStructure {
	private MutableBlockPos npos = new MutableBlockPos();

	private boolean canPlaceAt(ServerLevelAccessor world, BlockPos pos) {
		return BlocksHelper.isNetherGround(world.getBlockState(pos.below()));
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		if (canPlaceAt(world, pos)) {
			BlockState top = NetherBlocks.SMOKER.defaultBlockState();
			BlockState middle = NetherBlocks.SMOKER.defaultBlockState().setValue(BlockSmoker.SHAPE, TripleShape.MIDDLE);
			BlockState bottom = NetherBlocks.SMOKER.defaultBlockState().setValue(BlockSmoker.SHAPE, TripleShape.BOTTOM);
			for (int i = 0; i < 8; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++) {
					npos.set(x, y - j, z);
					if (world.isEmptyBlock(npos) && canPlaceAt(world, npos)) {
						int h = random.nextInt(5);
						BlocksHelper.setWithoutUpdate(world, npos, bottom);
						for (int n = 1; n < h; n++) {
							BlockPos up = npos.above(n);
							if (world.isEmptyBlock(up.above()))
								BlocksHelper.setWithoutUpdate(world, up, middle);
							else {
								BlocksHelper.setWithoutUpdate(world, up, top);
								return;
							}
						}
						BlocksHelper.setWithoutUpdate(world, npos.above(h), top);
						break;
					}
				}
			}
		}
	}
}
