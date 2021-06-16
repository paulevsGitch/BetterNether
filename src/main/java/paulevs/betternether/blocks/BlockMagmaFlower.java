package paulevs.betternether.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class BlockMagmaFlower extends BlockCommonPlant {
	private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 12, 15);

	public BlockMagmaFlower() {
		super(MapColor.TERRACOTTA_ORANGE);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return SHAPE;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock() == Blocks.MAGMA_BLOCK;
	}
}
