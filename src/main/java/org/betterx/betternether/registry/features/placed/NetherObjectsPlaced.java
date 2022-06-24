package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v2.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.features.SequenceFeature;
import org.betterx.bclib.api.v2.levelgen.features.features.TemplateFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherObjects;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherObjectsPlaced {
    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> BONES = NetherObjects
            .BONES
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration> OBSIDIAN_CRYSTAL = NetherObjects
            .OBSIDIAN_CRYSTAL
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<SequenceFeature, SequenceFeatureConfig> BONE_STALACMITE = NetherObjects
            .PATCH_BONE_STALAGMITE_ON_GROUND
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> STALACTITE = NetherObjects
            .PATCH_STALACTITE
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .betterNetherCeiling(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> STALAGMITE = NetherObjects
            .PATCH_STALAGMITE
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BASALT_STALACTITE = NetherObjects
            .PATCH_BASALT_STALACTITE
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .betterNetherCeiling(8)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BASALT_STALAGMITE = NetherObjects
            .PATCH_BASALT_STALAGMITE
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(1)
            .buildAndRegister();

    public static final BCLFeature<SequenceFeature, SequenceFeatureConfig> BLACKSTONE_STALACTITE = NetherObjects
            .PATCH_BLACKSTONE_STALACTITE_ON_CEIL
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .betterNetherCeiling(4)
            .buildAndRegister();

    public static final BCLFeature<SequenceFeature, SequenceFeatureConfig> BLACKSTONE_STALAGMITE = NetherObjects
            .PATCH_BLACKSTONE_STALAGMITE_ON_GROUND
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static void ensureStaticInitialization() {

    }
}
