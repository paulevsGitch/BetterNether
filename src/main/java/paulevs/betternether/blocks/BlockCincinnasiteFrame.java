package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteFrame extends BlockBaseNotFull {
	public BlockCincinnasiteFrame() {
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_BLOCK).noOcclusion());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public boolean skipRendering(BlockState state, BlockState neighbor, Direction facing) {
		return neighbor.getBlock() == this ? true : super.skipRendering(state, neighbor, facing);
	}
}
