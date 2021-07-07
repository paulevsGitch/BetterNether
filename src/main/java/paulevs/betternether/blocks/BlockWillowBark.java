package paulevs.betternether.blocks;

import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowBark extends BlockBase {
	public BlockWillowBark() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_RED).noOcclusion());
	}
}
