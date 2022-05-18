package org.betterx.betternether.world.structures.plants;

import org.betterx.betternether.blocks.BlockCommonPlant;
import org.betterx.betternether.registry.NetherBlocks;

public class StructureEggPlant extends StructureScatter {
    public StructureEggPlant() {
        super(NetherBlocks.EGG_PLANT, BlockCommonPlant.AGE, 4);
    }
}
