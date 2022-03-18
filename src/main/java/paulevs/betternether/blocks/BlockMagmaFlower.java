package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.interfaces.SurvivesOnMagmaBlock;

public class BlockMagmaFlower extends BlockCommonPlant implements SurvivesOnMagmaBlock {
	private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 12, 15);

	public BlockMagmaFlower() {
		super(MaterialColor.TERRACOTTA_ORANGE);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return canSurviveOnTop(state, world, pos);
	}
}
