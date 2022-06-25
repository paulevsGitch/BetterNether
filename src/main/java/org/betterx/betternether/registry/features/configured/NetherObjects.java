package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v2.levelgen.features.config.PillarFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.features.SequenceFeature;
import org.betterx.bclib.api.v2.levelgen.features.features.TemplateFeature;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.BlockPredicates;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.features.NetherFeatures;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NetherObjects {

    public static final BCLConfigureFeature<Feature<NoneFeatureConfiguration>, NoneFeatureConfiguration> OBSIDIAN_CRYSTAL = BCLFeatureBuilder
            .start(BN.id("obsidian_crystal"), NetherFeatures.OBSIDIAN_CRYSTAL)
            .buildAndRegister();
    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> BONES = BCLFeatureBuilder
            .startWithTemplates(BN.id("bones"))
            .add(BN.id("bone_01"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("bone_02"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("bone_03"), 0, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();

    public static final BCLConfigureFeature<TemplateFeature<TemplateFeatureConfig>, TemplateFeatureConfig> JUNGLE_BONES = BCLFeatureBuilder
            .startWithTemplates(BN.id("jungle_bones"))
            .add(BN.id("jungle_bones_3"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("jungle_bones_2"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("jungle_bones_1"), 0, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();

    public static final Holder<PlacedFeature> PATCH_BONE_CLUMP = BCLFeatureBuilder
            .start(BN.id("temp_bone_clump"), NetherBlocks.BONE_BLOCK)
            .inlinePlace()
            .extendXYZ(3, 3)
            .is(BlockPredicates.ONLY_NETHER_GROUND)
            .build();

    public static final Holder<PlacedFeature> PATCH_TERRACOTTA_CLUMP = BCLFeatureBuilder
            .start(BN.id("temp_terracotta_clump"), Blocks.BLACK_GLAZED_TERRACOTTA)
            .inlinePlace()
            .extendXYZ(3, 3)
            .is(BlockPredicate.matchesBlocks(Blocks.BASALT))
            .build();

    public static final Holder<PlacedFeature> PATCH_BONE_STALAGMITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_bone_stalagmite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.UP)
            .blockState(NetherBlocks.BONE_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(2, 7))
            .inlinePlace()
            .isEmpty()
            .isOn(BlockPredicate.matchesBlocks(NetherBlocks.BONE_BLOCK, Blocks.BONE_BLOCK))
            .inRandomPatch(BN.id("patch_bone_stalagmite"))
            .spreadXZ(4)
            .inlinePlace()
            .build();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_STALACTITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_stalactite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.DOWN)
            .blockState(NetherBlocks.NETHERRACK_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(2, 7))
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_stalactite"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_STALAGMITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_stalagmite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.UP)
            .blockState(NetherBlocks.NETHERRACK_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(2, 7))
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_stalagmite"))
            .buildAndRegister();

    public static final Holder<PlacedFeature> PATCH_BLACKSTONE_STALACTITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_blackstone_stalactite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.DOWN)
            .blockState(NetherBlocks.BLACKSTONE_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(3, 9))
            .inlinePlace()
            .isOn(BlockPredicate.matchesBlocks(Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BASALT))
            .inRandomPatch(BN.id("patch_blackstone_stalactite"))
            .inlinePlace()
            .build();

    public static final Holder<PlacedFeature> PATCH_BLACKSTONE_STALAGMITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_blackstone_stalagmite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.UP)
            .blockState(NetherBlocks.BLACKSTONE_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(3, 8))
            .inlinePlace()
            .isOn(BlockPredicate.matchesBlocks(Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BASALT))
            .inRandomPatch(BN.id("patch_blackstone_stalagmite"))
            .inlinePlace()
            .build();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BASALT_STALACTITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_basalt_stalactite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.DOWN)
            .blockState(NetherBlocks.BASALT_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(4, 11))
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_basalt_stalactite"))
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BASALT_STALAGMITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_basalt_stalagmite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.UP)
            .blockState(NetherBlocks.BASALT_STALACTITE)
            .maxHeight(BiasedToBottomInt.of(3, 9))
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_basalt_stalagmite"))
            .buildAndRegister();


    public static final BCLConfigureFeature<SequenceFeature, SequenceFeatureConfig> PATCH_BONE_STALAGMITE_ON_GROUND = BCLFeatureBuilder
            .startSequence(BN.id("patch_bone_stalagmite_on_ground"))
            .add(PATCH_BONE_CLUMP)
            .add(PATCH_BONE_STALAGMITE)
            .buildAndRegister();

    public static final BCLConfigureFeature<SequenceFeature, SequenceFeatureConfig> PATCH_BLACKSTONE_STALAGMITE_ON_GROUND = BCLFeatureBuilder
            .startSequence(BN.id("patch_blackstone_stalagmite_on_ground"))
            .add(PATCH_TERRACOTTA_CLUMP)
            .add(PATCH_BLACKSTONE_STALAGMITE)
            .buildAndRegister();

    public static final BCLConfigureFeature<SequenceFeature, SequenceFeatureConfig> PATCH_BLACKSTONE_STALACTITE_ON_CEIL = BCLFeatureBuilder
            .startSequence(BN.id("patch_blackstone_stalactite_on_ceil"))
            .add(PATCH_TERRACOTTA_CLUMP)
            .add(PATCH_BLACKSTONE_STALACTITE)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_SMOKER = BCLFeatureBuilder
            .startColumn(BN.id("temp_smoker"))
            .direction(Direction.UP)
            .addTripleShape(NetherBlocks.SMOKER.defaultBlockState(), BiasedToBottomInt.of(0, 4))
            .prioritizeTip()
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_smoker"))
            .tries(18)
            .spreadXZ(4)
            .spreadY(3)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
