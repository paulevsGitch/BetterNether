package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import paulevs.betternether.registers.BlocksRegister;

public class BlockCincinnasitePedestal extends BlockBaseNotFull
{
	private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);
	
	public BlockCincinnasitePedestal()
	{
		super(FabricBlockSettings.copy(BlocksRegister.CINCINNASITE_BLOCK).nonOpaque().build());
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos)
	{
		return SHAPE;
	}
}
