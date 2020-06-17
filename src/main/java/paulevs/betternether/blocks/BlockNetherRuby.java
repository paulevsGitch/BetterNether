package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BlockNetherRuby extends BlockBase
{
	public BlockNetherRuby()
	{
		super(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK));
	}
}
