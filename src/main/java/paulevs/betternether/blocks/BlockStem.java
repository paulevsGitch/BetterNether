package paulevs.betternether.blocks;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.blocks.materials.Materials;

public class BlockStem extends BlockBaseNotFull {
	public static final EnumProperty<Axis> AXIS = Properties.AXIS;
	private static final EnumMap<Axis, VoxelShape> OUTLINES = Maps.newEnumMap(Axis.class);

	public BlockStem(MaterialColor color) {
		super(Materials.makeWood(color).nonOpaque());
		this.setDefaultState(this.getDefaultState().with(AXIS, Axis.Y));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return OUTLINES.get(state.get(AXIS));
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
		return OUTLINES.get(state.get(AXIS));
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
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
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState) this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
	}

	static {
		OUTLINES.put(Axis.X, Block.createCuboidShape(0, 5, 5, 16, 11, 11));
		OUTLINES.put(Axis.Y, Block.createCuboidShape(5, 0, 5, 11, 16, 11));
		OUTLINES.put(Axis.Z, Block.createCuboidShape(5, 5, 0, 11, 11, 16));
	}
}
