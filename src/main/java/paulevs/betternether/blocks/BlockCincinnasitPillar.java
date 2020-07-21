package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasitPillar extends BlockBase
{
	public static final EnumProperty<CincinnasitPillarShape> SHAPE = EnumProperty.of("shape", CincinnasitPillarShape.class);
	
	public BlockCincinnasitPillar()
	{
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_BLOCK));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(SHAPE);
    }
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		boolean top = world.getBlockState(pos.up()).getBlock() == this;
		boolean bottom = world.getBlockState(pos.down()).getBlock() == this;
		if (top && bottom)
			return state.with(SHAPE, CincinnasitPillarShape.MIDDLE);
		else if (top)
			return state.with(SHAPE, CincinnasitPillarShape.BOTTOM);
		else if (bottom)
			return state.with(SHAPE, CincinnasitPillarShape.TOP);
		else
			return state.with(SHAPE, CincinnasitPillarShape.SMALL);
	}
	
	public static enum CincinnasitPillarShape implements StringIdentifiable
	{
		SMALL("small"),
		TOP("top"),
		MIDDLE("middle"),
		BOTTOM("bottom");
		
		final String name;
		
		CincinnasitPillarShape(String name)
		{
			this.name = name;
		}
		
		@Override
		public String asString()
		{
			return name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}