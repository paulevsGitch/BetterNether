package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class BNSlab extends SlabBlock
{
	public BNSlab(Block block)
	{
		super(FabricBlockSettings.copy(block).build());
	}
}
