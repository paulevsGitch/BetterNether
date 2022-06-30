package org.betterx.betternether.blocks;

import org.betterx.betternether.interfaces.SurvivesOnNetherGround;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BlockInkBush extends BlockCommonPlant implements SurvivesOnNetherGround {
    public BlockInkBush() {
        super(MaterialColor.COLOR_BLACK);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(NetherBlocks.INK_BUSH_SEED);
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canSurviveOnTop(world, pos);
    }
}
