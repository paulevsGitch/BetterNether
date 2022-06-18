package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.features.TemplateFeature;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.betternether.BN;

public class NetherTrees {
    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> CRIMSON_GLOWING_TREE = BCLFeatureBuilder
            .startWithTemplates(BN.id("tree_crimson_glowing"))
            .add(BN.id("trees/crimson_glow_tree_01"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_glow_tree_02"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_glow_tree_03"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_glow_tree_04"), 0, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();


    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> CRIMSON_PINE = BCLFeatureBuilder
            .startWithTemplates(BN.id("tree_crimson_pine"))
            .add(BN.id("trees/crimson_pine_01"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_pine_02"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_pine_03"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_pine_04"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/crimson_pine_05"), 0, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
