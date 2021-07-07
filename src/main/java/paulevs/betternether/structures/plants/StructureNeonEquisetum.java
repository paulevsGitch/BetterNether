package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockNeonEquisetum;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureNeonEquisetum implements IStructure {
	private MutableBlockPos blockPos = new MutableBlockPos();

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		if (pos.getY() < 90 || !BlocksHelper.isNetherrack(world.getBlockState(pos.above()))) return;

		int h = BlocksHelper.downRay(world, pos, 10);
		if (h < 3)
			return;
		h = MHelper.randRange(3, h, random);

		BlockState bottom = BlocksRegistry.NEON_EQUISETUM.defaultBlockState().setValue(BlockNeonEquisetum.SHAPE, TripleShape.BOTTOM);
		BlockState middle = BlocksRegistry.NEON_EQUISETUM.defaultBlockState().setValue(BlockNeonEquisetum.SHAPE, TripleShape.MIDDLE);
		BlockState top = BlocksRegistry.NEON_EQUISETUM.defaultBlockState().setValue(BlockNeonEquisetum.SHAPE, TripleShape.TOP);

		blockPos.set(pos);
		for (int y = 0; y < h - 2; y++) {
			blockPos.setY(pos.getY() - y);
			BlocksHelper.setWithUpdate(world, blockPos, top);
		}
		blockPos.setY(blockPos.getY() - 1);
		BlocksHelper.setWithUpdate(world, blockPos, middle);
		blockPos.setY(blockPos.getY() - 1);
		BlocksHelper.setWithUpdate(world, blockPos, bottom);
	}
}