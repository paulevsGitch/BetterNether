package org.betterx.betternether.blocks.materials;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;

public class Materials {
    public static final Material NETHER_GRASS = new FabricMaterialBuilder(MaterialColor.GRASS).build();
    public static final Material NETHER_SAPLING = new FabricMaterialBuilder(MaterialColor.PLANT).destroyedByPiston()
                                                                                                .noCollider()
                                                                                                .build();
    public static final Material NETHER_PLANT = Material.PLANT;

    public static FabricBlockSettings makeWood(MaterialColor color) {
        return FabricBlockSettings.of(Material.NETHER_WOOD)
                                  .requiresTool()
                                  .mapColor(color)
                                  .sounds(SoundType.WOOD)
                                  .hardness(1);
    }

    public static FabricBlockSettings makeGrass(MaterialColor color) {
        return FabricBlockSettings.of(NETHER_GRASS)
                                  .allowsSpawning((state, world, pos, type) -> true)
                                  .sounds(SoundType.GRASS)
                                  .mapColor(color)
                                  .noCollision()
                                  .nonOpaque()
                                  .breakInstantly();
    }
}
