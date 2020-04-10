package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BNObsidian extends BlockBase
{
	public BNObsidian()
	{
		super(FabricBlockSettings.copy(Blocks.OBSIDIAN).build());
	}
}
