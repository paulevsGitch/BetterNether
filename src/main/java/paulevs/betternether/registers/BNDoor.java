package paulevs.betternether.registers;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;

public class BNDoor extends DoorBlock
{
	protected BNDoor(Block block)
	{
		super(FabricBlockSettings.copy(block).build());
	}
}
