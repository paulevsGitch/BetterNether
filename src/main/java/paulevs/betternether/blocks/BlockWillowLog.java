package paulevs.betternether.blocks;

import net.minecraft.block.MapColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowLog extends BNPillar {
	public BlockWillowLog() {
		super(Materials.makeWood(MapColor.TERRACOTTA_RED).nonOpaque());
	}
}
