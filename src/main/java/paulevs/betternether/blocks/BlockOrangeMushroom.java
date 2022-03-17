package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;

public class BlockOrangeMushroom extends BlockCommonPlant {
	private static final VoxelShape[] SHAPES = new VoxelShape[] {
			Shapes.box(0.25, 0.0, 0.25, 0.75, 0.375, 0.75),
			Shapes.box(0.125, 0.0, 0.125, 0.875, 0.625, 0.875),
			Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375),
			Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
	};

	public BlockOrangeMushroom() {
		super(FabricBlockSettings.of(Materials.NETHER_PLANT)
				.mapColor(MaterialColor.COLOR_ORANGE)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.hardness(0.5F)
				.ticksRandomly()
				.noCollision());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return BlocksHelper.isNetherMycelium(world.getBlockState(pos.below()));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return SHAPES[state.getValue(AGE)];
	}
}