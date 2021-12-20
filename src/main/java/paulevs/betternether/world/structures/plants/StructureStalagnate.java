package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.BlockStalagnate;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureStalagnate implements IStructure {
	public static final int MAX_LENGTH = 25; // 27
	public static final int MIN_LENGTH = 3; // 5

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		int length = BlocksHelper.upRay(world, pos, MAX_LENGTH);
		Block main = NetherBlocks.MAT_STALAGNATE.getTrunk();
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.above(length + 1)))) {
			BlockState bottom = main.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = main.defaultBlockState();
			BlockState top = main.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.TOP);

			BlocksHelper.setWithUpdate(world, pos, bottom);
			BlocksHelper.setWithUpdate(world, pos.above(length), top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithUpdate(world, pos.above(y), middle);
		}
	}

	public void generateDown(ServerLevelAccessor world, BlockPos pos, Random random) {
		int length = BlocksHelper.downRay(world, pos, MAX_LENGTH);
		Block main = NetherBlocks.MAT_STALAGNATE.getTrunk();
		if (length > MIN_LENGTH && BlocksHelper.isNetherrack(world.getBlockState(pos.below(length + 1)))) {
			BlockState bottom = main.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.BOTTOM);
			BlockState middle = main.defaultBlockState();
			BlockState top = main.defaultBlockState().setValue(BlockStalagnate.SHAPE, TripleShape.TOP);

			BlocksHelper.setWithUpdate(world, pos.below(length), bottom);
			BlocksHelper.setWithUpdate(world, pos, top);
			for (int y = 1; y < length; y++)
				BlocksHelper.setWithUpdate(world, pos.below(y), middle);
		}
	}
}
