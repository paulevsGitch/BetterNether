package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BlockBlackApple extends BlockCommonPlant
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	
	public BlockBlackApple()
	{
		super(MaterialColor.ORANGE_TERRACOTTA);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}
}
