package paulevs.betternether.registers;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import paulevs.betternether.blocks.BlockBase;

public class BlockWartRoots extends BlockBase
{
	public BlockWartRoots()
	{
		super(FabricBlockSettings.copy(Blocks.NETHER_WART_BLOCK).build());
		this.setDropItself(false);
	}
}
