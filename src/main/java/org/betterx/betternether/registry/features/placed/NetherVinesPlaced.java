package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherVines;

import net.minecraft.world.level.levelgen.GenerationStep;

public class NetherVinesPlaced {
    public static final BCLFeature LUMABUS_VINE = NetherVines
            .PATCH_LUMABUS_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(12)
            .onceEvery(2)
            .buildAndRegister();
    public static final BCLFeature GOLDEN_LUMABUS_VINE = NetherVines
            .PATCH_GOLDEN_LUMABUS_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(8)
            .onceEvery(2)
            .buildAndRegister();
    public static final BCLFeature GOLDEN_VINE = NetherVines
            .PATCH_GOLDEN_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature GOLDEN_VINE_SPARSE = NetherVines
            .PATCH_GOLDEN_VINE_SPARSE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(3)
            .buildAndRegister();

    public static final BCLFeature EYE_VINE = NetherVines
            .PATCH_EYE_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature BLACK_VINE = NetherVines
            .PATCH_BLACK_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature BLOOMING_VINE = NetherVines
            .PATCH_BLOOMING_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature TWISTING_VINES = NetherVines
            .PATCH_TWISTING_VINES
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .vanillaNetherGround(12)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature NEON_EQUISETUM = NetherVines
            .PATCH_NEON_EQUISETUM
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(12)
            .onceEvery(2)
            .buildAndRegister();

    public static final BCLFeature WHISPERING_GOURD_VINE = NetherVines
            .PATCH_WHISPERING_GOURD_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
