package paulevs.betternether.blocks;

import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockFarmland extends BlockBase {
	public BlockFarmland() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
	}
}
