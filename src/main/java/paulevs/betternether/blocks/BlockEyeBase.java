package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockEyeBase extends BlockBase
{
	public BlockEyeBase(Settings settings)
	{
		super(settings);
		setDropItself(false);
	}
	
	public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType<?> type)
	{
		return false;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos)
	{
		BlockPos blockPos = pos.up();
		Block up = world.getBlockState(blockPos).getBlock();
		if (up != BlocksRegistry.EYE_VINE && up != Blocks.NETHERRACK)
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state)
	{
		return new ItemStack(BlocksRegistry.EYE_SEED);
	}
}
