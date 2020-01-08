package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import paulevs.betternether.registers.BlocksRegister;

public class BlockCincinnasitFireBowl extends BlockBase
{
	public BlockCincinnasitFireBowl()
	{
		super(FabricBlockSettings.copy(BlocksRegister.BLOCK_CINCINNASITE).build());
	}
}