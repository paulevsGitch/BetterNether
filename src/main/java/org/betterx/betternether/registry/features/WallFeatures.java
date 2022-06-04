package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.FastFeatures;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;

public class WallFeatures {

    public static final BCLFeature WALL_MOSS
            = FastFeatures.patch(BetterNether.makeID("wall_moss"), NetherBlocks.WALL_MOSS);

    public static final BCLFeature WALL_MUSHROOM_RED
            = FastFeatures.patch(BetterNether.makeID("wall_mushroom_red"), NetherBlocks.WALL_MUSHROOM_RED);

    public static void ensureStaticInitialization() {
    }
}
