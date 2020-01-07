package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import paulevs.betternether.registers.BlocksRegister;

public class BlockCincinnasitPillar extends BNPillar
{
	public BlockCincinnasitPillar()
	{
		super(FabricBlockSettings.copy(BlocksRegister.BLOCK_CINCINNASITE).build());
	}
}