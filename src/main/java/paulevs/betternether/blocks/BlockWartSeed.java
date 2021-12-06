package paulevs.betternether.blocks;

import java.util.EnumMap;
import java.util.Random;

import com.google.common.collect.Maps;

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
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.materials.Materials;
import paulevs.betternether.structures.plants.StructureWartTree;

public class BlockWartSeed extends BlockBaseNotFull implements BonemealableBlock {
	private static final EnumMap<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(Direction.class);
	private static final StructureWartTree STRUCTURE = new StructureWartTree();
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	static {
		BOUNDING_SHAPES.put(Direction.UP, Shapes.box(0.25, 0.0, 0.25, 0.75, 0.5, 0.75));
		BOUNDING_SHAPES.put(Direction.DOWN, Shapes.box(0.25, 0.5, 0.25, 0.75, 1.0, 0.75));
		BOUNDING_SHAPES.put(Direction.NORTH, Shapes.box(0.25, 0.25, 0.5, 0.75, 0.75, 1.0));
		BOUNDING_SHAPES.put(Direction.SOUTH, Shapes.box(0.25, 0.25, 0.0, 0.75, 0.75, 0.5));
		BOUNDING_SHAPES.put(Direction.WEST, Shapes.box(0.5, 0.25, 0.25, 1.0, 0.75, 0.75));
		BOUNDING_SHAPES.put(Direction.EAST, Shapes.box(0.0, 0.25, 0.25, 0.5, 0.75, 0.75));
	}

	public BlockWartSeed() {
		super(FabricBlockSettings.of(Materials.NETHER_SAPLING)
				.mapColor(MaterialColor.TERRACOTTA_RED)
				.sounds(SoundType.WART_BLOCK)
				.hardness(1F)
				.nonOpaque()
				.noCollision());
		this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState blockState = this.defaultBlockState();
		LevelReader worldView = ctx.getLevel();
		BlockPos blockPos = ctx.getClickedPos();
		Direction[] directions = ctx.getNearestLookingDirections();
		for (int i = 0; i < directions.length; ++i) {
			Direction direction = directions[i];
			Direction direction2 = direction.getOpposite();
			blockState = (BlockState) blockState.setValue(FACING, direction2);
			if (blockState.canSurvive(worldView, blockPos)) {
				return blockState;
			}
		}
		return null;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		Direction direction = (Direction) state.getValue(FACING);
		BlockPos blockPos = pos.relative(direction.getOpposite());
		return canSupportCenter(world, blockPos, direction) || world.getBlockState(pos.below()).getBlock() == Blocks.SOUL_SAND;
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
		Direction direction = (Direction) state.getValue(FACING);
		return direction == Direction.UP && BlocksHelper.isSoulSand(world.getBlockState(pos.below()));
	}

	@Override
	public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
		return random.nextInt(8) == 0;
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
		STRUCTURE.grow(world, pos, random);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return BlocksHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return BlocksHelper.mirrorHorizontal(state, mirror, FACING);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!canSurvive(state, world, pos))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}
}