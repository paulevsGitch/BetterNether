package paulevs.betternether.registry;

import net.minecraft.block.MaterialColor;
import paulevs.betternether.blocks.BNPillar;
import paulevs.betternether.blocks.materials.Materials;

public class BlockRubeusCone extends BNPillar
{
	public BlockRubeusCone()
	{
		super(Materials.makeWood(MaterialColor.CYAN).nonOpaque().lightLevel(10).build());
	}
}