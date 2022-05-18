package org.betterx.betternether.world.structures.plants;

import org.betterx.betternether.blocks.BlockCommonPlant;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.structures.IStructure;

public class StructureFeatherFern extends StructureScatter implements IStructure {
    public StructureFeatherFern() {
        super(NetherBlocks.FEATHER_FERN, BlockCommonPlant.AGE, 4);
    }
}