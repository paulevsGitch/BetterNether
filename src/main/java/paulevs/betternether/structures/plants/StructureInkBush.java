package paulevs.betternether.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.BlocksRegistry;

public class StructureInkBush extends StructureScatter {
	public StructureInkBush() {
		super(BlocksRegistry.INK_BUSH, BlockCommonPlant.AGE, 4);
	}
}