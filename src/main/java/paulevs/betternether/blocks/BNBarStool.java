package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BNBarStool extends BNChair
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
	
	public BNBarStool(Block block)
	{
		super(block, 16);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
	}
}