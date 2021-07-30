package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockLumabusVine;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureGoldenLumabusVine implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		int h = random.nextInt(19) + 5;
		int h2 = BlocksHelper.downRay(world, pos, h);
		h2 -= 2;

		if (h2 < 3)
			return;

		BlockState vineState = NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState().setValue(BlockLumabusVine.SHAPE, TripleShape.MIDDLE);

		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState());

		for (int y = 1; y < h2; y++)
			BlocksHelper.setWithUpdate(world, pos.below(y), vineState);

		BlocksHelper.setWithUpdate(world, pos.below(h2), NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState().setValue(BlockLumabusVine.SHAPE, TripleShape.BOTTOM));
	}
}