package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

public class BlockNetherReed extends BlockBase
{
	public static final BooleanProperty TOP = BooleanProperty.of("top");
	
	public BlockNetherReed()
	{
		super(FabricBlockSettings.copy(Blocks.TALL_GRASS).build());
		this.setRenderLayer(BlockRenderLayer.CUTOUT);
		this.setDefaultState(getStateManager().getDefaultState().with(TOP, true));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
	{
        stateManager.add(TOP);
    }
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos)
	{
		return 1.0F;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		Block up = world.getBlockState(pos.up()).getBlock();
		Block down = world.getBlockState(pos.down()).getBlock();
		if (down != Blocks.NETHERRACK && down != this)
			return Blocks.AIR.getDefaultState();
		else if (up != this)
			return this.getDefaultState();
		else
			return this.getDefaultState().with(TOP, false);
	}
}
