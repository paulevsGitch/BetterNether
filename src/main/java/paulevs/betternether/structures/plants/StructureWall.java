package paulevs.betternether.structures.plants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.IStructure;

public class StructureWall implements IStructure {
	private static final Direction[] DIRECTIONS = HorizontalFacingBlock.FACING.getValues().toArray(new Direction[] {});
	private static final Direction[] SHUFFLED = new Direction[DIRECTIONS.length];
	private final Block plantBlock;

	public StructureWall(Block plantBlock) {
		this.plantBlock = plantBlock;
	}

	@Override
	public void generate(ServerWorldAccess world, BlockPos pos, Random random) {
		if (world.isAir(pos)) {
			BlockState state = getPlacementState(world, pos, random);
			if (state != null)
				BlocksHelper.setWithoutUpdate(world, pos, state);
		}
	}

	private BlockState getPlacementState(ServerWorldAccess world, BlockPos pos, Random random) {
		BlockState blockState = plantBlock.getDefaultState();
		shuffle(random);
		for (int i = 0; i < 4; i++) {
			Direction direction = SHUFFLED[i];
			Direction direction2 = direction.getOpposite();
			blockState = blockState.with(HorizontalFacingBlock.FACING, direction2);
			if (blockState.canPlaceAt(world, pos)) {
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
