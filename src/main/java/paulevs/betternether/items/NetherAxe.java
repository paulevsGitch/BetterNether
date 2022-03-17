package paulevs.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.interfaces.InitialStackStateProvider;
import paulevs.betternether.items.materials.BNToolMaterial;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.tool.BaseAxeItem;

public class NetherAxe extends BaseAxeItem implements InitialStackStateProvider {
	public NetherAxe(Tier material) {
		super(material, 1, -2.8F, NetherItems.defaultSettings().fireResistant());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state);
	}
	
	@Override
	public void initializeState(ItemStack stack) {
		if (getTier()== BNToolMaterial.NETHER_RUBY) {
			EnchantmentHelper.setEnchantments(NetherArmor.DEFAULT_RUBY_ENCHANTS, stack);
		}
	}
}
