package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBiomes;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.features.NetherChunkPopulatorFeature;
import paulevs.betternether.world.structures.IGrowableStructure;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureEye implements IStructure, IGrowableStructure {
	@Override
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		generate(world, pos, random, 128, NetherChunkPopulatorFeature.generatorForThread().context);
	}
	
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		final float scale_factor = MAX_HEIGHT/128.0f;
		final int RANDOM_BOUND = (int)(NetherBiomes.useLegacyGeneration?19:21*scale_factor);
		int h = random.nextInt(RANDOM_BOUND) + 5;
		int h2 = BlocksHelper.downRay(world, pos, h);

		if (h2 < 5)
			return;

		h2 -= 1;

		BlockState vineState = NetherBlocks.EYE_VINE.defaultBlockState();
		BlockState eyeState = random.nextBoolean() ? NetherBlocks.EYEBALL.defaultBlockState() : NetherBlocks.EYEBALL_SMALL.defaultBlockState();

		for (int y = 0; y < h2; y++)
			BlocksHelper.setWithUpdate(world, pos.below(y), vineState);

		BlocksHelper.setWithUpdate(world, pos.below(h2), eyeState);
	}
}