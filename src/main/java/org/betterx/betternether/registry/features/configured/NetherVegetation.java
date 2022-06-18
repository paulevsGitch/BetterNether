package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.BlockPredicates;
import org.betterx.betternether.BN;
import org.betterx.betternether.blocks.BlockFeatherFern;
import org.betterx.betternether.blocks.BlockNetherReed;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.features.NetherFeatures;

import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.NetherForestVegetationFeature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class NetherVegetation {
    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> DEBUG = BCLFeatureBuilder
            .start(BN.id("debug"), Blocks.YELLOW_CONCRETE)
            .buildAndRegister();

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
                    ClampedNormalInt.of(12, 3, 3, 23)
            )
            .inlinePlace()
            .isEmptyAndUnderNetherGround()
            .inRandomPatch(BN.id("patch_golden_lumabus_vine"))
            .tries(64)
            .spreadXZ(6)
            .buildAndRegister();
    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> PATCH_BONE_REEF_VEGETATION = BCLFeatureBuilder
            .startNetherVegetation(BN.id("patch_bone_reef_vegetation"))
            .add(NetherBlocks.BONE_GRASS, 180)
            .addAllStatesFor(BlockFeatherFern.AGE, NetherBlocks.FEATHER_FERN, 20)
            .buildAndRegister();

    public static final BCLConfigureFeature<NetherForestVegetationFeature, NetherForestVegetationConfig> PATCH_SULFURIC_BONE_REEF_VEGETATION = BCLFeatureBuilder
            .startNetherVegetation(BN.id("patch_sulfuric_bone_reef_vegetation"))
            .add(NetherBlocks.SEPIA_BONE_GRASS, 180)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_JELLYFISH_MUSHROOM = BCLFeatureBuilder
            .start(BN.id("temp_jellyfish_mushroom"), NetherFeatures.JELLYFISH_MUSHROOM)
            .inlinePlace()
            .findSolidFloor(4)
            .isEmptyAndOnNylium()
            .inRandomPatch(BN.id("patch_jellyfish_mushroom"))
            .tries(6)
            .spreadXZ(6)
            .spreadY(4)
            .buildAndRegister();

    public static final BCLConfigureFeature<RandomPatchFeature, RandomPatchConfiguration> PATCH_NETHER_REED = BCLFeatureBuilder
            .startColumn(BN.id("temp_patch_nether_reed"))
            .direction(Direction.UP)
            .prioritizeTip()
            .add(
                    BiasedToBottomInt.of(0, 3),
                    NetherBlocks.MAT_REED.getStem().defaultBlockState().setValue(BlockNetherReed.TOP, false)
            )
            .add(1, NetherBlocks.MAT_REED.getStem().defaultBlockState().setValue(BlockNetherReed.TOP, true))
            .inlinePlace()
            .isEmptyAndOnNetherGround()
            .belowIsNextTo(BlockPredicates.ONLY_LAVA)
            .inRandomPatch(BN.id("patch_nether_reed"))
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
