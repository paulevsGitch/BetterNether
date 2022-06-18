package org.betterx.betternether.registry.features.configured;

import org.betterx.bclib.api.v3.levelgen.features.BCLConfigureFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder;
import org.betterx.betternether.BN;
import org.betterx.betternether.BetterNether;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class NetherTerrain {
    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> MAGMA_BLOBS = BCLFeatureBuilder
            .start(BetterNether.makeID("magma_block"), Blocks.MAGMA_BLOCK)
            .buildAndRegister();
    public static final BCLConfigureFeature<SimpleBlockFeature, SimpleBlockConfiguration> LAVA_PITS = BCLFeatureBuilder
            .start(BN.id("lava_pit"), Blocks.LAVA)
            .buildAndRegister();


    public static void ensureStaticInitialization() {
    }
}
