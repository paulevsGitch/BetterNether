package org.betterx.betternether.registry.features;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.api.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.tag.CommonBlockTags;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;

import java.util.List;

public class TreeFeatures {
    public static final BCLFeature CRIMSON_GLOWING = BCLFeatureBuilder
            .start(BetterNether.makeID("crimson_glowing_tree"), BCLFeature.TEMPLATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("trees/crimson_glow_tree_01"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_glow_tree_02"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_glow_tree_03"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_glow_tree_04"), 0, StructurePlacementType.FLOOR, 1.0f)
            )));
    public static final BCLFeature CRIMSON_PINEWOOD = BCLFeatureBuilder
            .start(BetterNether.makeID("crimson_pine_tree"), BCLFeature.TEMPLATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("trees/crimson_pine_01"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_pine_02"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_pine_03"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_pine_04"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/crimson_pine_05"), 0, StructurePlacementType.FLOOR, 1.0f)
            )));

    static StructureWorldNBT cfg(ResourceLocation location,
                                 int offsetY,
                                 StructurePlacementType type,
                                 float chance) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }

    public static void ensureStaticInitialization() {
    }
}
