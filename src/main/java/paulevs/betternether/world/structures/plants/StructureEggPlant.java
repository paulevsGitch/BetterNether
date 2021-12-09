package paulevs.betternether.world.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.NetherBlocks;

public class StructureEggPlant extends StructureScatter {
	public StructureEggPlant() {
		super(NetherBlocks.EGG_PLANT, BlockCommonPlant.AGE, 4);
	}
}
