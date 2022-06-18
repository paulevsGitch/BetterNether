package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v2.levelgen.features.FastFeatures;
import org.betterx.bclib.api.v2.levelgen.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v2.levelgen.structures.StructureWorldNBT;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.features.*;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import java.util.List;

public class TreeFeatures {
    public static final BCLFeature WART_DEADWOOD = BCLFeatureBuilder
            .start(BetterNether.makeID("wart_deadwood"), BCLFeature.TEMPLATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .offset(Direction.DOWN)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .offset(Direction.UP)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("trees/wart_root_01"), -0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/wart_root_02"), -0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/wart_root_03"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/wart_fallen_log"), 0, StructurePlacementType.FLOOR, 1.0f)
            )));

    public static final BCLFeature OLD_RED_MUSHROOM = BCLFeatureBuilder
            .start(BetterNether.makeID("old_red_mushroom"), BCLFeature.TEMPLATE)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .offset(Direction.DOWN)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("trees/red_mushroom_02"), -0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/red_mushroom_03"), -0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/red_mushroom_01"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/red_mushroom_04"), -3, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/red_mushroom_05"), -3, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/red_mushroom_06"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/red_mushroom_07"), -4, StructurePlacementType.FLOOR, 1.0f)
            )));

    public static final BCLFeature OLD_RED_MUSHROOM_CLUSTER = BCLFeatureBuilder
            .start(BetterNether.makeID("old_red_mushroom_cluster"), OLD_RED_MUSHROOM.getFeature())
            .countRange(2, 4)
            .spreadHorizontal(UniformInt.of(-16, 16))
            .findSolidFloor(3)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.MYCELIUM))
            .buildAndRegister(OLD_RED_MUSHROOM.getConfiguration());

    public static final BCLFeature OLD_BROWN_MUSHROOM = BCLFeatureBuilder
            .start(BetterNether.makeID("old_brown_mushroom"), BCLFeature.TEMPLATE)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .offset(Direction.DOWN)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("trees/brown_mushroom_02"), -3, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/brown_mushroom_03"), -2, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/brown_mushroom_01"), -2, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/brown_mushroom_04"), -1, StructurePlacementType.FLOOR, 1.0f)

            )));

    public static final BCLFeature OLD_BROWN_MUSHROOM_CLUSTER = BCLFeatureBuilder
            .start(BetterNether.makeID("old_brown_mushroom_cluster"), OLD_BROWN_MUSHROOM.getFeature())
            .countRange(2, 4)
            .spreadHorizontal(UniformInt.of(-16, 16))
            .findSolidFloor(3)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.MYCELIUM))
            .buildAndRegister(OLD_BROWN_MUSHROOM.getConfiguration());

    public static final BCLFeature BIG_WARPED_TREE = BCLFeatureBuilder
            .start(BetterNether.makeID("big_warped_tree"), BCLFeature.TEMPLATE)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .offset(Direction.DOWN)
            .buildAndRegister(new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("trees/warped_tree_01"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/warped_tree_02"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/warped_tree_03"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/warped_tree_04"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("trees/warped_tree_05"), -3, StructurePlacementType.FLOOR, 1.0f)
            )));


    public static final BCLFeature BIG_WARPED_TREE_CLUSTER = BCLFeatureBuilder
            .start(BetterNether.makeID("big_warped_tree_cluster"), BIG_WARPED_TREE.getFeature())
            .countRange(2, 4)
            .spreadHorizontal(UniformInt.of(-16, 16))
            .findSolidFloor(3)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .buildAndRegister(BIG_WARPED_TREE.getConfiguration());

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
            .spreadHorizontal(UniformInt.of(-12, 12))
            .findSolidFloor(3)
            .isAbove(BlockPredicate.matchesTag(CommonBlockTags.MYCELIUM))
            .buildAndRegister();

    public static final BCLFeature MUSHROOM_FIR
            = FastFeatures.patch(
            BetterNether.makeID("mushroom_fir"),
            2,
            2,
            4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("mushroom_fir_single"), new MushroomFirFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.MYCELIUM))
                    .buildAndRegister()
    );

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
            = FastFeatures.patch(
            BetterNether.makeID("rubeus_tree"),
            2,
            2,
            4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("rubeus_tree"), new RubeusTreeFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister(NaturalTreeConfiguration.natural())
    );

    public static final BCLFeature WILLOW
            = FastFeatures.patch(BetterNether.makeID("willow_tree"),
            2, 2, 4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("willow_tree"), new WillowTreeFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister()
    );

    public static final BCLFeature OLD_WILLOW
            = FastFeatures.patch(BetterNether.makeID("old_willow_tree"),
            4, 8, 4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("old_willow_tree"), new OldWillowTree())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister(NaturalTreeConfiguration.naturalLarge())
    );

    public static final BCLFeature WART
            = FastFeatures.patch(BetterNether.makeID("wart_tree"),
            32, 7, 3,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("wart_tree"), new WartTreeFeature())
                    .isAbove(BlockPredicate.matchesBlocks(Blocks.SOUL_SAND))
                    .buildAndRegister(NaturalTreeConfiguration.natural())
    );

    public static final BCLFeature SOUL_LILY
            = FastFeatures.patch(BetterNether.makeID("soul_lily"),
            16, 3, 3,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("soul_lily"), new SoulLilyFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.SOUL_GROUND))
                    .buildAndRegister()
    );

    public static final BCLFeature ANCHOR_TREE
            = FastFeatures.patch(BetterNether.makeID("anchor_tree"),
            2, 16, 8,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("anchor_tree"), new AnchorTreeFeature())
                    .onceEvery(14)
                    .findSolidCeil(4)
                    .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
                    .isUnder(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister()
    );

    public static final BCLFeature ANCHOR_TREE_ROOT = BCLFeatureBuilder
            .start(BetterNether.makeID("anchor_tree_root"), new AnchorTreeRootFeature())
            .onceEvery(4)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isUnder(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .buildAndRegister();

    public static final BCLFeature ANCHOR_TREE_BRANCH = BCLFeatureBuilder
            .start(BetterNether.makeID("anchor_tree_branch"), new AnchorTreeBranchFeature())
            .onceEvery(8)
            .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
            .isUnder(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .buildAndRegister();

    public static final BCLFeature NETHER_SAKURA
            = FastFeatures.patch(BetterNether.makeID("nether_sakura"),
            3, 16, 4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("nether_sakura"), new NetherSakuraFeature())
                    .onceEvery(3)
                    .is(BlockPredicate.ONLY_IN_AIR_PREDICATE)
                    .isUnder(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister()
    );

    static StructureWorldNBT cfg(
            ResourceLocation location,
            int offsetY,
            StructurePlacementType type,
            float chance
    ) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }

    public static void ensureStaticInitialization() {
    }
}
