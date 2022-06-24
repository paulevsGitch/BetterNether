package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherVines;

import net.minecraft.world.level.levelgen.GenerationStep;

public class NetherVinesPlaced {
    public static final BCLFeature LUMABUS_VINE = NetherVines
            .PATCH_LUMABUS_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();
    public static final BCLFeature GOLDEN_LUMABUS_VINE = NetherVines
            .PATCH_GOLDEN_LUMABUS_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();
    public static final BCLFeature GOLDEN_VINE = NetherVines
            .PATCH_GOLDEN_VINE
            .place()
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .betterNetherCeiling(4)
            .onceEvery(2)
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
