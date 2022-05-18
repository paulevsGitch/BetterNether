package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.betterx.betternether.registry.NetherBlocks;

public class BlockInkBush extends BlockCommonPlant {
    public BlockInkBush() {
        super(MaterialColor.COLOR_BLACK);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(NetherBlocks.INK_BUSH_SEED);
    }
}
