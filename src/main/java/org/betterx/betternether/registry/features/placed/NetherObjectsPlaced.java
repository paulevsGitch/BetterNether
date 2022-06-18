package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v2.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.features.SequenceFeature;
import org.betterx.bclib.api.v2.levelgen.features.features.TemplateFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherObjects;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherObjectsPlaced {
    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> BONES = NetherObjects
            .BONES
            .place()
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration> OBSIDIAN_CRYSTAL = NetherObjects
            .OBSIDIAN_CRYSTAL
            .place()
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<SequenceFeature, SequenceFeatureConfig> BONE_STALACMITE = NetherObjects
            .PATCH_BONE_STALAGMITE_ON_GROUND
            .place()
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> STALACTITE = NetherObjects
            .PATCH_STALACTITE
            .place()
            .betterNetherCeiling(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> STALAGMITE = NetherObjects
            .PATCH_STALAGMITE
            .place()
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static void ensureStaticInitialization() {

    }
}
