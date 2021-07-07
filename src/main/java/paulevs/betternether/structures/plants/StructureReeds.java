package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockNetherReed;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureReeds implements IStructure {
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (world.isEmptyBlock(pos) && BlocksRegistry.NETHER_REED.canSurvive(world.getBlockState(pos), world, pos)) {
			BlockState med = BlocksRegistry.NETHER_REED.defaultBlockState().setValue(BlockNetherReed.TOP, false);
			int h = random.nextInt(3);
			for (int i = 0; i < h; i++) {
				BlockPos posN = pos.above(i);
				BlockPos up = posN.above();
				if (world.isEmptyBlock(posN)) {
					if (world.isEmptyBlock(up))
						BlocksHelper.setWithUpdate(world, posN, med);
					else {
						BlocksHelper.setWithUpdate(world, posN, BlocksRegistry.NETHER_REED.defaultBlockState());
						return;
					}
				}
			}
			BlocksHelper.setWithUpdate(world, pos.above(h), BlocksRegistry.NETHER_REED.defaultBlockState());
		}
	}
}