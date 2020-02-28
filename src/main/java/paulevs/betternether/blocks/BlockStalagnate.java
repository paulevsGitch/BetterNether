package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import paulevs.betternether.blocks.shapes.TripleShape;

public class BlockStalagnate extends BlockStalagnateStem
{
	public static final EnumProperty<TripleShape> SHAPE = EnumProperty.of("shape", TripleShape.class);
	
	public BlockStalagnate()
	{
		super();
		//this.setClimmable(true);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, TripleShape.MIDDLE));
		this.setDropItself(false);
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
}
