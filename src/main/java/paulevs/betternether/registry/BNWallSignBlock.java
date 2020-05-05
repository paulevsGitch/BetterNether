package paulevs.betternether.registry;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.SignType;
import net.minecraft.world.BlockView;
import paulevs.betternether.blockentities.NetherSignBlockEntity;

public class BNWallSignBlock extends WallSignBlock
{
	public BNWallSignBlock(Settings settings, SignType signType)
	{
		super(settings, signType);
	}
	
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new NetherSignBlockEntity();
	}
}
