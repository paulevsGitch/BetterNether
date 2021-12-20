package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockLumabusVine;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.structures.IGrowableStructure;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureGoldenLumabusVine implements IStructure, IGrowableStructure {
	@Override
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		generate(world, pos, random, 128, NetherChunkPopulatorFeature.generatorForThread().context);
	}
	
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(19*scale_factor);
		
		int h = random.nextInt(RANDOM_BOUND) + 5;
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