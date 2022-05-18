package org.betterx.betternether.world.structures.plants;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;

public class StructureNetherWart extends StructureScatter {
    public StructureNetherWart() {
        super(Blocks.NETHER_WART, NetherWartBlock.AGE, 4);
    }
}