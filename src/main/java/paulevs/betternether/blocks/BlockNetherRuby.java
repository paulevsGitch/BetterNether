package paulevs.betternether.blocks;

import net.minecraft.block.Blocks;
import paulevs.betternether.BlocksHelper;

public class BlockNetherRuby extends BlockBase {
	public BlockNetherRuby() {
		super(BlocksHelper.copySettingsOf(Blocks.DIAMOND_BLOCK));
	}
}
