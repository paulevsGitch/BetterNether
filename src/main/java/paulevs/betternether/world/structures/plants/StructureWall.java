package paulevs.betternether.world.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.structures.IStructure;
import paulevs.betternether.world.structures.StructureGeneratorThreadContext;

public class StructureWall implements IStructure {
	private static final Direction[] DIRECTIONS = HorizontalDirectionalBlock.FACING.getPossibleValues().toArray(new Direction[] {});
	private static final Direction[] SHUFFLED = new Direction[DIRECTIONS.length];
	private final Block plantBlock;

	public StructureWall(Block plantBlock) {
		this.plantBlock = plantBlock;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT, StructureGeneratorThreadContext context) {
		if (world.isEmptyBlock(pos)) {
			BlockState state = getPlacementState(world, pos, random);
			if (state != null)
				BlocksHelper.setWithoutUpdate(world, pos, state);
		}
	}

	private BlockState getPlacementState(ServerLevelAccessor world, BlockPos pos, Random random) {
		BlockState blockState = plantBlock.defaultBlockState();
		shuffle(random);
		for (int i = 0; i < 4; i++) {
			Direction direction = SHUFFLED[i];
			Direction direction2 = direction.getOpposite();
			blockState = blockState.setValue(HorizontalDirectionalBlock.FACING, direction2);
			if (blockState.canSurvive(world, pos)) {
				return blockState;
			}
		}
		return null;
	}

	private void shuffle(Random random) {
		for (int i = 0; i < 4; i++)
			SHUFFLED[i] = DIRECTIONS[i];
		for (int i = 0; i < 4; i++) {
			int i2 = random.nextInt(4);
			Direction d = SHUFFLED[i2];
			SHUFFLED[i2] = SHUFFLED[i];
			SHUFFLED[i] = d;
		}
	}
}
