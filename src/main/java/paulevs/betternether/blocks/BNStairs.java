package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class BNStairs extends StairsBlock
{
	public BNStairs(Block block)
	{
		super(block.getDefaultState(), FabricBlockSettings.copy(block).build());
	}
}
