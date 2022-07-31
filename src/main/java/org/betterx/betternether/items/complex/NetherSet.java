package org.betterx.betternether.items.complex;

import org.betterx.bclib.items.complex.EquipmentDescription;
import org.betterx.bclib.items.complex.EquipmentSet;
import org.betterx.bclib.items.tool.BaseShearsItem;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.items.*;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;

public class NetherSet extends EquipmentSet {
    public NetherSet(
            String prefix,
            Tier material,
            ArmorMaterial armor,
            int attackDamage,
            float attackSpeed,
            boolean withShears
    ) {
        super(material, BetterNether.MOD_ID, prefix, NetherBlocks.NETHER_REED_STEM);

        add(PICKAXE_SLOT, new EquipmentDescription<>(NetherPickaxe::new));
        add(AXE_SLOT, new EquipmentDescription<>(NetherAxe::new));
        add(SHOVEL_SLOT, new EquipmentDescription<>(NetherShovel::new));
        add(HOE_SLOT, new EquipmentDescription<>(NetherHoe::new));
        add(SWORD_SLOT, new EquipmentDescription<>((tier) -> new NetherSword(tier, attackDamage, attackSpeed)));

        add(HELMET_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.HEAD)));
        add(CHESTPLATE_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.CHEST)));
        add(LEGGINS_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.LEGS)));
        add(BOOTS_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.FEET)));

        if (withShears) {
            add(
                    SHEARS_SLOT,
                    new EquipmentDescription<>(tier -> new BaseShearsItem(
                            NetherItems
                                    .defaultSettings()
                                    .durability((int) (material.getUses() * 0.75))
                    )
                    )
            );
        }
    }

    public NetherSet init() {
        super.init(NetherItems.getItemRegistry());
        return this;
    }
}
