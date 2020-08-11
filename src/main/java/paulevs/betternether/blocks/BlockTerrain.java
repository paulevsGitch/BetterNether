package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BlockTerrain extends BlockBase
{
	public BlockTerrain()
	{
		super(FabricBlockSettings.copyOf(Blocks.NETHERRACK).requiresTool());
	}
}