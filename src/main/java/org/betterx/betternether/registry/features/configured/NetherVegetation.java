package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.betternether.BN;
import org.betterx.betternether.blocks.BlockFeatherFern;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;

public class NetherVegetation {
//    public static final BCLConfigureFeature<Feature<RandomPatchConfiguration>, RandomPatchConfiguration> PATCH_SOUL_GRASS = BCLFeatureBuilder
//            .start(BN.id("soul_grass"), NetherBlocks.SOUL_GRASS)
//            .inlinePlace()
//            .onlyWhenEmpty()
//            .inRandomPatch(BN.id("patch_soul_grass"))
//            .tries(96)
//            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> PATCH_BONE_GRASS = BCLFeatureBuilder
            .startNetherVegetation(BN.id("patch_bone_grass"))
            .add(NetherBlocks.BONE_GRASS, 60)
            .addAllStatesFor(BlockFeatherFern.AGE, NetherBlocks.FEATHER_FERN, 40)
            .buildAndRegister();
    
    public static void ensureStaticInitialization() {
    }
}
