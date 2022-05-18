package org.betterx.betternether.blocks;

import net.minecraft.world.level.material.MaterialColor;

import org.betterx.betternether.blocks.materials.Materials;

public class BlockStalagnateBark extends BNPillar {
    public BlockStalagnateBark() {
        super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
    }
}