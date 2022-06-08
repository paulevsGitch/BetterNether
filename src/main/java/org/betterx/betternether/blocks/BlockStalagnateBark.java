package org.betterx.betternether.blocks;

import org.betterx.betternether.blocks.materials.Materials;

import net.minecraft.world.level.material.MaterialColor;

public class BlockStalagnateBark extends BNPillar {
    public BlockStalagnateBark() {
        super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
    }
}