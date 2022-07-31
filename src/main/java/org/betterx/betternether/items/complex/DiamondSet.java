package org.betterx.betternether.items.complex;

import org.betterx.bclib.items.complex.EquipmentDescription;
import org.betterx.bclib.items.complex.EquipmentSet;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.items.*;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class DiamondSet extends EquipmentSet {
    public DiamondSet(EquipmentSet set, int attackDamage, float attackSpeed) {
        super(set.material, BetterNether.MOD_ID, set.prefix, NetherBlocks.NETHER_REED_STEM);

        add(PICKAXE_SLOT, new DiamondDescriptor<>(set.getSlot(PICKAXE_SLOT), NetherPickaxe::new));
        add(AXE_SLOT, new DiamondDescriptor<>(set.getSlot(AXE_SLOT), NetherAxe::new));
        add(SHOVEL_SLOT, new DiamondDescriptor<>(set.getSlot(SHOVEL_SLOT), NetherShovel::new));
        add(HOE_SLOT, new DiamondDescriptor<>(set.getSlot(HOE_SLOT), NetherHoe::new));
        add(
                SWORD_SLOT,
                new DiamondDescriptor<>(
                        set.getSlot(SWORD_SLOT),
                        tier -> new NetherSword(tier, attackDamage, attackSpeed)
                )
        );

    }

    @Override
    protected @NotNull ResourceLocation buildID(Map.Entry<String, EquipmentDescription<?>> desc) {
        return new ResourceLocation(modID, prefix + "_" + desc.getKey() + "_diamond");
    }

    public DiamondSet init() {
        super.init(NetherItems.getItemRegistry());
        return this;
    }
}
