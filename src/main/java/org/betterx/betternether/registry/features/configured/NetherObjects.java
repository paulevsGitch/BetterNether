package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v2.levelgen.features.config.PillarFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.SequenceFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.v2.levelgen.features.features.SequenceFeature;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.BlockPredicates;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class NetherObjects {
    public static final BCLConfigureFeature<Feature<TemplateFeatureConfig>, TemplateFeatureConfig> BONES = BCLFeatureBuilder
            .startWithTemplates(BN.id("bones"))
            .add(BN.id("bone_01"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("bone_02"), 0, StructurePlacementType.FLOOR, 1.0f)
            .add(BN.id("bone_03"), 0, StructurePlacementType.FLOOR, 1.0f)
            .buildAndRegister();

    public static final Holder<PlacedFeature> PATCH_BONE_CLUMP = BCLFeatureBuilder
            .start(BN.id("temp_bone_clump"), NetherBlocks.BONE_BLOCK)
            .inlinePlace()
            .extendXYZ(3, 3)
            .is(BlockPredicates.ONLY_NETHER_GROUND)
            .build();

    public static final Holder<PlacedFeature> PATCH_BONE_STALAGMITE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_bone_stalagmite"), PillarFeatureConfig.KnownTransformers.SIZE_DECREASE)
            .direction(Direction.UP)
            .blockState(NetherBlocks.BONE_STALACTITE)
            .height(BiasedToBottomInt.of(2, 7))
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
            .height(BiasedToBottomInt.of(2, 7))
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_stalactite"))
            .buildAndRegister();


    public static final BCLConfigureFeature<SequenceFeature, SequenceFeatureConfig> PATCH_BONE_STALAGMITE_ON_GROUND = BCLFeatureBuilder
            .startSequence(BN.id("patch_bone_stalagmite_on_ground"))
            .add(PATCH_BONE_CLUMP)
            .add(PATCH_BONE_STALAGMITE)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
