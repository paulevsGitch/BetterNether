package org.betterx.betternether.registry;

import org.betterx.bclib.presets.FlatLevelPresets;
import org.betterx.betternether.BetterNether;

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
