package org.betterx.betternether.blocks;

import org.betterx.betternether.blocks.materials.Materials;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockMushroomFir extends BlockBaseNotFull {
    public static final EnumProperty<MushroomFirShape> SHAPE = EnumProperty.create("shape", MushroomFirShape.class);

    private static final VoxelShape BOTTOM_SHAPE = box(4, 0, 4, 12, 16, 12);
    private static final VoxelShape MIDDLE_SHAPE = box(5, 0, 5, 11, 16, 11);
    private static final VoxelShape TOP_SHAPE = box(6, 0, 6, 10, 16, 10);
    private static final VoxelShape SIDE_BIG_SHAPE = box(0.01, 0.01, 0.01, 15.99, 13, 15.99);
    private static final VoxelShape SIDE_SMALL_N_SHAPE = box(4, 1, 0, 12, 8, 8);
    private static final VoxelShape SIDE_SMALL_S_SHAPE = box(4, 1, 8, 12, 8, 16);
    private static final VoxelShape SIDE_SMALL_E_SHAPE = box(8, 1, 4, 16, 8, 12);
    private static final VoxelShape SIDE_SMALL_W_SHAPE = box(0, 1, 4, 8, 8, 12);
    private static final VoxelShape END_SHAPE = box(0.01, 0, 0.01, 15.99, 15.99, 15.99);

    public BlockMushroomFir() {
        super(Materials.makeWood(MaterialColor.COLOR_CYAN).noOcclusion());
        this.setDropItself(false);
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        switch (state.getValue(SHAPE)) {
            case BOTTOM:
                return BOTTOM_SHAPE;
            case END:
                return END_SHAPE;
            case MIDDLE:
            default:
                return MIDDLE_SHAPE;
            case SIDE_BIG_E:
            case SIDE_BIG_N:
            case SIDE_BIG_S:
            case SIDE_BIG_W:
                return SIDE_BIG_SHAPE;
            case SIDE_SMALL_E:
                return SIDE_SMALL_E_SHAPE;
            case SIDE_SMALL_N:
                return SIDE_SMALL_N_SHAPE;
            case SIDE_SMALL_S:
                return SIDE_SMALL_S_SHAPE;
            case SIDE_SMALL_W:
                return SIDE_SMALL_W_SHAPE;
            case TOP:
                return TOP_SHAPE;
        }
    }

    public enum MushroomFirShape implements StringRepresentable {
        BOTTOM("bottom"), MIDDLE("middle"), TOP("top"), SIDE_BIG_N("side_big_n"), SIDE_BIG_S("side_big_s"), SIDE_BIG_E(
                "side_big_e"), SIDE_BIG_W("side_big_w"), SIDE_SMALL_N("side_small_n"), SIDE_SMALL_S("side_small_s"), SIDE_SMALL_E(
                "side_small_e"), SIDE_SMALL_W("side_small_w"), END("end");

        final String name;

        MushroomFirShape(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        MushroomFirShape shape = state.getValue(SHAPE);
        return shape == MushroomFirShape.BOTTOM || shape == MushroomFirShape.MIDDLE
                ? new ItemStack(NetherBlocks.MAT_MUSHROOM_FIR.getStem())
                : new ItemStack(NetherBlocks.MAT_MUSHROOM_FIR.getSapling());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        final var _shape = state.getValues().get(SHAPE);
        if (_shape == null) return false;
        final MushroomFirShape shape = SHAPE.getValueClass().cast(_shape);

        if (shape == MushroomFirShape.SIDE_BIG_N || shape == MushroomFirShape.SIDE_SMALL_N)
            return world.getBlockState(pos.north()).getBlock() == this;
        else if (shape == MushroomFirShape.SIDE_BIG_S || shape == MushroomFirShape.SIDE_SMALL_S)
            return world.getBlockState(pos.south()).getBlock() == this;
        else if (shape == MushroomFirShape.SIDE_BIG_E || shape == MushroomFirShape.SIDE_SMALL_E)
            return world.getBlockState(pos.east()).getBlock() == this;
        else if (shape == MushroomFirShape.SIDE_BIG_W || shape == MushroomFirShape.SIDE_SMALL_W)
            return world.getBlockState(pos.west()).getBlock() == this;
        BlockState down = world.getBlockState(pos.below());
        return down.getBlock() == this || down.isFaceSturdy(world, pos.below(), Direction.UP);
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
        return canSurvive(state, world, pos) ? state : Blocks.AIR.defaultBlockState();
    }
}