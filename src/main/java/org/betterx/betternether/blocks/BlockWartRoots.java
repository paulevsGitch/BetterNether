package org.betterx.betternether.blocks;

import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockWartRoots extends BlockBase {
    public BlockWartRoots() {
        super(FabricBlockSettings.copy(Blocks.NETHER_WART_BLOCK));
        this.setDropItself(false);
    }
}
