package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.blockpredicates.BlockPredicates;
import org.betterx.bclib.api.v3.levelgen.features.config.PillarFeatureConfig;
import org.betterx.bclib.api.v3.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v3.levelgen.features.features.PillarFeature;
import org.betterx.bclib.api.v3.levelgen.features.features.TemplateFeature;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.*;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;

import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

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

    public static final BCLConfigureFeature<RubeusTreeFeature, NaturalTreeConfiguration> RUBEUS_TREE = BCLFeatureBuilder
            .start(BN.id("tree_rubeus"), org.betterx.betternether.registry.NetherFeatures.RUBEUS_TREE)
            .configuration(NaturalTreeConfiguration.natural())
            .buildAndRegister();

    public static final BCLConfigureFeature<MushroomFirFeature, NoneFeatureConfiguration> MUSHROOM_FIR = BCLFeatureBuilder
            .start(BN.id("tree_mushroom_fir"), org.betterx.betternether.registry.NetherFeatures.MUSHROOM_FIR)
            .buildAndRegister();

    public static final BCLConfigureFeature<PillarFeature, PillarFeatureConfig> STALAGNATE = BCLFeatureBuilder
            .startPillar(BN.id("stalagnate"), PillarFeatureConfig.KnownTransformers.TRIPLE_SHAPE_FILL)
            .direction(Direction.UP)
            .blockState(NetherBlocks.MAT_STALAGNATE.getTrunk())
            .minHeight(3)
            .maxHeight(64)
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_STALAGNATE = STALAGNATE
            .place()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_stalagnate"))
            .tries(30)
            .spreadXZ(6)
            .buildAndRegister();

    public static final BCLConfigureFeature<PillarFeature, PillarFeatureConfig> STALAGNATE_DOWN = BCLFeatureBuilder
            .startPillar(BN.id("stalagnate_down"), PillarFeatureConfig.KnownTransformers.TRIPLE_SHAPE_FILL)
            .direction(Direction.DOWN)
            .blockState(NetherBlocks.MAT_STALAGNATE.getTrunk())
            .minHeight(3)
            .maxHeight(64)
            .buildAndRegister();

    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> GIANT_MOLD = BCLFeatureBuilder
            .startColumn(BN.id("giant_mold"))
            .direction(Direction.UP)
            .addTripleShape(NetherBlocks.GIANT_MOLD.defaultBlockState(), ClampedNormalInt.of(5, 1.3f, 3, 8))
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_GIANT_MOLD = GIANT_MOLD
            .place()
            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
            .inRandomPatch(BN.id("patch_giant_mold"))
            .tries(30)
            .spreadXZ(6)
            .buildAndRegister();

    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> BIG_RED_MUSHROOM = BCLFeatureBuilder
            .startColumn(BN.id("big_red_mushroom"))
            .direction(Direction.UP)
            .prioritizeTip()
            .addTripleShape(NetherBlocks.RED_LARGE_MUSHROOM.defaultBlockState(), ClampedNormalInt.of(6, 2.1f, 3, 9))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BIG_RED_MUSHROOM = BIG_RED_MUSHROOM
            .place()
            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
            .inRandomPatch(BN.id("patch_big_red_mushroom"))
            .tries(30)
            .spreadXZ(6)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BIG_BROWN_MUSHROOM = BCLFeatureBuilder
            .start(
                    BN.id("temp_big_brown_mushroom"),
                    org.betterx.betternether.registry.NetherFeatures.BIG_BROWN_MUSHROOM
            )
            .inlinePlace()
            .isEmptyAndOn(BlockPredicates.ONLY_MYCELIUM)
            .inRandomPatch(BN.id("patch_big_brown_mushroom"))
            .likeDefaultNetherVegetation()
            .tries(30)
            .spreadXZ(7)
            .buildAndRegister();

    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> OLD_RED_MUSHROOM = BCLFeatureBuilder
            .startWithTemplates(BN.id("old_red_mushroom"))
            .add(BN.id("trees/red_mushroom_01"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/red_mushroom_02"), -0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/red_mushroom_03"), -0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/red_mushroom_04"), -3, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/red_mushroom_05"), -3, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/red_mushroom_06"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/red_mushroom_07"), -4, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();


    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> OLD_BROWN_MUSHROOM = BCLFeatureBuilder
            .startWithTemplates(BN.id("old_brown_mushroom"))
            .add(BN.id("trees/brown_mushroom_02"), -3, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/brown_mushroom_03"), -2, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/brown_mushroom_01"), -2, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/brown_mushroom_04"), -1, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();

    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> BIG_WARPED_TREE = BCLFeatureBuilder
            .startWithTemplates(BN.id("big_warped_tree"))
            .add(BN.id("trees/warped_tree_01"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/warped_tree_02"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/warped_tree_03"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/warped_tree_04"), -1, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("trees/warped_tree_05"), -3, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();

    public static final BCLConfigureFeature<SoulLilyFeature, NoneFeatureConfiguration> SOUL_LILY = BCLFeatureBuilder
            .start(BN.id("soul_lily"), org.betterx.betternether.registry.NetherFeatures.SOUL_LILY)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_SOUL_LILY = SOUL_LILY
            .place()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_soul_lily"))
            .spreadXZ(3)
            .spreadY(3)
            .buildAndRegister();

    public static final BCLConfigureFeature<WartTreeFeature, NaturalTreeConfiguration> WART_TREE = BCLFeatureBuilder
            .start(BN.id("tree_wart"), org.betterx.betternether.registry.NetherFeatures.WART_TREE)
            .configuration(NaturalTreeConfiguration.natural())
            .buildAndRegister();

    public static final BCLConfigureFeature<WillowTreeFeature, NoneFeatureConfiguration> WILLOW_TREE = BCLFeatureBuilder
            .start(BN.id("tree_willow"), org.betterx.betternether.registry.NetherFeatures.WILLOW_TREE)
            .buildAndRegister();

    public static final BCLConfigureFeature<OldWillowTree, NaturalTreeConfiguration> OLD_WILLOW_TREE = BCLFeatureBuilder
            .start(BN.id("tree_old_willow"), org.betterx.betternether.registry.NetherFeatures.OLD_WILLOW_TREE)
            .configuration(NaturalTreeConfiguration.naturalLarge())
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherSakuraFeature, NoneFeatureConfiguration> SAKURA_TREE = BCLFeatureBuilder
            .start(BN.id("tree_sakura"), org.betterx.betternether.registry.NetherFeatures.SAKURA_TREE)
            .buildAndRegister();

    public static final BCLConfigureFeature<AnchorTreeFeature, NoneFeatureConfiguration> ANCHOR_TREE = BCLFeatureBuilder
            .start(BN.id("anchor_tree"), org.betterx.betternether.registry.NetherFeatures.ANCHOR_TREE)
            .buildAndRegister();

    public static final BCLConfigureFeature<AnchorTreeBranchFeature, NoneFeatureConfiguration> ANCHOR_TREE_BRANCH = BCLFeatureBuilder
            .start(BN.id("anchor_tree_branch"), org.betterx.betternether.registry.NetherFeatures.ANCHOR_TREE_BRANCH)
            .buildAndRegister();

    public static final BCLConfigureFeature<AnchorTreeRootFeature, NoneFeatureConfiguration> ANCHOR_TREE_ROOT = BCLFeatureBuilder
            .start(BN.id("anchor_tree_root"), NetherFeatures.ANCHOR_TREE_ROOT)
            .buildAndRegister();


    public static void ensureStaticInitialization() {
    }
}
