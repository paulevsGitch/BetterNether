package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registers.BlocksRegister;

public class StructureEggPlant extends StructureScatter
{
	public StructureEggPlant()
	{
		super(BlocksRegister.EGG_PLANT, BlockCommonPlant.AGE, 4);
	}
}
