package org.betterx.betternether.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;

import org.betterx.bclib.blocks.BaseStripableLogBlock;
import org.betterx.bclib.blocks.BlockProperties.TripleShape;

public class RubeusLog extends BaseStripableLogBlock {
    public static final EnumProperty<TripleShape> SHAPE = BNBlockProperties.TRIPLE_SHAPE;

    public RubeusLog(MaterialColor color, Block striped) {
        super(color, striped);
        this.registerDefaultState(this.defaultBlockState()
                                      .setValue(AXIS, Direction.Axis.Y)
                                      .setValue(SHAPE, TripleShape.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(SHAPE);
    }
}