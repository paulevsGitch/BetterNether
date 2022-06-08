package org.betterx.betternether.blocks;

import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockCincinnasiteFrame extends BlockBaseNotFull {
    public BlockCincinnasiteFrame() {
        super(FabricBlockSettings.copy(NetherBlocks.CINCINNASITE_BLOCK).noOcclusion());
        this.setRenderLayer(BNRenderLayer.CUTOUT);
    }

    @Environment(EnvType.CLIENT)
    public float getShadeBrightness(BlockState state, BlockGetter view, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter view, BlockPos pos) {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public boolean skipRendering(BlockState state, BlockState neighbor, Direction facing) {
        return neighbor.getBlock() == this || super.skipRendering(state, neighbor, facing);
    }
}
