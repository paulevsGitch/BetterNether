package paulevs.betternether.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.interfaces.SurvivesOnGravel;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class BlockNetherCactus extends BlockBaseNotFull implements SurvivesOnGravel {
	private static final VoxelShape TOP_SHAPE = Block.box(4, 0, 4, 12, 8, 12);
	private static final VoxelShape SIDE_SHAPE = Block.box(5, 0, 5, 11, 16, 11);
	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public BlockNetherCactus() {
		super(FabricBlockSettings.of(Material.CACTUS)
				.mapColor(MaterialColor.TERRACOTTA_ORANGE)
				.sounds(SoundType.WOOL)
				.nonOpaque()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.registerDefaultState(getStateDefinition().any().setValue(TOP, true));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(TOP);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return state.getValue(TOP).booleanValue() ? TOP_SHAPE : SIDE_SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (canSurvive(state, world, pos)) {
			Block up = world.getBlockState(pos.above()).getBlock();
			if (up == this)
				return state.setValue(TOP, false);
			else
				return this.defaultBlockState();
		}
		else
			return Blocks.AIR.defaultBlockState();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState down = world.getBlockState(pos.below());
		return isSurvivable(down) || down.getBlock() == this;
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (!canSurvive(state, world, pos)) {
			world.destroyBlock(pos, true);
			return;
		}
		if (state.getValue(TOP).booleanValue() && random.nextInt(16) == 0) {
			BlockPos up = pos.above();
			boolean grow = world.getBlockState(up).getBlock() == Blocks.AIR;
			grow = grow && (BlocksHelper.getLengthDown(world, pos, this) < 3);
			if (grow) {
				BlocksHelper.setWithUpdate(world, up, defaultBlockState());
				BlocksHelper.setWithUpdate(world, pos, defaultBlockState().setValue(TOP, false));
			}
		}
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		entity.hurt(DamageSource.CACTUS, 1.0F);
	}
}