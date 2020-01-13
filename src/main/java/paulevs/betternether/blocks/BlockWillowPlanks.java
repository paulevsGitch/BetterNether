package paulevs.betternether.blocks;

import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowPlanks extends BlockBase
{
	public BlockWillowPlanks()
	{
		super(Materials.makeWood(MaterialColor.RED_TERRACOTTA).build());
	}
}
