package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureBarrelCactus extends StructureScatter {
	public StructureBarrelCactus() {
		super(BlocksRegistry.BARREL_CACTUS, BlockCommonPlant.AGE, 4);
	}
}