package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.config.PillarFeatureConfig;
import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.betternether.BN;
import org.betterx.betternether.blocks.BlockNeonEquisetum;
import org.betterx.betternether.blocks.BlockWhisperingGourdVine;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;

import net.minecraft.core.Direction;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class NetherVines {
    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> LUMABUS_VINE = BCLFeatureBuilder
            .startColumn(BN.id("lumabus_vine"))
            .direction(Direction.DOWN)
            .prioritizeTip()
            .addTripleShapeUpsideDown(NetherBlocks.LUMABUS_VINE.defaultBlockState(), ClampedNormalInt.of(10, 3, 3, 21))
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_LUMABUS_VINE = LUMABUS_VINE
            .place()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_lumabus_vine"))
            .tries(48)
            .spreadXZ(5)
            .buildAndRegister();
    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> GOLDEN_LUMABUS_VINE = BCLFeatureBuilder
            .startColumn(BN.id("golden_lumabus_vine"))
            .direction(Direction.DOWN)
            .prioritizeTip()
            .addTripleShapeUpsideDown(
                    NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState(),
                    ClampedNormalInt.of(12, 3.3f, 2, 23)
            )
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_GOLDEN_LUMABUS_VINE = GOLDEN_LUMABUS_VINE
            .place()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_golden_lumabus_vine"))
            .tries(64)
            .spreadXZ(6)
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_GOLDEN_VINE = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_golden_vine"))
            .direction(Direction.DOWN)
            .prioritizeTip()
            .addBottomShapeUpsideDown(
                    NetherBlocks.GOLDEN_VINE.defaultBlockState(),
                    ClampedNormalInt.of(12, 3, 3, 23)
            )
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_golden_vine"))
            .tries(64)
            .spreadXZ(6)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_GOLDEN_VINE_SPARSE = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_golden_vine_sparse"))
            .direction(Direction.DOWN)
            .addBottomShapeUpsideDown(
                    NetherBlocks.GOLDEN_VINE.defaultBlockState(),
                    ClampedNormalInt.of(12, 3, 3, 23)
            )
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_golden_vine_sparse"))
            .tries(32)
            .spreadXZ(6)
            .buildAndRegister();

    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> EYE_VINE = BCLFeatureBuilder
            .startColumn(BN.id("eye_vine"))
            .prioritizeTip()
            .direction(Direction.DOWN)
            .add(ClampedNormalInt.of(6, 3, 3, 16), NetherBlocks.EYE_VINE.defaultBlockState())
            .addRandom(1, NetherBlocks.EYEBALL.defaultBlockState(), NetherBlocks.EYEBALL_SMALL.defaultBlockState())
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_EYE_VINE = EYE_VINE
            .place()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_eye_vine"))
            .tries(48)
            .spreadXZ(5)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BLACK_VINE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_black_vine"), PillarFeatureConfig.KnownTransformers.BOTTOM_GROW)
            .direction(Direction.DOWN)
            .blockState(NetherBlocks.BLACK_VINE)
            .minHeight(3)
            .maxHeight(ClampedNormalInt.of(12, 2.3f, 3, 16))
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_black_vine"))
            .tries(24)
            .spreadXZ(5)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_TWISTING_VINES = BCLFeatureBuilder
            .start(BN.id("temp_patch_twisting_vine"), NetherFeatures.TWISTING_VINES)
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .inRandomPatch(BN.id("patch_twisting_vine"))
            .tries(10)
            .spreadXZ(5)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_BLOOMING_VINE = BCLFeatureBuilder
            .startPillar(BN.id("temp_patch_blooming_vine"), PillarFeatureConfig.KnownTransformers.BOTTOM_GROW)
            .direction(Direction.DOWN)
            .blockState(NetherBlocks.BLOOMING_VINE)
            .minHeight(3)
            .maxHeight(ClampedNormalInt.of(14, 2, 3, 21))
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_blooming_vine"))
            .tries(32)
            .spreadXZ(5)
            .buildAndRegister();

    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> NEON_EQUISETUM = BCLFeatureBuilder
            .startColumn(BN.id("neon_equisetum"))
            .direction(Direction.DOWN)
            .add(
                    BiasedToBottomInt.of(1, 21),
                    NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(
                            BlockNeonEquisetum.SHAPE,
                            BlockProperties.TripleShape.TOP
                    )
            )
            .add(
                    1,
                    NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(
                            BlockNeonEquisetum.SHAPE,
                            BlockProperties.TripleShape.MIDDLE
                    )
            )
            .add(
                    1,
                    NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(
                            BlockNeonEquisetum.SHAPE,
                            BlockProperties.TripleShape.BOTTOM
                    )
            )
            .prioritizeTip()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_NEON_EQUISETUM = NEON_EQUISETUM
            .place()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_neon_equisetum"))
            .tries(32)
            .spreadXZ(5)
            .buildAndRegister();

    public static final BCLConfigureFeature<BlockColumnFeature, BlockColumnConfiguration> WHISPERING_GOURD_VINE = BCLFeatureBuilder
            .startColumn(BN.id("whispering_gourd_vine"))
            .direction(Direction.DOWN)
            .add(1, NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState().setValue(
                    BlockWhisperingGourdVine.SHAPE,
                    BlockProperties.TripleShape.BOTTOM
            ))
            .add(
                    BiasedToBottomInt.of(1, 5),
                    new WeightedStateProvider(SimpleWeightedRandomList
                            .<BlockState>builder()
                            .add(NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                                   .setValue(
                                                                           BlockWhisperingGourdVine.SHAPE,
                                                                           BlockProperties.TripleShape.TOP
                                                                   ), 5)
                            .add(NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                                   .setValue(
                                                                           BlockWhisperingGourdVine.SHAPE,
                                                                           BlockProperties.TripleShape.MIDDLE
                                                                   ), 5))
            )
            .prioritizeTip()
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_WHISPERING_GOURD_VINE = WHISPERING_GOURD_VINE
            .place()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_whispering_gourd_vine"))
            .tries(16)
            .spreadXZ(3)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
