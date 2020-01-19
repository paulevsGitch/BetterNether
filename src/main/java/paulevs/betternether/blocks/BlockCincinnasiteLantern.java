package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import paulevs.betternether.registers.BlocksRegister;

public class BlockCincinnasiteLantern extends BlockBase
{
	public BlockCincinnasiteLantern()
	{
		super(FabricBlockSettings.copy(BlocksRegister.BLOCK_CINCINNASITE)
				.lightLevel(15)
				.build());
	}
}
