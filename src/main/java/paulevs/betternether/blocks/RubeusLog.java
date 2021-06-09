package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;
import paulevs.betternether.blocks.shapes.TripleShape;

public class RubeusLog extends BNLogStripable {
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);

	public RubeusLog(Block striped) {
		super(MapColor.MAGENTA, striped);
		this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.Y).with(SHAPE, TripleShape.BOTTOM));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		super.appendProperties(stateManager);
		stateManager.add(SHAPE);
	}
}