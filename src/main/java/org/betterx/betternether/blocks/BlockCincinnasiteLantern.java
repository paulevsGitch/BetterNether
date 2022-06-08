package org.betterx.betternether.blocks;

import org.betterx.betternether.registry.NetherBlocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockCincinnasiteLantern extends BlockBase {
    public BlockCincinnasiteLantern() {
        super(FabricBlockSettings.copyOf(NetherBlocks.CINCINNASITE_BLOCK).luminance(15));
    }
}
