package paulevs.betternether.blocks;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.BlockProperties.WillowBranchShape;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.registry.NetherBlocks;

import java.util.List;
import java.util.function.ToIntFunction;

public class BlockWillowBranch extends BlockBaseNotFull {
	private static final VoxelShape V_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
	public static final EnumProperty<WillowBranchShape> SHAPE = BlockProperties.WILLOW_SHAPE;

	public BlockWillowBranch() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_RED).nonOpaque().noCollision().lightLevel(getLuminance()));
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDropItself(false);
		this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, WillowBranchShape.MIDDLE));
	}

	protected static ToIntFunction<BlockState> getLuminance() {
		return (state) -> {
			return state.getValue(SHAPE) == WillowBranchShape.END ? 15 : 0;
		};
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return V_SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (world.isEmptyBlock(pos.above()))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(state.getValue(SHAPE) == WillowBranchShape.END ? NetherBlocks.MAT_WILLOW.getTorch() : NetherBlocks.WILLOW_LEAVES);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		if (state.getValue(SHAPE) == WillowBranchShape.END) {
			return Lists.newArrayList(new ItemStack(NetherBlocks.MAT_WILLOW.getTorch()));
		}
		else {
			return Lists.newArrayList();
		}
	}
}
