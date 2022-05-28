package org.betterx.betternether.integrations.blockus;

import net.fabricmc.loader.api.FabricLoader;

public class BlockusUtils {
    public static boolean hasBlockus() {
        return FabricLoader.getInstance()
                           .getModContainer("blockus").isPresent();
    }
}
