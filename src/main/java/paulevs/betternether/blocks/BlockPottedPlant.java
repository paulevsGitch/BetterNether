package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.blocks.BlockProperties.PottedPlantShape;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;

public class BlockPottedPlant extends BlockBaseNotFull {
	public static final EnumProperty<PottedPlantShape> PLANT = BlockProperties.PLANT;

	public BlockPottedPlant() {
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MapColor.BLACK)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.luminance(getLuminance()));
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(PLANT, PottedPlantShape.AGAVE));
	}

	private static ToIntFunction<BlockState> getLuminance() {
		return (blockState) -> {
			if (blockState.get(PLANT) == PottedPlantShape.WILLOW)
				return 12;
			else if (blockState.get(PLANT) == PottedPlantShape.JELLYFISH_MUSHROOM)
				return 13;
			else
				return 0;
		};
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		Block block = state.get(PLANT).getBlock();
		Vec3d vec3d = block.getDefaultState().getModelOffset(view, pos);
		return block.getOutlineShape(block.getDefaultState(), view, pos, ePos).offset(-vec3d.x, -0.5 - vec3d.y, -vec3d.z);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(PLANT);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock() instanceof BlockBNPot;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!canPlaceAt(state, world, pos))
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		Block block = state.get(PLANT).getBlock();
		return Collections.singletonList(new ItemStack(block.asItem()));
	}

	public static BlockState getPlant(Item item) {
		for (PottedPlantShape shape : PottedPlantShape.values()) {
			if (shape.getItem().equals(item))
				return BlocksRegistry.POTTED_PLANT.getDefaultState().with(PLANT, shape);
		}
		return null;
	}
}
