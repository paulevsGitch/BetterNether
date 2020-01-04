package paulevs.betternether.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class BlockBaseNotFull extends BlockBase
{

	public BlockBaseNotFull(Settings settings)
	{
		super(settings);
	}

	public boolean canSuffocate(BlockState state, BlockView view, BlockPos pos)
	{
		return false;
	}

	public boolean isSimpleFullBlock(BlockState state, BlockView view, BlockPos pos)
	{
		return false;
	}

	public boolean allowsSpawning(BlockState state, BlockView view, BlockPos pos, EntityType<?> type)
	{
		return false;
	}
}
