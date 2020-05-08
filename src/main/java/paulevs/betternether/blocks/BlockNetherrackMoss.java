package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BlockNetherrackMoss extends BlockBase
{
	public BlockNetherrackMoss()
	{
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK));
	}
}