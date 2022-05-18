package org.betterx.betternether.advancements;

import net.minecraft.advancements.critereon.PlayerTrigger;

import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;

import org.betterx.betternether.BetterNether;

public class BNCriterion {
    public static PlayerTrigger BREW_BLUE;
    public static ConvertByLightningTrigger CONVERT_BY_LIGHTNING;

    public static void register() {
        BREW_BLUE = CriterionRegistry.register(new PlayerTrigger(BetterNether.makeID("brew_blue")));
        CONVERT_BY_LIGHTNING = CriterionRegistry.register(new ConvertByLightningTrigger());
    }
}
