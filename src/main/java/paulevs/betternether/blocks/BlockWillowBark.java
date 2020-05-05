package paulevs.betternether.blocks;

import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowBark extends BlockBase
{
	public BlockWillowBark()
	{
		super(Materials.makeWood(MaterialColor.RED_TERRACOTTA).nonOpaque());
	}
}
