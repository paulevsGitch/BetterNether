package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.betterx.betternether.blocks.BlockProperties.TripleShape;
import org.betterx.betternether.blocks.materials.Materials;

public class BlockWillowTrunk extends BlockBaseNotFull {
    public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
    private static final VoxelShape SHAPE_BOTTOM = box(4, 0, 4, 12, 16, 12);
    private static final VoxelShape SHAPE_TOP = box(4, 0, 4, 12, 12, 12);

    public BlockWillowTrunk() {
        super(Materials.makeWood(MaterialColor.TERRACOTTA_RED).noOcclusion());
        this.setDropItself(false);
        this.registerDefaultState(getStateDefinition().any().setValue(SHAPE, TripleShape.TOP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter view, BlockPos pos, CollisionContext ePos) {
        return state.getValue(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
    }
}
