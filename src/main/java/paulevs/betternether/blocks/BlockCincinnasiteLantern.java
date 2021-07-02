package paulevs.betternether.blocks;

import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteLantern extends BlockBase {
	public BlockCincinnasiteLantern() {
		super(BlocksHelper.copySettingsOf(BlocksRegistry.CINCINNASITE_BLOCK).luminance(15));
	}
}
