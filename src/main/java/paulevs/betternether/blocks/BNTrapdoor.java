package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.TrapdoorBlock;

public class BNTrapdoor extends TrapdoorBlock
{
	public BNTrapdoor(Block block)
	{
		super(FabricBlockSettings.copy(block).build());
	}
}
