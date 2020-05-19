package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockSmallLantern extends BlockBaseNotFull
{
	private static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(5, 0, 8, 11, 16, 16);
	private static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(5, 0, 0, 11, 16, 8);
	private static final VoxelShape SHAPE_WEST = Block.createCuboidShape(8, 0, 5, 16, 16, 11);
	private static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 0, 5, 8, 16, 11);
	private static final VoxelShape SHAPE_UP = Block.createCuboidShape(5, 0, 5, 11, 9, 11);
	private static final VoxelShape SHAPE_DOWN = Block.createCuboidShape(5, 3, 5, 11, 16, 11);
	
	public static final DirectionProperty FACING = Properties.FACING;
	
	public BlockSmallLantern()
	{
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_LANTERN).nonOpaque());
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.DOWN));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		switch (state.get(FACING))
		{
		case NORTH:
			return SHAPE_NORTH;
		case SOUTH:
			return SHAPE_SOUTH;
		case EAST:
			return SHAPE_EAST;
		case WEST:
			return SHAPE_WEST;
		case UP:
			return SHAPE_UP;
		case DOWN:
		default:
			return SHAPE_DOWN;
		}
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		Direction direction = (Direction) state.get(FACING).getOpposite();
		return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror)
	{
		return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		if (canPlaceAt(state, world, pos))
			return state;
		else
			return Blocks.AIR.getDefaultState();
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		BlockState blockState = this.getDefaultState();
		WorldView worldView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		Direction[] directions = ctx.getPlacementDirections();
		for(int i = 0; i < directions.length; ++i) 
		{
			Direction direction = directions[i];
			Direction direction2 = direction.getOpposite();
			blockState = blockState.with(FACING, direction2);
			if (blockState.canPlaceAt(worldView, blockPos))
			{
				return blockState;
			}
		}
		return null;
	}
}
