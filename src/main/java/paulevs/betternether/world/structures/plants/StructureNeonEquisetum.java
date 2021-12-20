package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockNeonEquisetum;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureNeonEquisetum implements IStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
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

		context.POS.set(pos);
		for (int y = 0; y < h - 2; y++) {
			context.POS.setY(pos.getY() - y);
			BlocksHelper.setWithUpdate(world, context.POS, top);
		}
		context.POS.setY(context.POS.getY() - 1);
		BlocksHelper.setWithUpdate(world, context.POS, middle);
		context.POS.setY(context.POS.getY() - 1);
		BlocksHelper.setWithUpdate(world, context.POS, bottom);
	}
}