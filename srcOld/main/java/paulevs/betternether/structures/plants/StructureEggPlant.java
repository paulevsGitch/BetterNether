package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureEggPlant extends StructureScatter {
	public StructureEggPlant() {
		super(BlocksRegistry.EGG_PLANT, BlockCommonPlant.AGE, 4);
	}
}
