package org.betterx.betternether.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class BlockCincinnasite extends BlockBase {
    public BlockCincinnasite() {
        super(FabricBlockSettings.of(Material.METAL)
                                 .mapColor(MaterialColor.COLOR_YELLOW)
                                 .hardness(3F)
                                 .resistance(10F)
                                 .requiresTool()
                                 .sounds(SoundType.METAL));
    }
}
