package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWartPlanks extends Block
{
	public BlockWartPlanks()
	{
		super(Materials.makeWood(MaterialColor.RED).build());
	}
}
