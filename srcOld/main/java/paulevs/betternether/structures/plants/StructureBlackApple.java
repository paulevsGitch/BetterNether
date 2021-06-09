package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureBlackApple extends StructureScatter {
	public StructureBlackApple() {
		super(BlocksRegistry.BLACK_APPLE, BlockCommonPlant.AGE, 4);
	}
}