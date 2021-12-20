package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockNeonEquisetum;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;

public class StructureNeonEquisetum implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		MutableBlockPos blockPos = new MutableBlockPos();
		final float scale_factor = MAX_HEIGHT/128.0f;
		
		if (pos.getY() < (30 + random.nextInt(0, (int)(10*scale_factor))) || !BlocksHelper.isNetherrack(world.getBlockState(pos.above()))) return;
		
		final int RANDOM_BOUND = (int)(5 + 5*scale_factor);
		
		int h = BlocksHelper.downRay(world, pos, RANDOM_BOUND);
		if (h < 3)
			return;
		h = MHelper.randRange(3, h, random);

		BlockState bottom = NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(BlockNeonEquisetum.SHAPE, TripleShape.BOTTOM);
		BlockState middle = NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(BlockNeonEquisetum.SHAPE, TripleShape.MIDDLE);
		BlockState top = NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(BlockNeonEquisetum.SHAPE, TripleShape.TOP);

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