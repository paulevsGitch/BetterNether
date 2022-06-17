package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherVegetation;

import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;

public class NetherVegetationPlaced {
    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> BONE_GRASS = NetherVegetation
            .PATCH_BONE_GRASS
            .place()
            .vanillaNetherGround(6)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
