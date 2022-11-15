package org.betterx.betternether.items.complex;

import org.betterx.bclib.api.v2.advancement.AdvancementManager;
import org.betterx.bclib.items.complex.EquipmentDescription;
import org.betterx.bclib.items.complex.EquipmentSet;
import org.betterx.bclib.items.tool.BaseShearsItem;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.items.*;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class NetherSet extends EquipmentSet {
    public NetherSet(
            String prefix,
            BNToolMaterial material,
            ArmorMaterial armor,
            boolean withShears
    ) {
        super(
                material,
                BetterNether.MOD_ID,
                prefix,
                NetherBlocks.NETHER_REED_STEM,
                material.attackDamages,
                material.attackSpeeds
        );

        add(PICKAXE_SLOT, EquipmentDescription::new, NetherPickaxe::new);
        add(AXE_SLOT, EquipmentDescription::new, NetherAxe::new);
        add(SHOVEL_SLOT, EquipmentDescription::new, NetherShovel::new);
        add(HOE_SLOT, EquipmentDescription::new, NetherHoe::new);
        add(SWORD_SLOT, EquipmentDescription::new, NetherSword::new);

        add(HELMET_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.HEAD)));
        add(CHESTPLATE_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.CHEST)));
        add(LEGGINGS_SLOT, new EquipmentDescription<>(tier -> new NetherArmor(armor, EquipmentSlot.LEGS)));
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

    public AdvancementManager.Builder addArmorSet(AdvancementManager.Builder builder) {
        return builder
                .addInventoryChangedCriterion(baseName + "_" + HELMET_SLOT, getSlot(HELMET_SLOT))
                .addInventoryChangedCriterion(baseName + "_" + CHESTPLATE_SLOT, getSlot(CHESTPLATE_SLOT))
                .addInventoryChangedCriterion(baseName + "_" + LEGGINGS_SLOT, getSlot(LEGGINGS_SLOT))
                .addInventoryChangedCriterion(baseName + "_" + BOOTS_SLOT, getSlot(BOOTS_SLOT));
    }
}
