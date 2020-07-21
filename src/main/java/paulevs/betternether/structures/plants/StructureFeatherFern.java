package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.IStructure;

public class StructureFeatherFern extends StructureScatter implements IStructure
{
	public StructureFeatherFern()
	{
		super(BlocksRegistry.FEATHER_FERN, BlockCommonPlant.AGE, 4);
	}
}