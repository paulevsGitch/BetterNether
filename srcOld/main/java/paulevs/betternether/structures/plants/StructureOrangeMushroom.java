package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureOrangeMushroom extends StructureScatter {
	public StructureOrangeMushroom() {
		super(BlocksRegistry.ORANGE_MUSHROOM, BlockCommonPlant.AGE, 4);
	}
}