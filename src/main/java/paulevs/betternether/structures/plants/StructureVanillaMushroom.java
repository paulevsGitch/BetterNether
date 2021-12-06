package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureVanillaMushroom implements IStructure {
	private MutableBlockPos npos = new MutableBlockPos();

	private boolean canPlaceAt(LevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM;
	}

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random, final int MAX_HEIGHT) {
		if (canPlaceAt(world, pos)) {
			BlockState state = random.nextBoolean() ? Blocks.RED_MUSHROOM.defaultBlockState() : Blocks.BROWN_MUSHROOM.defaultBlockState();
			for (int i = 0; i < 16; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 4);
				int z = pos.getZ() + (int) (random.nextGaussian() * 4);
				int y = pos.getY() + random.nextInt(8);
				for (int j = 0; j < 8; j++) {
					npos.set(x, y - j, z);
					if (world.isEmptyBlock(npos) && canPlaceAt(world, npos)) {
						BlocksHelper.setWithoutUpdate(world, npos, state);
					}
				}
			}
		}
	}
}
