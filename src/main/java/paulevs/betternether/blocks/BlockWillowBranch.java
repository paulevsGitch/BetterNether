package paulevs.betternether.blocks;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.BlocksRegistry;

import java.util.List;
import java.util.function.ToIntFunction;

public class BlockWillowBranch extends BlockBaseNotFull {
	private static final VoxelShape V_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 16, 12);
	public static final EnumProperty<WillowBranchShape> SHAPE = EnumProperty.of("shape", WillowBranchShape.class);

	public BlockWillowBranch() {
		super(Materials.makeWood(MapColor.TERRACOTTA_RED).nonOpaque().noCollision().luminance(getLuminance()));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, WillowBranchShape.MIDDLE));
	}

	protected static ToIntFunction<BlockState> getLuminance() {
		return (state) -> {
			return state.get(SHAPE) == WillowBranchShape.END ? 15 : 0;
		};
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return V_SHAPE;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (world.isAir(pos.up()))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}

	public enum WillowBranchShape implements StringIdentifiable {
		END("end"), MIDDLE("middle");

		final String name;

		WillowBranchShape(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(state.get(SHAPE) == WillowBranchShape.END ? BlocksRegistry.WILLOW_TORCH : BlocksRegistry.WILLOW_LEAVES);
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		if (state.get(SHAPE) == WillowBranchShape.END) {
			return Lists.newArrayList(new ItemStack(BlocksRegistry.WILLOW_TORCH));
		}
		else {
			return Lists.newArrayList();
		}
	}
}
