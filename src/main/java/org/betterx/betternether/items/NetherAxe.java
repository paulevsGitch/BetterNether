package org.betterx.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.items.tool.BaseAxeItem;
import org.betterx.betternether.interfaces.InitialStackStateProvider;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherItems;

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
        if (getTier() == BNToolMaterial.NETHER_RUBY) {
            EnchantmentHelper.setEnchantments(NetherArmor.DEFAULT_RUBY_ENCHANTS, stack);
        }
    }
}
