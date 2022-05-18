package org.betterx.betternether.blocks;

import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BNNetherBrick extends BlockBase {
    public BNNetherBrick() {
        super(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS));
    }
}