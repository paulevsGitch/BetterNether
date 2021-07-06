package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.blocks.BlockProperties.FoodShape;
import paulevs.betternether.registry.BlocksRegistry;

import java.util.Collections;
import java.util.List;

public class BlockStalagnateBowl extends BlockBaseNotFull {
	private static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 3, 11);
	public static final EnumProperty<FoodShape> FOOD = BlockProperties.FOOD;

	public BlockStalagnateBowl() {
		super(FabricBlockSettings.copyOf(BlocksRegistry.STALAGNATE).nonOpaque());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(FOOD, FoodShape.NONE));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FOOD);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return SHAPE;
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(state.get(FOOD).getItem()));
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos down = pos.down();
		return world.getBlockState(down).isSideSolidFullSquare(world, down, Direction.UP);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
}
