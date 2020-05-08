package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AnvilBlock;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteAnvil extends AnvilBlock
{
	public BlockCincinnasiteAnvil()
	{
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_BLOCK).nonOpaque());
	}
}
