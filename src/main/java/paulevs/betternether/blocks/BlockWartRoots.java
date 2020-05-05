package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BlockWartRoots extends BlockBase
{
	public BlockWartRoots()
	{
		super(FabricBlockSettings.copy(Blocks.NETHER_WART_BLOCK));
		this.setDropItself(false);
	}
}
