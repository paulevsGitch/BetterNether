package org.betterx.betternether.blocks;

import org.betterx.betternether.blocks.materials.Materials;

import net.minecraft.world.level.material.MaterialColor;

public class BlockReedsBlock extends BNPillar {
    public BlockReedsBlock() {
        super(Materials.makeWood(MaterialColor.COLOR_CYAN));
    }
}
