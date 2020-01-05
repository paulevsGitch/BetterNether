package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class BlockStalagnate extends BlockStalagnateStem
{
	public static final EnumProperty<StalagnateShape> SHAPE = EnumProperty.of("shape", StalagnateShape.class);
	
	public BlockStalagnate()
	{
		super();
		this.setClimmable(true);
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(SHAPE, StalagnateShape.MIDDLE));
		this.setDropItself(false);
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
	
	public enum StalagnateShape implements StringIdentifiable
	{
		TOP,
		MIDDLE,
		BOTTOM;
		
		@Override
		public String asString()
		{
			return this.toString().toLowerCase();
		}
	}
}
