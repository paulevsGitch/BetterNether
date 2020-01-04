package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BlockNetherrackMoss extends BlockBase
{
	public BlockNetherrackMoss()
	{
		super(FabricBlockSettings.copy(Blocks.NETHERRACK).build());
	}
}
