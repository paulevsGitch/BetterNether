package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registers.BlocksRegister;

public class StructureInkBush extends StructureScatter
{
	public StructureInkBush()
	{
		super(BlocksRegister.BLOCK_INK_BUSH, BlockCommonPlant.AGE, 4);
	}
}