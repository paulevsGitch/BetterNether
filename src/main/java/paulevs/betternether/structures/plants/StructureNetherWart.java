package paulevs.betternether.structures.plants;

import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;

public class StructureNetherWart extends StructureScatter {
	public StructureNetherWart() {
		super(Blocks.NETHER_WART, NetherWartBlock.AGE, 4);
	}
}