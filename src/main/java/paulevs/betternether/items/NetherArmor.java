package paulevs.betternether.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import paulevs.betternether.interfaces.InitialStackStateProvider;
import paulevs.betternether.items.materials.BNArmorMaterial;
import paulevs.betternether.registry.NetherEnchantments;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.BaseArmorItem;

import java.util.HashMap;
import java.util.Map;

public class NetherArmor extends BaseArmorItem implements InitialStackStateProvider {
	public NetherArmor(ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, NetherItems.defaultSettings().fireResistant());
	}
	
	static final Map<Enchantment, Integer> DEFAULT_RUBY_ENCHANTS;
	
	@Override
	public void initializeState(ItemStack stack) {
		if (material== BNArmorMaterial.NETHER_RUBY) {
			EnchantmentHelper.setEnchantments(DEFAULT_RUBY_ENCHANTS, stack);
		}
	}
	
	static {
		DEFAULT_RUBY_ENCHANTS = new HashMap<>();
		DEFAULT_RUBY_ENCHANTS.put(NetherEnchantments.RUBY_FIRE, 1);
	}
}
