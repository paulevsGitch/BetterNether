package paulevs.betternether.structures.plants;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockBrownLargeMushroom;
import paulevs.betternether.blocks.BlockProperties.BrownMushroomShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureMedBrownMushroom implements IStructure {
	private static final MutableBlockPos POS = new MutableBlockPos();

	@Override
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random) {
		Block under;
		if (world.getBlockState(pos.below()).getBlock() == BlocksRegistry.NETHER_MYCELIUM) {
			for (int i = 0; i < 10; i++) {
				int x = pos.getX() + (int) (random.nextGaussian() * 2);
				int z = pos.getZ() + (int) (random.nextGaussian() * 2);
				if (((x + z + 1) & 1) == 0) {
					if (random.nextBoolean()) {
						x += random.nextBoolean() ? 1 : -1;
					}
					else {
						z += random.nextBoolean() ? 1 : -1;
					}
				}
				int y = pos.getY() + random.nextInt(6);
				for (int j = 0; j < 12; j++) {
					POS.set(x, y - j, z);
					under = world.getBlockState(POS.below()).getBlock();
					if (under == BlocksRegistry.NETHER_MYCELIUM) {
						grow(world, POS, random);
					}
				}
			}
		}
	}

	public void grow(ServerLevelAccessor world, BlockPos pos, Random random) {
		int size = 2 + random.nextInt(3);
		for (int y = 1; y <= size; y++)
			if (!world.isEmptyBlock(pos.above(y))) {
				if (y < 3)
					return;
				size = y - 1;
				break;
			}
		boolean hasAir = true;
		for (int x = -1; x < 2; x++)
			for (int z = -1; z < 2; z++)
				hasAir = hasAir && world.isEmptyBlock(pos.above(size).offset(x, 0, z));
		if (hasAir) {
			BlockState middle = BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.MIDDLE);
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.BOTTOM));
			for (int y = 1; y < size; y++)
				BlocksHelper.setWithoutUpdate(world, pos.above(y), middle);
			pos = pos.above(size);
			BlocksHelper.setWithUpdate(world, pos, BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.TOP));
			BlocksHelper.setWithoutUpdate(world, pos.north(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_N));
			BlocksHelper.setWithoutUpdate(world, pos.south(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_S));
			BlocksHelper.setWithoutUpdate(world, pos.east(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_E));
			BlocksHelper.setWithoutUpdate(world, pos.west(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.SIDE_W));
			BlocksHelper.setWithoutUpdate(world, pos.north().east(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_N));
			BlocksHelper.setWithoutUpdate(world, pos.north().west(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_W));
			BlocksHelper.setWithoutUpdate(world, pos.south().east(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_E));
			BlocksHelper.setWithoutUpdate(world, pos.south().west(), BlocksRegistry.BROWN_LARGE_MUSHROOM
					.defaultBlockState()
					.setValue(BlockBrownLargeMushroom.SHAPE, BrownMushroomShape.CORNER_S));
		}
	}
}
