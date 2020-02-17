package paulevs.betternether.blocks;

import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowLog extends BNPillar
{
	public BlockWillowLog()
	{
		super(Materials.makeWood(MaterialColor.RED_TERRACOTTA).nonOpaque().build());
	}
}
