package paulevs.betternether.blocks;

import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockStalagnatePlanks extends BlockBase
{
	public BlockStalagnatePlanks()
	{
		super(Materials.makeWood(MaterialColor.LIME_TERRACOTTA).build());
	}
}
