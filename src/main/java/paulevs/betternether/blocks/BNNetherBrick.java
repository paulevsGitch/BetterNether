package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Blocks;

public class BNNetherBrick extends BlockBase {
	public BNNetherBrick() {
		super(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
	}
}