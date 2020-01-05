package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;

public class BNPlate extends PressurePlateBlock
{
	public BNPlate(ActivationRule type, Block block)
	{
		super(type, FabricBlockSettings.copy(block).build());
	}
}
