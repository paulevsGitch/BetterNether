package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;

public class StructureTwistedVines implements IStructure {
	private MutableBlockPos npos = new MutableBlockPos();

	private boolean canPlaceAt(LevelAccessor world, BlockPos pos) {
		Block block = world.getBlockState(pos.below()).getBlock();
		return block == Blocks.WARPED_NYLIUM || block == Blocks.TWISTING_VINES;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		if (canPlaceAt(world, pos)) {
			for (int i = 0; i < 10; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 6; j++) {
					npos.set(x, y - j, z);
					if (world.isEmptyBlock(npos) && canPlaceAt(world, npos)) {
						int h = random.nextInt(20) + 1;
						int sy = npos.getY();
						for (int n = 0; n < h; n++) {
							npos.setY(sy + n);
							if (!world.isEmptyBlock(npos.above())) {
								BlocksHelper.setWithoutUpdate(world, npos, Blocks.TWISTING_VINES.defaultBlockState());
								break;
							}
							BlocksHelper.setWithoutUpdate(world, npos, Blocks.TWISTING_VINES_PLANT.defaultBlockState());
						}
						BlocksHelper.setWithoutUpdate(world, npos, Blocks.TWISTING_VINES.defaultBlockState());
						break;
					}
				}
			}
		}
	}
}
