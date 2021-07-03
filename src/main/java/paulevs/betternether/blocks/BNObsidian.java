package paulevs.betternether.blocks;

import net.minecraft.block.Blocks;
import paulevs.betternether.BlocksHelper;

public class BNObsidian extends BlockBase {
	public BNObsidian() {
		super(BlocksHelper.copySettingsOf(Blocks.OBSIDIAN));
	}
}
