package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherVegetation;

import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherVegetationPlaced {
    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> BONE_REEF_VEGETATION = NetherVegetation
            .PATCH_BONE_REEF_VEGETATION
            .place()
            .vanillaNetherGround(8)
            .buildAndRegister();

    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> SULFURIC_BONE_REEF_VEGETATION = NetherVegetation
            .PATCH_SULFURIC_BONE_REEF_VEGETATION
            .place()
            .vanillaNetherGround(8)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> JELLYFISH_MUSHROOM = NetherVegetation
            .PATCH_JELLYFISH_MUSHROOM
            .place()
            .vanillaNetherGround(2)
            .onceEvery(4)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> NETHER_REED = NetherVegetation
            .PATCH_NETHER_REED
            .place()
            .vanillaNetherGround(6)
            .buildAndRegister();
    public static final BCLFeature LUMABUS_VINE = NetherVegetation
            .PATCH_LUMABUS_VINE
            .place()
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature GOLDEN_LUMABUS_VINE = NetherVegetation
            .PATCH_GOLDEN_LUMABUS_VINE
            .place()
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
