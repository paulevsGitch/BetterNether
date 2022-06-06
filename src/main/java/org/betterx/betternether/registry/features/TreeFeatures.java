package org.betterx.betternether.registry.features;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.api.features.FastFeatures;
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.tag.CommonBlockTags;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.features.BigBrownMushroomFeature;
import org.betterx.betternether.world.features.MushroomFirFeature;
import org.betterx.betternether.world.features.RubeusTreeFeature;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;

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

    //TODO: 1.19 make sure we are placed over mycelium
    public static final BCLFeature BIG_RED_MUSHROOM_CLUSTER
            = FastFeatures.vine(
            BetterNether.makeID("big_red_mushroom_cluster"), true, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .tripleShape(NetherBlocks.RED_LARGE_MUSHROOM)
                    .heightRange(2, 7)
                    .growWhileFree()
                    .spread(2, 0.9f, ClampedNormalInt.of(8, 1.3f, 3, 14))
    );

    public static final BCLFeature BIG_BROWN_MUSHROOM
            = FastFeatures.simple(BetterNether.makeID("big_brown_mushroom"), new BigBrownMushroomFeature());


    public static final BCLFeature BIG_BROWN_MUSHROOM_CLUSTER = BCLFeatureBuilder
            .start(BetterNether.makeID("big_brown_mushroom_cluster"), BIG_BROWN_MUSHROOM.getFeature())
            .count(10)
            .spreadHorizontal(UniformInt.of(8, 12))
            .findSolidFloor(3)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.MYCELIUM))
            .buildAndRegister();

    public static final BCLFeature MUSHROOM_FIR
            = FastFeatures.patch(BetterNether.makeID("mushroom_fir"),
            2,
            2,
            4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("mushroom_fir_single"), new MushroomFirFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.MYCELIUM))
                    .buildAndRegister());

    //TODO: 1.19 make sure we are placed over mycelium
    public static final BCLFeature GIANT_MOLD
            = FastFeatures.vine(
            BetterNether.makeID("giant_mold"), true, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .tripleShape(NetherBlocks.GIANT_MOLD)
                    .heightRange(2, 6)
                    .spread(0, 0)
    );

    public static final BCLFeature RUBEUS
            = FastFeatures.patch(BetterNether.makeID("rubeus"),
            2,
            2,
            4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("rubeus"), new RubeusTreeFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister(NaturalTreeConfiguration.natural()));


    static StructureWorldNBT cfg(ResourceLocation location,
                                 int offsetY,
                                 StructurePlacementType type,
                                 float chance) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }

    public static void ensureStaticInitialization() {
    }
}
