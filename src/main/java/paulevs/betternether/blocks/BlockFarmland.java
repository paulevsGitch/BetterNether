package paulevs.betternether.blocks;

import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockFarmland extends BlockBase
{
	public BlockFarmland()
	{
		super(Materials.makeWood(MaterialColor.LIME_TERRACOTTA));
	}
}
