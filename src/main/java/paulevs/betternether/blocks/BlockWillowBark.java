package paulevs.betternether.blocks;

import net.minecraft.block.MapColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowBark extends BlockBase {
	public BlockWillowBark() {
		super(Materials.makeWood(MapColor.TERRACOTTA_RED).nonOpaque());
	}
}
