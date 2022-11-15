package org.betterx.betternether.items;

import org.betterx.bclib.items.tool.BaseSwordItem;
import org.betterx.betternether.interfaces.InitialStackStateProvider;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class NetherSword extends BaseSwordItem implements InitialStackStateProvider {
    public NetherSword(Tier material, float attackDamage, float attackSpeed) {
        super(material, (int) attackDamage, attackSpeed, NetherItems.defaultSettings().fireResistant());
    }

    @Override
    public void initializeState(ItemStack stack) {
        if (getTier() == BNToolMaterial.NETHER_RUBY) {
            EnchantmentHelper.setEnchantments(NetherArmor.DEFAULT_RUBY_ENCHANTS, stack);
        }
    }
}
