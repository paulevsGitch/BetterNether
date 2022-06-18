package org.betterx.betternether.registry.features.placed;

import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.betternether.registry.features.configured.NetherTrees;

public class NetherTreesPlaced {
    public static BCLFeature CRIMSON_GLOWING_TREE = NetherTrees
            .CRIMSON_GLOWING_TREE
            .place()
            .vanillaNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static BCLFeature CRIMSON_PINE = NetherTrees
            .CRIMSON_PINE
            .place()
            .vanillaNetherGround(12)
            .isEmptyAndOnNetherGround()
            .buildAndRegister();

    public static void ensureStaticInitialization() {
    }
}
