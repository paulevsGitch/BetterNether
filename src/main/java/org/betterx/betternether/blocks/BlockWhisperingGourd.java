package org.betterx.betternether.blocks;

import org.betterx.betternether.blocks.materials.Materials;

import net.minecraft.world.level.material.MaterialColor;

public class BlockWhisperingGourd extends BlockBase {
    public BlockWhisperingGourd() {
        super(Materials.makeWood(MaterialColor.COLOR_BLUE));
    }
}
