package paulevs.betternether.registers;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;

public class BlockCincinnasiteLantern extends Block
{
	public BlockCincinnasiteLantern()
	{
		super(FabricBlockSettings.copy(BlocksRegister.BLOCK_CINCINNASITE)
				.lightLevel(15)
				.build());
	}
}
