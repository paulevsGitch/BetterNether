package paulevs.betternether.blocks;

import net.minecraft.block.MapColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockFarmland extends BlockBase {
	public BlockFarmland() {
		super(Materials.makeWood(MapColor.TERRACOTTA_LIME));
	}
}
