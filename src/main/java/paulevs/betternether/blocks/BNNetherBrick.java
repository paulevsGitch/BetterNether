package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BNNetherBrick extends BlockBase
{
	public BNNetherBrick()
	{
		super(FabricBlockSettings.copy(Blocks.NETHER_BRICKS).build());
	}
}