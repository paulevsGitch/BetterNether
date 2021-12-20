package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureVine implements IStructure {
	private final Block block;

	public StructureVine(Block block) {
		this.block = block;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		int h = BlocksHelper.downRay(world, pos, 25);
		if (h < 2)
			return;
		h = random.nextInt(h) + 1;

		BlockState bottom = block.defaultBlockState().setValue(BlockProperties.BOTTOM, true);
		BlockState middle = block.defaultBlockState().setValue(BlockProperties.BOTTOM, false);

		context.POS.set(pos);
		for (int y = 0; y < h; y++) {
			context.POS.setY(pos.getY() - y);
			if (world.isEmptyBlock(context.POS.below()))
				BlocksHelper.setWithoutUpdate(world, context.POS, middle);
			else {
				BlocksHelper.setWithoutUpdate(world, context.POS, bottom);
				return;
			}
		}
		BlocksHelper.setWithoutUpdate(world, context.POS.below(), bottom);
	}
}