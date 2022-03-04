package paulevs.betternether.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;
import paulevs.betternether.BetterNether;
import paulevs.betternether.enchantments.RubyFire;
import paulevs.betternether.enchantments.ObsidianBreaker;

public class NetherEnchantments {
	public static ObsidianBreaker OBSIDIAN_BREAKER = register("obsidian_breaker", new ObsidianBreaker());
	public static RubyFire RUBY_FIRE = register("ruby_fire", new RubyFire());
	
	private static<T extends Enchantment> T register(String name, T enchantment) {
		return Registry.register(Registry.ENCHANTMENT, BetterNether.makeID(name), enchantment);
	}
}
