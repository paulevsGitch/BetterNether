package com.paulevs.betternether.blocks;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import com.paulevs.betternether.blocks.materials.MaterialBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;


public class BlockStem extends BlockBaseNotFull {
	public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;
	private static final EnumMap<Axis, VoxelShape> OUTLINES = Maps.newEnumMap(Axis.class);

	public BlockStem(MaterialColor color) {
		super(MaterialBuilder.makeWood(color).notSolid());
		this.setDefaultState(this.getDefaultState().with(AXIS, Axis.Y));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return OUTLINES.get(state.get(AXIS));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader view, BlockPos pos, ISelectionContext ePos) {
		return OUTLINES.get(state.get(AXIS));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch (rotation) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch ((Direction.Axis) state.get(AXIS)) {
					case X:
						return (BlockState) state.with(AXIS, Direction.Axis.Z);
					case Z:
						return (BlockState) state.with(AXIS, Direction.Axis.X);
					default:
						return state;
				}
			default:
				return state;
		}
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		return (BlockState) this.getDefaultState().with(AXIS, ctx.getFace().getAxis());
	}

	static {
		OUTLINES.put(Axis.X, Block.makeCuboidShape(0, 5, 5, 16, 11, 11));
		OUTLINES.put(Axis.Y, Block.makeCuboidShape(5, 0, 5, 11, 16, 11));
		OUTLINES.put(Axis.Z, Block.makeCuboidShape(5, 5, 0, 11, 11, 16));
	}
}
