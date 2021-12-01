package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import paulevs.betternether.BlocksHelper;

public class BlockWillowLeaves extends BNLeaves {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

	public BlockWillowLeaves(Block sapling) {
		super(sapling, MaterialColor.TERRACOTTA_RED);
		
		//this.setDropItself(false);
		this.registerDefaultState(getStateDefinition().any().setValue(DISTANCE, 7).setValue(PERSISTENT, false));
		this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP).setValue(NATURAL, true));
		//this.setRenderLayer(BNRenderLayer.CUTOUT);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		super.createBlockStateDefinition(stateManager);
		stateManager.add(FACING, NATURAL);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(NATURAL, false);
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
		if (state.getValue(NATURAL) && world.isEmptyBlock(pos.relative(state.getValue(FACING).getOpposite())))
			return Blocks.AIR.defaultBlockState();
		else
			return state;
	}

	@Environment(EnvType.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
		return true;
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
		return Shapes.empty();
	}
}
