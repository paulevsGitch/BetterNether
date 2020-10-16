package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureAgave extends StructureScatter {
	public StructureAgave() {
		super(BlocksRegistry.AGAVE, BlockCommonPlant.AGE, 4);
	}
}