package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;

public class BNGate extends FenceGateBlock
{
	public BNGate(Block block)
	{
		super(FabricBlockSettings.copy(block).build());
	}
}
