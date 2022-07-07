package org.betterx.betternether.world.features;

import org.betterx.betternether.world.BNWorldGenerator;

public class NetherThreadDataStorage {
    public static final ThreadLocal<BNWorldGenerator> WORLD_GENERATOR = ThreadLocal.withInitial(() -> new BNWorldGenerator());

    public static BNWorldGenerator generatorForThread() {
        return WORLD_GENERATOR.get();
    }

}
