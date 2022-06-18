package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.betternether.BN;
import org.betterx.betternether.registry.NetherBlocks;

import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherVines {
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_LUMABUS_VINE = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_lumabus_vine"))
            .direction(Direction.DOWN)
            .addTripleShapeUpsideDown(NetherBlocks.LUMABUS_VINE.defaultBlockState(), ClampedNormalInt.of(10, 3, 3, 21))
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_lumabus_vine"))
            .tries(48)
            .spreadXZ(5)
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_GOLDEN_LUMABUS_VINE = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_golden_lumabus_vine"))
            .direction(Direction.DOWN)
            .addTripleShapeUpsideDown(
                    NetherBlocks.GOLDEN_LUMABUS_VINE.defaultBlockState(),
                    ClampedNormalInt.of(12, 3.3f, 2, 23)
            )
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_golden_lumabus_vine"))
            .tries(64)
            .spreadXZ(6)
            .buildAndRegister();
    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_GOLDEN_VINE = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_golden_vine"))
            .direction(Direction.DOWN)
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

    public static void ensureStaticInitialization() {
    }
}
