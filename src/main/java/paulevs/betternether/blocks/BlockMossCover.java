package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class BlockMossCover extends BlockMold
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);
	
	public BlockMossCover()
	{
		super(MaterialColor.GREEN);
	}
	
	@Override
	public Block.OffsetType getOffsetType()
	{
		return Block.OffsetType.NONE;
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos, Direction.UP);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}
}
