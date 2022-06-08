package org.betterx.betternether.blocks;

import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockSmallLantern extends BlockBaseNotFull {
    private static final VoxelShape SHAPE_NORTH = box(5, 0, 8, 11, 16, 16);
    private static final VoxelShape SHAPE_SOUTH = box(5, 0, 0, 11, 16, 8);
    private static final VoxelShape SHAPE_WEST = box(8, 0, 5, 16, 16, 11);
    private static final VoxelShape SHAPE_EAST = box(0, 0, 5, 8, 16, 11);
    private static final VoxelShape SHAPE_UP = box(5, 0, 5, 11, 9, 11);
    private static final VoxelShape SHAPE_DOWN = box(5, 3, 5, 11, 16, 11);

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public BlockSmallLantern() {
        super(FabricBlockSettings.copy(NetherBlocks.CINCINNASITE_LANTERN).noOcclusion());
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.DOWN));
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case EAST:
                return SHAPE_EAST;
            case WEST:
                return SHAPE_WEST;
            case UP:
                return SHAPE_UP;
            case DOWN:
            default:
                return SHAPE_DOWN;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        var _facing = state.getValues().get(FACING);
        if (_facing == null) return false;
        final var shape = FACING.getValueClass().cast(_facing);
        Direction direction = shape.getOpposite();
        return canSupportCenter(world, pos.relative(direction), direction.getOpposite());
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
    public BlockState updateShape(
            BlockState state,
            Direction facing,
            BlockState neighborState,
            LevelAccessor world,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (canSurvive(state, world, pos))
            return state;
        else
            return Blocks.AIR.defaultBlockState();
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
            blockState = blockState.setValue(FACING, direction2);
            if (blockState.canSurvive(worldView, blockPos)) {
                return blockState;
            }
        }
        return null;
    }
}
