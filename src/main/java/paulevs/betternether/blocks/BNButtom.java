package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.WoodButtonBlock;

public class BNButtom extends WoodButtonBlock
{
	public BNButtom(Block block)
	{
		super(FabricBlockSettings.copy(block).build());
	}
}
