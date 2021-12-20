package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureTwistedVines implements IStructure {
	private boolean canPlaceAt(LevelAccessor world, BlockPos pos) {
		Block block = world.getBlockState(pos.below()).getBlock();
		return block == Blocks.WARPED_NYLIUM || block == Blocks.TWISTING_VINES;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(6*scale_factor);
		if (canPlaceAt(world, pos)) {
			for (int i = 0; i < 10; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(RANDOM_BOUND);
				for (int j = 0; j < RANDOM_BOUND; j++) {
					context.POS.set(x, y - j, z);
					if (world.isEmptyBlock(context.POS) && canPlaceAt(world, context.POS)) {
						int h = random.nextInt(20) + 1;
						int sy = context.POS.getY();
						for (int n = 0; n < h; n++) {
							context.POS.setY(sy + n);
							if (!world.isEmptyBlock(context.POS.above())) {
								BlocksHelper.setWithoutUpdate(world, context.POS, Blocks.TWISTING_VINES.defaultBlockState());
								break;
							}
							BlocksHelper.setWithoutUpdate(world, context.POS, Blocks.TWISTING_VINES_PLANT.defaultBlockState());
						}
						BlocksHelper.setWithoutUpdate(world, context.POS, Blocks.TWISTING_VINES.defaultBlockState());
						break;
					}
				}
			}
		}
	}
}
