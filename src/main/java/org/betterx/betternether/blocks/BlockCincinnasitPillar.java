package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import org.betterx.betternether.blocks.BlockProperties.CincinnasitPillarShape;
import org.betterx.betternether.registry.NetherBlocks;

public class BlockCincinnasitPillar extends BlockBase {
    public static final EnumProperty<CincinnasitPillarShape> SHAPE = BlockProperties.PILLAR_SHAPE;

    public BlockCincinnasitPillar() {
        super(FabricBlockSettings.copy(NetherBlocks.CINCINNASITE_BLOCK));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SHAPE);
    }

    @Override
    public BlockState updateShape(BlockState state,
                                  Direction facing,
                                  BlockState neighborState,
                                  LevelAccessor world,
                                  BlockPos pos,
                                  BlockPos neighborPos) {
        boolean top = world.getBlockState(pos.above()).getBlock() == this;
        boolean bottom = world.getBlockState(pos.below()).getBlock() == this;
        if (top && bottom)
            return state.setValue(SHAPE, CincinnasitPillarShape.MIDDLE);
        else if (top)
            return state.setValue(SHAPE, CincinnasitPillarShape.BOTTOM);
        else if (bottom)
            return state.setValue(SHAPE, CincinnasitPillarShape.TOP);
        else
            return state.setValue(SHAPE, CincinnasitPillarShape.SMALL);
    }
}