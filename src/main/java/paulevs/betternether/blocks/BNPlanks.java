package paulevs.betternether.blocks;

import net.minecraft.block.MapColor;
import paulevs.betternether.blocks.materials.Materials;

public class BNPlanks extends BlockBase {
	public BNPlanks(MapColor color) {
		super(Materials.makeWood(color));
	}
}
