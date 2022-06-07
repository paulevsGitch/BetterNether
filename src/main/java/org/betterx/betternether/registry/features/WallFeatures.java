package org.betterx.betternether.registry.features;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.FastFeatures;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.world.features.LucisFeature;

public class WallFeatures {

    public static final BCLFeature WALL_MOSS
            = FastFeatures.wallPatch(BetterNether.makeID("wall_moss"), NetherBlocks.WALL_MOSS, 4, 2, 8);
    public static final BCLFeature JUNGLE_MOSS
            = FastFeatures.wallPatch(BetterNether.makeID("jungle_moss"), NetherBlocks.JUNGLE_MOSS, 4, 2, 8);

    public static final BCLFeature WALL_MUSHROOM_RED
            = FastFeatures.wallPatch(BetterNether.makeID("wall_mushroom_red"),
            NetherBlocks.WALL_MUSHROOM_RED,
            4,
            2,
            8);

    public static final BCLFeature WALL_MUSHROOM_BROWN
            = FastFeatures.wallPatch(BetterNether.makeID("wall_mushroom_brown"),
            NetherBlocks.WALL_MUSHROOM_BROWN,
            4,
            2,
            8);

    public static final BCLFeature LUCIS_FEATURE = FastFeatures.simple(BetterNether.makeID("lucis"),
            new LucisFeature());
    public static final BCLFeature JUNGLE_MOSS_COVER
            = FastFeatures.patch(BetterNether.makeID("jungle_moss_cover"), NetherBlocks.JUNGLE_MOSS);

    
    public static void ensureStaticInitialization() {
    }
}
