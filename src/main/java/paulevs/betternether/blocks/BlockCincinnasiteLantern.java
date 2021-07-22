package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import paulevs.betternether.registry.NetherBlocks;

public class BlockCincinnasiteLantern extends BlockBase {
	public BlockCincinnasiteLantern() {
		super(FabricBlockSettings.copyOf(NetherBlocks.CINCINNASITE_BLOCK).luminance(15));
	}
}
