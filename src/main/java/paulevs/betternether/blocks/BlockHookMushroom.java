package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;

public class BlockHookMushroom extends BlockMold {
	public BlockHookMushroom() {
		super(Materials.makeGrass(MaterialColor.COLOR_PINK)
				.luminance(13)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.ticksRandomly()
				);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos.above()));
	}
}
