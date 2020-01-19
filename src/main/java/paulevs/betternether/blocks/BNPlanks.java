package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BNPlanks extends Block
{
	public BNPlanks(MaterialColor color)
	{
		super(Materials.makeWood(color).build());
	}
}
