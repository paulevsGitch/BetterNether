package org.betterx.betternether.registry;

import org.betterx.betternether.BetterNether;
import org.betterx.worlds.together.flatLevel.FlatLevelPresets;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;

public class FlatLevelPresetsRegistry {
    public static final ResourceKey<FlatLevelGeneratorPreset> BN_FLAT = FlatLevelPresets.register(
            BetterNether.makeID("flat")
    );

    public static void register() {
        //needs to get called in order for the static variables to be initialized
    }
}
