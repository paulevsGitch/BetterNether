package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IGrowableStructure;
import paulevs.betternether.structures.IStructure;

public class StructureEye implements IStructure, IGrowableStructure {
	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		grow(world, pos, random);
	}
	
	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		int h = random.nextInt(19) + 5;
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