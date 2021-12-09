package paulevs.betternether.world.structures.plants;

import paulevs.betternether.blocks.BlockCommonPlant;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.IStructure;

public class StructureFeatherFern extends StructureScatter implements IStructure {
	public StructureFeatherFern() {
		super(NetherBlocks.FEATHER_FERN, BlockCommonPlant.AGE, 4);
	}
}