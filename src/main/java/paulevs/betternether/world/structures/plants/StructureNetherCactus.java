package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherCactus;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureNetherCactus implements IStructure {
	private boolean canPlaceAt(LevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos.below()).getBlock() == Blocks.GRAVEL;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(8*scale_factor);
		
		if (canPlaceAt(world, pos)) {
			BlockState top = NetherBlocks.NETHER_CACTUS.defaultBlockState();
			BlockState bottom = NetherBlocks.NETHER_CACTUS.defaultBlockState().setValue(BlockNetherCactus.TOP, false);
			for (int i = 0; i < 16; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 4);
				int z = pos.getZ() + (int) (random.nextGaussian() * 4);
				if (((x + z + pos.getY()) & 1) == 0) {
					if (random.nextBoolean()) {
						x += random.nextBoolean() ? 1 : -1;
					}
					else {
						z += random.nextBoolean() ? 1 : -1;
					}
				}
				int y = pos.getY() + random.nextInt(RANDOM_BOUND);
				for (int j = 0; j < RANDOM_BOUND; j++) {
					context.POS.set(x, y - j, z);
					if (world.isEmptyBlock(context.POS) && canPlaceAt(world, context.POS)) {
						int h = random.nextInt(3);
						for (int n = 0; n < h; n++)
							BlocksHelper.setWithUpdate(world, context.POS.above(n), bottom);
						BlocksHelper.setWithUpdate(world, context.POS.above(h), top);
						break;
					}
				}
			}
		}
	}
}