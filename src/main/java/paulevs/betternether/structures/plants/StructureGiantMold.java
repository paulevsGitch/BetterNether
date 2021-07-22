package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import paulevs.betternether.blocks.BlockRedLargeMushroom;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.IStructure;

public class StructureGiantMold implements IStructure {
	MutableBlockPos npos = new MutableBlockPos();

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		Block under;
		if (world.getBlockState(pos.below()).getBlock() == NetherBlocks.NETHER_MYCELIUM) {
			for (int i = 0; i < 10; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 16; j++) {
					npos.set(x, y - j, z);
					under = world.getBlockState(npos.below()).getBlock();
					if (under == NetherBlocks.NETHER_MYCELIUM) {
						grow(world, npos, random);
					}
				}
			}
		}
	}

	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		int size = 2 + random.nextInt(6);
		for (int y = 1; y <= size; y++)
			if (!world.isEmptyBlock(pos.above(y))) {
				if (y == 1)
					return;
				size = y - 1;
				break;
			}
		BlockState middle = NetherBlocks.GIANT_MOLD.defaultBlockState().setValue(BlockRedLargeMushroom.SHAPE, TripleShape.MIDDLE);
		for (int y = 1; y < size; y++)
			BlocksHelper.setWithoutUpdate(world, pos.above(y), middle);
		BlocksHelper.setWithoutUpdate(world, pos.above(size), NetherBlocks.GIANT_MOLD.defaultBlockState().setValue(BlockRedLargeMushroom.SHAPE, TripleShape.TOP));
		BlocksHelper.setWithUpdate(world, pos, NetherBlocks.GIANT_MOLD.defaultBlockState().setValue(BlockRedLargeMushroom.SHAPE, TripleShape.BOTTOM));
	}
}
