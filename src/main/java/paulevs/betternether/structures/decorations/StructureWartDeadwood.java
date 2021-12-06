package paulevs.betternether.structures.decorations;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.StructureWorld;

public class StructureWartDeadwood implements IStructure {
	private static final StructureWorld[] TREES = new StructureWorld[] {
			new StructureWorld("trees/wart_root_01", 0, StructureType.FLOOR),
			new StructureWorld("trees/wart_root_02", 0, StructureType.FLOOR),
			new StructureWorld("trees/wart_root_03", -2, StructureType.FLOOR),
			new StructureWorld("trees/wart_fallen_log", 0, StructureType.FLOOR)
	};

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		if (isGround(world.getBlockState(pos.below())) && isGround(world.getBlockState(pos.below(2)))) {
			StructureWorld tree = TREES[random.nextInt(TREES.length)];
			tree.generate(world, pos, random, MAX_HEIGHT);
		}
	}

	private boolean isGround(BlockState state) {
		return BlocksHelper.isNetherGround(state);
	}
}
