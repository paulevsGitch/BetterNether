package org.betterx.betternether.registry.features;

import org.betterx.bclib.BCLib;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.betternether.world.features.JellyfishMushroomFeature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class NetherFeatures {
    public static final Feature<NoneFeatureConfiguration> JELLYFISH_MUSHROOM = BCLFeature.register(
            BCLib.makeID("jellyfish_mushroom"),
            new JellyfishMushroomFeature()
    );
}
