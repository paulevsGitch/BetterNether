package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.interfaces.SurvivesOnSoulGroundOrFarmLand;
import paulevs.betternether.registry.NetherBlocks;

public class BlockSoulLilySapling extends BaseBlockCommonSapling implements SurvivesOnSoulGroundOrFarmLand {
	public BlockSoulLilySapling() {
		super(NetherBlocks.SOUL_LILY, MaterialColor.COLOR_ORANGE);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canSurviveOnTop(state, world, pos);
	}
}
