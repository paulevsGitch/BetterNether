package org.betterx.betternether.blocks;

import org.betterx.betternether.blocks.BNBlockProperties.BrownMushroomShape;
import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockBrownLargeMushroom extends BlockBaseNotFull {
    private static final VoxelShape TOP_CENTER_SHAPE = box(0, 0.1, 0, 16, 16, 16);
    private static final VoxelShape TOP_EDGE_SHAPE = box(0, 8, 0, 16, 16, 16);
    private static final VoxelShape MIDDLE_SHAPE = box(4, 0, 4, 12, 16, 12);
    public static final EnumProperty<BrownMushroomShape> SHAPE = BNBlockProperties.BROWN_MUSHROOM_SHAPE;

    private static final BrownMushroomShape[] ROT_SIDE = new BrownMushroomShape[]{
            BrownMushroomShape.SIDE_N,
            BrownMushroomShape.SIDE_E,
            BrownMushroomShape.SIDE_S,
            BrownMushroomShape.SIDE_W
    };

    private static final BrownMushroomShape[] ROT_CORNER = new BrownMushroomShape[]{
            BrownMushroomShape.CORNER_N,
            BrownMushroomShape.CORNER_E,
            BrownMushroomShape.CORNER_S,
            BrownMushroomShape.CORNER_W
    };

    public BlockBrownLargeMushroom() {
        super(Materials.makeWood(MaterialColor.COLOR_BROWN).noOcclusion());
        this.setDropItself(false);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        BrownMushroomShape shape = state.getValue(SHAPE);
        return shape == BrownMushroomShape.BOTTOM || shape == BrownMushroomShape.MIDDLE
                ? new ItemStack(NetherBlocks.MAT_NETHER_MUSHROOM.getStem())
                : new ItemStack(Items.BROWN_MUSHROOM);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        BrownMushroomShape shape = state.getValue(SHAPE);
        if (shape == BrownMushroomShape.TOP)
            return TOP_CENTER_SHAPE;
        else if (shape == BrownMushroomShape.MIDDLE || shape == BrownMushroomShape.BOTTOM)
            return MIDDLE_SHAPE;
        else
            return TOP_EDGE_SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        BrownMushroomShape shape = state.getValue(SHAPE);

        int index = getRotationIndex(shape, ROT_SIDE);
        if (index < 0) {
            index = getRotationIndex(shape, ROT_CORNER);

            if (index < 0) {
                return state;
            }

            int offset = rotOffset(rotation);
            return state.setValue(SHAPE, ROT_CORNER[(index + offset) & 3]);
        }

        int offset = rotOffset(rotation);
        return state.setValue(SHAPE, ROT_SIDE[(index + offset) & 3]);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        BrownMushroomShape shape = state.getValue(SHAPE);
        int index = getRotationIndex(shape, ROT_SIDE);
        if (index < 0) {
            index = getRotationIndex(shape, ROT_CORNER);
            if (index < 0)
                return state;
            if (mirror == Mirror.FRONT_BACK) {
                if (shape == BrownMushroomShape.CORNER_E)
                    shape = BrownMushroomShape.CORNER_W;
                else if (shape == BrownMushroomShape.CORNER_W)
                    shape = BrownMushroomShape.CORNER_E;
            } else if (mirror == Mirror.LEFT_RIGHT) {
                if (shape == BrownMushroomShape.CORNER_N)
                    shape = BrownMushroomShape.CORNER_S;
                else if (shape == BrownMushroomShape.CORNER_S)
                    shape = BrownMushroomShape.CORNER_N;
            }
            return state.setValue(SHAPE, shape);
        }
        if (mirror == Mirror.FRONT_BACK) {
            if (shape == BrownMushroomShape.SIDE_E)
                shape = BrownMushroomShape.SIDE_W;
            else if (shape == BrownMushroomShape.SIDE_W)
                shape = BrownMushroomShape.SIDE_E;
        } else if (mirror == Mirror.LEFT_RIGHT) {
            if (shape == BrownMushroomShape.SIDE_N)
                shape = BrownMushroomShape.SIDE_S;
            else if (shape == BrownMushroomShape.SIDE_S)
                shape = BrownMushroomShape.SIDE_N;
        }
        return state.setValue(SHAPE, shape);
    }

    private int getRotationIndex(BrownMushroomShape shape, BrownMushroomShape[] rotations) {
        for (int i = 0; i < 4; i++) {
            if (shape == rotations[i])
                return i;
        }
        return -1;
    }

    private int rotOffset(Rotation rotation) {
        if (rotation == Rotation.NONE)
            return 0;
        else if (rotation == Rotation.CLOCKWISE_90)
            return 1;
        else if (rotation == Rotation.CLOCKWISE_180)
            return 2;
        else
            return 3;
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
        switch (state.getValue(SHAPE)) {
            case BOTTOM:
                return state;
            case MIDDLE:
            case TOP:
            default:
                return getStateIfSame(state, world, pos.below());
            case SIDE_E:
            case CORNER_E:
                return getStateIfSame(state, world, pos.west());
            case SIDE_N:
            case CORNER_N:
                return getStateIfSame(state, world, pos.south());
            case SIDE_S:
            case CORNER_S:
                return getStateIfSame(state, world, pos.north());
            case SIDE_W:
            case CORNER_W:
                return getStateIfSame(state, world, pos.east());
        }
    }

    private BlockState getStateIfSame(BlockState state, LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == this ? state : Blocks.AIR.defaultBlockState();
    }
}
