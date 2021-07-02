package paulevs.betternether.blocks;

import net.minecraft.block.Blocks;
import paulevs.betternether.BlocksHelper;

public class BNNetherBrick extends BlockBase {
	public BNNetherBrick() {
		super(BlocksHelper.copySettingsOf(Blocks.NETHER_BRICKS));
	}
}