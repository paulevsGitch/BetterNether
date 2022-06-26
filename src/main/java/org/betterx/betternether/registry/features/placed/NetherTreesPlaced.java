package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.features.TemplateFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v3.levelgen.features.BlockPredicates;
import org.betterx.betternether.registry.features.configured.NetherTrees;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherTreesPlaced {
    public static BCLFeature CRIMSON_GLOWING_TREE = NetherTrees
            .CRIMSON_GLOWING_TREE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static BCLFeature CRIMSON_PINE = NetherTrees
            .CRIMSON_PINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static BCLFeature RUBEUS_TREE = NetherTrees
            .RUBEUS_TREE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static BCLFeature MUSHROOM_FIR = NetherTrees
            .MUSHROOM_FIR
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(14)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> STALAGNATE = NetherTrees
            .PATCH_STALAGNATE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .onceEvery(5)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> GIANT_MOLD = NetherTrees
            .PATCH_GIANT_MOLD
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .onceEvery(5)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BIG_RED_MUSHROOM = NetherTrees
            .PATCH_BIG_RED_MUSHROOM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .onceEvery(2)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<RandomPatchFeature, RandomPatchConfiguration> BIG_BROWN_MUSHROOM = NetherTrees
            .PATCH_BIG_BROWN_MUSHROOM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(6)
            .onceEvery(2)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> OLD_RED_MUSHROOM = NetherTrees
            .OLD_RED_MUSHROOM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(5)
            .onceEvery(3)
            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
            .buildAndRegister();

    public static final BCLFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> OLD_BROWN_MUSHROOM = NetherTrees
            .OLD_BROWN_MUSHROOM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(4)
            .onceEvery(3)
            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
