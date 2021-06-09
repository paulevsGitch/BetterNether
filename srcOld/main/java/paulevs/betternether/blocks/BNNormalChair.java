package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;

public class BNNormalChair extends BNChair {
	private static final VoxelShape SHAPE_BOTTOM = Block.createCuboidShape(3, 0, 3, 13, 16, 13);
	private static final VoxelShape SHAPE_TOP = Block.createCuboidShape(3, 0, 3, 13, 6, 13);
	private static final VoxelShape COLLIDER = Block.createCuboidShape(3, 0, 3, 13, 10, 13);
	public static final BooleanProperty TOP = BooleanProperty.of("top");

	public BNNormalChair(Block block) {
		super(block, 10);
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(TOP, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING, TOP);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return state.get(TOP) ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return state.get(TOP) ? VoxelShapes.empty() : COLLIDER;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (state.get(TOP))
			return true;
		BlockState up = world.getBlockState(pos.up());
		return up.isAir() || (up.getBlock() == this && up.get(TOP));
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient())
			BlocksHelper.setWithUpdate((ServerWorld) world, pos.up(), state.with(TOP, true));
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(TOP)) {
			return world.getBlockState(pos.down()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
		else {
			return world.getBlockState(pos.up()).getBlock() == this ? state : Blocks.AIR.getDefaultState();
		}
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		if (!state.get(TOP))
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return Collections.emptyList();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state.get(TOP)) {
			pos = pos.down();
			state = world.getBlockState(pos);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (player.isCreative() && state.get(TOP) && world.getBlockState(pos.down()).getBlock() == this) {
			world.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
		}
		super.onBreak(world, pos, state, player);
	}
}
