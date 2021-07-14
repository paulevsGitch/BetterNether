package paulevs.betternether.blocks;

import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BNPlanks extends BlockBase {
	public BNPlanks(MaterialColor color) {
		super(Materials.makeWood(color));
	}
}
