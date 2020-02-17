package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;
import paulevs.betternether.blocks.materials.Materials;

public class BlockAshVineStem extends BlockBaseNotFull
{
	public static final DirectionProperty FACING = Properties.FACING;
	public static final BooleanProperty UP = BooleanProperty.of("up");
	public static final BooleanProperty DOWN = BooleanProperty.of("down");
	public static final BooleanProperty NORTH = BooleanProperty.of("north");
	public static final BooleanProperty SOUTH = BooleanProperty.of("south");
	public static final BooleanProperty EAST = BooleanProperty.of("east");
	public static final BooleanProperty WEST = BooleanProperty.of("west");
	
	private static final VoxelShape SHAPE = Block.createCuboidShape(3, 3, 3, 13, 13, 13);
	
	public BlockAshVineStem()
	{
		super(Materials.makeWood(MaterialColor.BROWN).nonOpaque().build());
		this.setDefaultState(getStateManager().getDefaultState()
				.with(FACING, Direction.DOWN)
				.with(UP, false)
				.with(DOWN, false)
				.with(NORTH, false)
				.with(SOUTH, false)
				.with(EAST, false)
				.with(WEST, false));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING, UP, DOWN, NORTH, SOUTH, EAST, WEST);
	}

	@Override
	public int getOpacity(BlockState state, BlockView view, BlockPos pos)
	{
		return 1;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		boolean up = connectTo(world, pos, Direction.UP);
		boolean down = connectTo(world, pos, Direction.DOWN);
		boolean north = connectTo(world, pos, Direction.NORTH);
		boolean south = connectTo(world, pos, Direction.SOUTH);
		boolean east = connectTo(world, pos, Direction.EAST);
		boolean west = connectTo(world, pos, Direction.WEST);
		return state
				.with(UP, up)
				.with(DOWN, down)
				.with(NORTH, north)
				.with(SOUTH, south)
				.with(EAST, east)
				.with(WEST, west);
	}
	
	private boolean connectTo(IWorld world, BlockPos pos, Direction dir)
	{
		BlockState state = world.getBlockState(pos.offset(dir));
		return state.getBlock() == this || state.isSideSolidFullSquare(world, pos, dir.getOpposite());
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