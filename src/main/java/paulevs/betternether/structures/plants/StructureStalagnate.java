package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureStalagnate implements IStructure {
	public static final int MAX_LENGTH = 25; // 27
	public static final int MIN_LENGTH = 3; // 5

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		int length = BlocksHelper.upRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.above(length + 1)))) {
			BlockState bottom = BlocksRegistry.STALAGNATE.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = BlocksRegistry.STALAGNATE.defaultBlockState();
			BlockState top = BlocksRegistry.STALAGNATE.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.TOP);

			BlocksHelper.setWithUpdate(world, pos, bottom);
			BlocksHelper.setWithUpdate(world, pos.above(length), top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithUpdate(world, pos.above(y), middle);
		}
	}

	public void generateDown(ServerLevelAccessor world, BlockPos pos, Random random) {
		int length = BlocksHelper.downRay(world, pos, MAX_LENGTH);
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.below(length + 1)))) {
			BlockState bottom = BlocksRegistry.STALAGNATE.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = BlocksRegistry.STALAGNATE.defaultBlockState();
			BlockState top = BlocksRegistry.STALAGNATE.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.TOP);

			BlocksHelper.setWithUpdate(world, pos.below(length), bottom);
			BlocksHelper.setWithUpdate(world, pos, top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithUpdate(world, pos.below(y), middle);
		}
	}
}
