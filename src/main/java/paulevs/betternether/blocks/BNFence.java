package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;

public class BNFence extends FenceBlock
{
	public BNFence(Block block)
	{
		super(FabricBlockSettings.copy(block).build());
	}
}
