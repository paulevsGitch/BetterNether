package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.betternether.BN;
import org.betterx.betternether.world.features.*;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NetherFeatures {
    public static final Feature<NoneFeatureConfiguration> JELLYFISH_MUSHROOM = BCLFeature.register(
            BN.id("jellyfish_mushroom"),
            new JellyfishMushroomFeature()
    );

    public static final Feature<NoneFeatureConfiguration> OBSIDIAN_CRYSTAL = BCLFeature.register(
            BN.id("obsidian_crystal"),
            new CrystalFeature()
    );

    public static final Feature<NoneFeatureConfiguration> WART_BUSH = BCLFeature.register(
            BN.id("wart_bush"),
            new WartBushFeature()
    );

    public static final RubeusTreeFeature RUBEUS_TREE = BCLFeature.register(
            BN.id("rubeus_tree"),
            new RubeusTreeFeature()
    );

    public static final MushroomFirFeature MUSHROOM_FIR = BCLFeature.register(
            BN.id("mushroom_fir"),
            new MushroomFirFeature()
    );

    public static final BigBrownMushroomFeature BIG_BROWN_MUSHROOM = BCLFeature.register(
            BN.id("big_brown_mushroom"),
            new BigBrownMushroomFeature()
    );

    public static final Feature<NoneFeatureConfiguration> RUBEUS_BUSH = BCLFeature.register(
            BN.id("rubeus_bush"),
            new RubeusBushFeature()
    );

    public static final Feature<NoneFeatureConfiguration> LUCIS = BCLFeature.register(
            BN.id("lucis"),
            new LucisFeature()
    );

    public static void ensureStaticInitialization() {
    }
}
