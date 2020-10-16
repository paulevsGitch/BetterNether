package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteLantern extends BlockBase {
	public BlockCincinnasiteLantern() {
		super(FabricBlockSettings.copyOf(BlocksRegistry.CINCINNASITE_BLOCK).lightLevel(15));
	}
}
