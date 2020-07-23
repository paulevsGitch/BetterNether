package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
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

public class BlockBoneMushroom extends BlockBaseNotFull
{
	private static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(1, 1, 8, 15, 15, 16);
	private static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(1, 1, 0, 15, 15, 8);
	private static final VoxelShape SHAPE_WEST = Block.createCuboidShape(8, 1, 1, 16, 15, 15);
	private static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 1, 1, 8, 15, 15);
	private static final VoxelShape SHAPE_UP = Block.createCuboidShape(1, 0, 1, 15, 12, 15);
	public static final DirectionProperty FACING = Properties.FACING;
	public static final IntProperty AGE = IntProperty.of("age", 0, 2);

	public BlockBoneMushroom()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.LIME)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(AGE, 0).with(FACING, Direction.UP));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
		stateManager.add(FACING, AGE);
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
		default:
			return SHAPE_UP;
		}
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		Direction direction = (Direction) state.get(FACING);
		if (direction == Direction.DOWN)
			return false;
		BlockPos blockPos = pos.offset(direction.getOpposite());
		BlockState blockState = world.getBlockState(blockPos);
		return BlocksHelper.isBone(blockState);
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
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		super.scheduledTick(state, world, pos, random);
		int age = state.get(AGE);
		if (age < 2 && random.nextInt(32) == 0)
		{
			BlocksHelper.setWithoutUpdate(world, pos, state.with(AGE, age + 1));
		}
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
			if (direction != Direction.UP)
			{
				Direction direction2 = direction.getOpposite();
				blockState = blockState.with(FACING, direction2);
				if (blockState.canPlaceAt(worldView, blockPos))
				{
					return blockState;
				}
			}
		}
		return null;
	}
}
