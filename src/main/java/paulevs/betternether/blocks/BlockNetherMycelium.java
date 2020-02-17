package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;

public class BlockNetherMycelium extends BlockBase
{
	public BlockNetherMycelium()
	{
		super(FabricBlockSettings.copy(Blocks.NETHERRACK).materialColor(MaterialColor.GRAY).build());
	}
}