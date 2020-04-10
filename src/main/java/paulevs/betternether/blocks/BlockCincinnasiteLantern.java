package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteLantern extends BlockBase
{
	public BlockCincinnasiteLantern()
	{
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_BLOCK)
				.lightLevel(15)
				.build());
	}
}
