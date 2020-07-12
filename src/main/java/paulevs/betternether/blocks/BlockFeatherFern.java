package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BlockFeatherFern extends BlockCommonPlant
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 14, 14);
	
	public BlockFeatherFern()
	{
		super(MaterialColor.LIGHT_BLUE);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		Vec3d vec3d = state.getModelOffset(view, pos);
		return SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public Block.OffsetType getOffsetType()
	{
		return Block.OffsetType.XZ;
	}
}
