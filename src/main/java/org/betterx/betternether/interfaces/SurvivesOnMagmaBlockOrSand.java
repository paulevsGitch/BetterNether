package org.betterx.betternether.interfaces;

import org.betterx.bclib.interfaces.SurvivesOnBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public interface SurvivesOnMagmaBlockOrSand extends SurvivesOnBlocks {
    List<Block> GROUND = List.of(Blocks.MAGMA_BLOCK, Blocks.RED_SAND, Blocks.SAND, Blocks.SCULK);

    @Override
    default List<Block> getSurvivableBlocks() {
        return GROUND;
    }
}

