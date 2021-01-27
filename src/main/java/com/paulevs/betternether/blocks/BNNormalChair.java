package com.paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import com.paulevs.betternether.BlocksHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BNNormalChair extends BNChair {
	private static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(3, 0, 3, 13, 16, 13);
	private static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(3, 0, 3, 13, 6, 13);
	private static final VoxelShape COLLIDER = Block.makeCuboidShape(3, 0, 3, 13, 10, 13);
	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public BNNormalChair(Block block) {
		super(block, 10);
		this.setDefaultState(getStateContainer().getBaseState().with(FACING, Direction.NORTH).with(TOP, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING, TOP);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return state.get(TOP) ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return state.get(TOP) ? VoxelShapes.empty() : COLLIDER;
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
		if (state.get(TOP))
			return true;
		BlockState up = world.getBlockState(pos.up());
		return up.isAir() || (up.getBlock() == this && up.get(TOP));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isRemote())
			BlocksHelper.setWithUpdate((ServerWorld) world, pos.up(), state.with(TOP, true));
	}

	@Override
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(TOP)) {
			return world.getBlockState(pos.down()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
		else {
			return world.getBlockState(pos.up()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (!state.get(TOP))
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return Collections.emptyList();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		if (state.get(TOP)) {
			pos = pos.down();
			state = world.getBlockState(pos);
		}
		return super.onBlockActivated(state, world, pos, player, hand, hit);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (player.isCreative() && state.get(TOP) && world.getBlockState(pos.down()).getBlock() == this) {
			world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
		}
		super.onBlockHarvested(world, pos, state, player);
	}
}
