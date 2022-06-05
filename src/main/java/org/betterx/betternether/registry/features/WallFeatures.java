package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.FastFeatures;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;

public class WallFeatures {

    public static final BCLFeature WALL_MOSS
            = FastFeatures.wallPatch(BetterNether.makeID("wall_moss_red"), NetherBlocks.WALL_MOSS, 4, 2, 8);

    public static final BCLFeature WALL_MUSHROOM_RED
            = FastFeatures.wallPatch(BetterNether.makeID("wall_mushroom_red"),
            NetherBlocks.WALL_MUSHROOM_RED,
            4,
            2,
            8);

    public static void ensureStaticInitialization() {
    }
}
