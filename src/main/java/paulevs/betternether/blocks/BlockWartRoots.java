package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BlockWartRoots extends BlockBase
{
	public BlockWartRoots()
	{
		super(FabricBlockSettings.copy(Blocks.NETHER_WART_BLOCK).build());
		this.setDropItself(false);
	}
}
