package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherVegetation;

import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherVegetationPlaced {
    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_BONE_REEF = NetherVegetation
            .VEGETATION_BONE_REEF
            .place()
            .vanillaNetherGround(8)
            .buildAndRegister();

    public static final BCLFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> VEGETATION_SULFURIC_BONE_REEF = NetherVegetation
            .VEGETATION_SULFURIC_BONE_REEF
            .place()
            .vanillaNetherGround(8)
            .buildAndRegister();

    public static final BCLFeature VEGETATION_MAGMA_LAND = NetherVegetation
            .VEGETATION_MAGMA_LAND
            .place()
            .betterNetherGround(8)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature VEGETATION_CRIMSON_GLOWING_WOODS = NetherVegetation
            .VEGETATION_CRIMSON_GLOWING_WOODS
            .place()
            .betterNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> JELLYFISH_MUSHROOM = NetherVegetation
            .PATCH_JELLYFISH_MUSHROOM
            .place()
            .vanillaNetherGround(2)
            .onceEvery(4)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> WALL_MUSHROOM_RED_WITH_MOSS = NetherVegetation
            .PATCH_WALL_MUSHROOM_RED_WITH_MOSS
            .place()
            .betterNetherWall(5)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> NETHER_REED = NetherVegetation
            .PATCH_NETHER_REED
            .place()
            .vanillaNetherGround(6)
            .buildAndRegister();


    public static final BCLFeature BLACK_BUSH = NetherVegetation
            .PATCH_BLACK_BUSH
            .place()
            .vanillaNetherGround(6)
            .buildAndRegister();

    public static final BCLFeature WART_BUSH = NetherVegetation
            .PATCH_WART_BUSH
            .place()
            .vanillaNetherGround(4)
            .onceEvery(3)
            .buildAndRegister();


    public static void ensureStaticInitialization() {
    }
}