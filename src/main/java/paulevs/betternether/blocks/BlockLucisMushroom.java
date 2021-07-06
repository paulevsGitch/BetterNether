package paulevs.betternether.blocks;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.MHelper;
import paulevs.betternether.blocks.BlockProperties.EnumLucisShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;

import java.util.List;

public class BlockLucisMushroom extends BlockBaseNotFull {
	private static final VoxelShape V_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 9, 16);
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final EnumProperty<EnumLucisShape> SHAPE = BlockProperties.LUCIS_SHAPE;

	public BlockLucisMushroom() {
		super(FabricBlockSettings.of(Material.SOLID_ORGANIC)
				.materialColor(MapColor.YELLOW)
				.breakByTool(FabricToolTags.AXES)
				.sounds(BlockSoundGroup.WOOD)
				.hardness(1F)
				.luminance(15)
				.nonOpaque());
		this.setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(SHAPE, EnumLucisShape.CORNER));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING, SHAPE);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return V_SHAPE;
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Lists.newArrayList(new ItemStack(BlocksRegistry.LUCIS_SPORE), new ItemStack(ItemsRegistry.GLOWSTONE_PILE, MHelper.randRange(2, 4, MHelper.RANDOM)));
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		if (mirror == BlockMirror.FRONT_BACK) {
			if (state.get(SHAPE) == EnumLucisShape.SIDE) state = state.with(FACING, state.get(FACING).rotateYCounterclockwise());
			if (state.get(FACING) == Direction.NORTH) return state.with(FACING, Direction.WEST);
			if (state.get(FACING) == Direction.WEST) return state.with(FACING, Direction.NORTH);
			if (state.get(FACING) == Direction.SOUTH) return state.with(FACING, Direction.EAST);
			if (state.get(FACING) == Direction.EAST) return state.with(FACING, Direction.SOUTH);
		}
		else if (mirror == BlockMirror.LEFT_RIGHT) {
			if (state.get(SHAPE) == EnumLucisShape.SIDE) state = state.with(FACING, state.get(FACING).rotateYCounterclockwise());
			if (state.get(FACING) == Direction.NORTH) return state.with(FACING, Direction.EAST);
			if (state.get(FACING) == Direction.EAST) return state.with(FACING, Direction.NORTH);
			if (state.get(FACING) == Direction.SOUTH) return state.with(FACING, Direction.WEST);
			if (state.get(FACING) == Direction.WEST) return state.with(FACING, Direction.SOUTH);
		}
		return state;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(BlocksRegistry.LUCIS_SPORE);
	}
}
