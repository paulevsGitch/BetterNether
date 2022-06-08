package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.poi.BCLPoiType;
import org.betterx.betternether.BetterNether;

public class NetherPoiTypes {
    public static final BCLPoiType PIG_STATUE = BCLPoiType.register(
            BetterNether.makeID("PIG_STATUE"),
            BCLPoiType.getBlockStates(NetherBlocks.PIG_STATUE_RESPAWNER),
            1, 1
    );

    public static void register() {

    }
}
