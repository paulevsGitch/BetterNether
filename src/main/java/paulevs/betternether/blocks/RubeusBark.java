package paulevs.betternether.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockProperties.TripleShape;
import ru.bclib.blocks.StripableBarkBlock;

public class RubeusBark extends StripableBarkBlock {
	public RubeusBark(MaterialColor color, Block striped) {
		super(color, striped);
		this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y).setValue(RubeusLog.SHAPE, TripleShape.BOTTOM));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		super.createBlockStateDefinition(stateManager);
		stateManager.add(RubeusLog.SHAPE);
	}
}
