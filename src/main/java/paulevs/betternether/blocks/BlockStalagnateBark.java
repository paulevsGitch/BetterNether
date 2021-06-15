package paulevs.betternether.blocks;

import net.minecraft.block.MapColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockStalagnateBark extends BNPillar {
	public BlockStalagnateBark() {
		super(Materials.makeWood(MapColor.TERRACOTTA_LIME));
	}
}