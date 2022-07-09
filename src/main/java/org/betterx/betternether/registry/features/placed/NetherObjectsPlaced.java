package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v3.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v3.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v3.levelgen.features.features.SequenceFeature;
import org.betterx.bclib.api.v3.levelgen.features.features.TemplateFeature;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.features.configured.NetherObjects;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class NetherObjectsPlaced {
    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> BONES = NetherObjects
            .BONES
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(3)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> JUNGLE_BONES = NetherObjects
            .JUNGLE_BONES
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(4)
            .buildAndRegister();

    public static final BCLFeature<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration> OBSIDIAN_CRYSTAL = NetherObjects
            .OBSIDIAN_CRYSTAL
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<SequenceFeature, SequenceFeatureConfig> BONE_STALAGMITE = NetherObjects
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

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BASALT_STALACTITE_SPARSE = NetherObjects
            .PATCH_BASALT_STALACTITE
            .place(BN.id("patch_basalt_stalactite_sparse"))
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .betterNetherCeiling(1)
            .onceEvery(5)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BASALT_STALAGMITE = NetherObjects
            .PATCH_BASALT_STALAGMITE
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(1)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BASALT_STALAGMITE_SPARSE = NetherObjects
            .PATCH_BASALT_STALAGMITE
            .place(BN.id("patch_basalt_stalagmite_sparse"))
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(1)
            .onceEvery(5)
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

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> SMOKER = NetherObjects
            .PATCH_SMOKER
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(3)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> SMOKER_SPARSE = NetherObjects
            .PATCH_SMOKER
            .place(BN.id("patch_smoker_sparse"))
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(3)
            .onceEvery(4)
            .buildAndRegister();
    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> WART_DEADWOOD = NetherObjects
            .WART_DEADWOOD
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .onceEvery(2)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<SimpleBlockFeature, SimpleBlockConfiguration> SCULK_HIDDEN = NetherObjects
            .SCULK_HIDDEN
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .onceEvery(4)
            .isEmptyAndOn(BlockPredicate.matchesBlocks(Blocks.SCULK))
            .offset(Direction.DOWN)
            .buildAndRegister();

    public static final BCLFeature<SimpleBlockFeature, SimpleBlockConfiguration> SCULK_TOP = NetherObjects
            .SCULK_TOP
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(8)
            .onceEvery(5)
            .isEmptyAndOn(BlockPredicate.matchesBlocks(Blocks.SCULK))
            .buildAndRegister();

    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> FOREST_LITTER = NetherObjects
            .FOREST_LITTER
            .place()
            .decoration(GenerationStep.Decoration.SURFACE_STRUCTURES)
            .vanillaNetherGround(4)
            .onceEvery(2)
            .buildAndRegister();


    public static void ensureStaticInitialization() {

    }
}
