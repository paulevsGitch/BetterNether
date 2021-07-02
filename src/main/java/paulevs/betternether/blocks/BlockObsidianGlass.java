package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import paulevs.betternether.BlocksHelper;

public class BlockObsidianGlass extends BlockBaseNotFull {
	public BlockObsidianGlass() {
		super(BlocksHelper.copySettingsOf(Blocks.OBSIDIAN)
				.nonOpaque()
				.suffocates((arg1, arg2, arg3) -> {
					return false;
				})
				.blockVision((arg1, arg2, arg3) -> {
					return false;
				}));
		this.setRenderLayer(BNRenderLayer.TRANSLUCENT);
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