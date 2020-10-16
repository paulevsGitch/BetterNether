package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import paulevs.betternether.registry.BlocksRegistry;

public class BlockCincinnasiteFrame extends BlockBaseNotFull {
	public BlockCincinnasiteFrame() {
		super(FabricBlockSettings.copy(BlocksRegistry.CINCINNASITE_BLOCK).nonOpaque());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState state, BlockState neighbor, Direction facing) {
		return neighbor.getBlock() == this ? true : super.isSideInvisible(state, neighbor, facing);
	}
}
