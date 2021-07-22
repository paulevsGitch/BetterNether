package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.blocks.BlockProperties.PottedPlantShape;
import paulevs.betternether.registry.NetherBlocks;

public class BlockPottedPlant extends BlockBaseNotFull {
	public static final EnumProperty<PottedPlantShape> PLANT = BlockProperties.PLANT;

	public BlockPottedPlant() {
		super(FabricBlockSettings.of(Material.PLANT)
				.mapColor(MaterialColor.COLOR_BLACK)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.lightLevel(getLuminance()));
		this.setDropItself(false);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.registerDefaultState(getStateDefinition().any().setValue(PLANT, PottedPlantShape.AGAVE));
	}

	private static ToIntFunction<BlockState> getLuminance() {
		return (blockState) -> {
			if (blockState.getValue(PLANT) == PottedPlantShape.WILLOW)
				return 12;
			else if (blockState.getValue(PLANT) == PottedPlantShape.JELLYFISH_MUSHROOM)
				return 13;
			else
				return 0;
		};
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		Block block = state.getValue(PLANT).getBlock();
		Vec3 vec3d = block.defaultBlockState().getOffset(view, pos);
		return block.getShape(block.defaultBlockState(), view, pos, ePos).move(-vec3d.x, -0.5 - vec3d.y, -vec3d.z);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(PLANT);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return world.getBlockState(pos.below()).getBlock() instanceof BlockBNPot;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		Block block = state.getValue(PLANT).getBlock();
		return Collections.singletonList(new ItemStack(block.asItem()));
	}

	public static BlockState getPlant(Item item) {
		for (PottedPlantShape shape : PottedPlantShape.values()) {
			if (shape.getItem().equals(item))
				return NetherBlocks.POTTED_PLANT.defaultBlockState().setValue(PLANT, shape);
		}
		return null;
	}
}
