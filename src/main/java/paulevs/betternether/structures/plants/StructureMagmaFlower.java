package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureMagmaFlower extends StructureScatter
{
	public StructureMagmaFlower()
	{
		super(BlocksRegistry.MAGMA_FLOWER, BlockCommonPlant.AGE, 4);
	}
}
