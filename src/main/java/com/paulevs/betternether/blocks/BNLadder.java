package com.paulevs.betternether.blocks;

import com.paulevs.betternether.BlocksHelper;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BNLadder extends LadderBlock  {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
	protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);

	public BNLadder(Block block) {
		super(AbstractBlock.Properties.from(block).notSolid());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
		stateManager.add(WATERLOGGED);
	}

	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		switch (state.get(FACING)) {
			case NORTH:
				return NORTH_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			case EAST:
			default:
				return EAST_SHAPE;
		}
	}

	private boolean canPlaceOn(IBlockReader world, BlockPos pos, Direction side) {
		BlockState blockState = world.getBlockState(pos);
		return !blockState.canProvidePower() && blockState.isSolidSide(world, pos, side);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		Direction direction = (Direction) state.get(FACING);
		return this.canPlaceOn(world, pos.offset(direction.getOpposite()), direction);
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (facing.getOpposite() == state.get(FACING) && !state.isValidPosition(world, pos)) {
			return Blocks.AIR.getDefaultState();
		}
		else {
			if ((Boolean) state.get(WATERLOGGED)) {
				world .getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
			}

			return super.updatePostPlacement(state, facing, neighborState, world, pos, neighborPos);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		BlockState blockState2;
		if (!ctx.replacingClickedOnBlock()) {
			blockState2 = ctx.getWorld().getBlockState(ctx.getPos().offset(ctx.getFace().getOpposite()));
			if (blockState2.getBlock() == this && blockState2.get(FACING) == ctx.getFace()) {
				return null;
			}
		}

		blockState2 = this.getDefaultState();
		IWorldReader worldView = ctx.getWorld();
		BlockPos blockPos = ctx.getPos();
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getPos());
		Direction[] var6 = ctx.getNearestLookingDirections();
		int var7 = var6.length;

		for (int var8 = 0; var8 < var7; ++var8) {
			Direction direction = var6[var8];
			if (direction.getAxis().isHorizontal()) {
				blockState2 = (BlockState) blockState2.with(FACING, direction.getOpposite());
				if (blockState2.isValidPosition(worldView, blockPos)) {
					return (BlockState) blockState2.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
				}
			}
		}

		return null;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
}
