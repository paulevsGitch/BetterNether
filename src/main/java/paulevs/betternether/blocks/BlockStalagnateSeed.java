package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.plants.StructureStalagnate;

public class BlockStalagnateSeed extends BlockBaseNotFull implements BonemealableBlock {
	protected static final VoxelShape SHAPE_TOP = Block.box(4, 6, 4, 12, 16, 12);
	protected static final VoxelShape SHAPE_BOTTOM = Block.box(4, 0, 4, 12, 12, 12);
	private static final StructureStalagnate STRUCTURE = new StructureStalagnate();
	public static final BooleanProperty TOP = BooleanProperty.create("top");

	public BlockStalagnateSeed() {
		super(FabricBlockSettings.of(Material.PLANT)
				.mapColor(MaterialColor.COLOR_CYAN)
				.sounds(SoundType.CROP)
				.nonOpaque()
				.breakInstantly()
				.noCollision()
				.ticksRandomly());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
		this.registerDefaultState(getStateDefinition().any().setValue(TOP, true));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(TOP);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState blockState = this.defaultBlockState();
		if (ctx.getClickedFace() == Direction.DOWN)
			return blockState;
		else if (ctx.getClickedFace() == Direction.UP)
			return blockState.setValue(TOP, false);
		else
			return null;
	}

	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return state.getValue(TOP).booleanValue() ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		if (random.nextInt(16) == 0) {
			if (state.getValue(TOP).booleanValue())
				return BlocksHelper.downRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
			else
				return BlocksHelper.upRay(world, pos, StructureStalagnate.MIN_LENGTH) > 0;
		}
		return false;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		if (state.getValue(TOP).booleanValue())
			STRUCTURE.generateDown(world, pos, random);
		else
			STRUCTURE.generate(world, pos, random);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return BlocksHelper.isNetherrack(world.getBlockState(pos.above())) || BlocksHelper.isNetherrack(world.getBlockState(pos.below()));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (state.getValue(TOP).booleanValue()) {
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.above())))
				return state;
			else
				return Blocks.AIR.defaultBlockState();
		}
		else {
			if (BlocksHelper.isNetherrack(world.getBlockState(pos.below())))
				return state;
			else
				return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		super.tick(state, world, pos, random);
		if (isBonemealSuccess(world, random, pos, state)) {
			performBonemeal(world, random, pos, state);
		}
	}
}
