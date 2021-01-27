package com.paulevs.betternether.mixin;

import com.paulevs.betternether.BetterNether;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {
    @Override
    public void connect() {
        BetterNether.LOGGER.debug("Better Nether: Connecting Mixins...");
        Mixins.addConfiguration("betternether.mixins.json");
        //BetterNether.isUsingMixin = true;
        BetterNether.LOGGER.info("Better Nether: Mixin Connected!");
    }
}