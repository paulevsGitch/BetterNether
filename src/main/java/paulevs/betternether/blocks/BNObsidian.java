package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class BNObsidian extends BlockBase {
	public BNObsidian() {
		super(FabricBlockSettings.copyOf(Blocks.OBSIDIAN));
	}
}
