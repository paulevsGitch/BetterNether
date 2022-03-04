package paulevs.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import paulevs.betternether.interfaces.InitialStackStateProvider;
import paulevs.betternether.items.materials.BNToolMaterial;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.tool.BaseSwordItem;

public class NetherSword extends BaseSwordItem implements InitialStackStateProvider {
	public NetherSword(Tier material, int attackDamage, float attackSpeed) {
		super(material, attackDamage, attackSpeed, NetherItems.defaultSettings().fireResistant());
	}
	
	@Override
	public void initializeState(ItemStack stack) {
		if (getTier()== BNToolMaterial.NETHER_RUBY) {
			EnchantmentHelper.setEnchantments(NetherArmor.DEFAULT_RUBY_ENCHANTS, stack);
		}
	}
}
