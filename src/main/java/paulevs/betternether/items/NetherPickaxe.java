package paulevs.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import paulevs.betternether.interfaces.InitialStackStateProvider;
import paulevs.betternether.items.materials.BNToolMaterial;
import paulevs.betternether.registry.NetherEnchantments;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.tool.BasePickaxeItem;

import java.util.HashMap;
import java.util.Map;

public class NetherPickaxe extends BasePickaxeItem implements InitialStackStateProvider {
	public NetherPickaxe(Tier material) {
		super(material, 1, -2.8F, NetherItems.defaultSettings().fireResistant());
	}
	
	@Override
	public void initializeState(ItemStack stack) {
		Map<Enchantment, Integer> defaultEnchants = new HashMap<>();
		
		int obsidianLevel = 0;
		if (this.getTier()== BNToolMaterial.CINCINNASITE_DIAMOND) obsidianLevel = 3;
		else if (this.getTier()== BNToolMaterial.NETHER_RUBY) {
			obsidianLevel = 2;
			defaultEnchants.put(NetherEnchantments.RUBY_FIRE, 1);
		}
		
		if (obsidianLevel>0) {
			defaultEnchants.put(NetherEnchantments.OBSIDIAN_BREAKER, obsidianLevel);
			EnchantmentHelper.setEnchantments(defaultEnchants, stack);
		}
	}
}
