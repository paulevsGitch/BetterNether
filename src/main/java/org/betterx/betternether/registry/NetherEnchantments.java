package org.betterx.betternether.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

import org.betterx.betternether.BetterNether;
import org.betterx.betternether.enchantments.ObsidianBreaker;
import org.betterx.betternether.enchantments.RubyFire;

public class NetherEnchantments {
    public static ObsidianBreaker OBSIDIAN_BREAKER = register("obsidian_breaker", new ObsidianBreaker());
    public static RubyFire RUBY_FIRE = register("ruby_fire", new RubyFire());

    private static <T extends Enchantment> T register(String name, T enchantment) {
        return Registry.register(Registry.ENCHANTMENT, BetterNether.makeID(name), enchantment);
    }
}
