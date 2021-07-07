package paulevs.betternether.blocks;

import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.materials.Materials;

public class BlockWillowLog extends BNPillar {
	public BlockWillowLog() {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_RED).noOcclusion());
	}
}
