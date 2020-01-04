package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import paulevs.betternether.registers.BlocksRegister;

public class BlockEyeBase extends BlockBase
{
	public BlockEyeBase(Settings settings)
	{
		super(settings);
	}
	
	public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType<?> type)
	{
		return false;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		BlockPos blockPos = pos.up();
		Block up = world.getBlockState(blockPos).getBlock();
		if (up != BlocksRegister.BLOCK_EYE_VINE && up != Blocks.NETHERRACK)
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
}
