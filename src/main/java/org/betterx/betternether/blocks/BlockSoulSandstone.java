package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockSoulSandstone extends BlockBase {
    public static final BooleanProperty UP = BooleanProperty.create("up");

    public BlockSoulSandstone() {
        super(FabricBlockSettings.copyOf(Blocks.SANDSTONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(UP);
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
        return state.setValue(UP, world.getBlockState(pos.above()).getBlock() != this);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                   .setValue(UP, ctx.getLevel().getBlockState(ctx.getClickedPos().above()).getBlock() != this);
    }
}