package paulevs.betternether.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;

public class BlockHookMushroom extends BlockMold {
	public BlockHookMushroom() {
		super(Materials.makeGrass(MaterialColor.PINK)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.noCollision()
				.breakInstantly()
				.ticksRandomly()
				.lightLevel(13));
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos.up()));
	}
}
