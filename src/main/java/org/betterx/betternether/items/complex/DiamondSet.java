package org.betterx.betternether.items.complex;

import org.betterx.bclib.items.complex.EquipmentDescription;
import org.betterx.bclib.items.complex.EquipmentSet;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.items.*;
import org.betterx.betternether.items.materials.BNToolMaterial;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherItems;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class DiamondSet extends EquipmentSet {
    public DiamondSet(EquipmentSet set) {
        super(
                BNToolMaterial.CINCINNASITE_DIAMOND,
                BetterNether.MOD_ID,
                set.baseName,
                NetherBlocks.NETHER_REED_STEM,
                BNToolMaterial.CINCINNASITE_DIAMOND.attackDamages,
                BNToolMaterial.CINCINNASITE_DIAMOND.attackSpeeds
        );

        add(PICKAXE_SLOT, set, DiamondDescriptor::new, NetherPickaxe::new);
        add(AXE_SLOT, set, DiamondDescriptor::new, NetherAxe::new);
        add(SHOVEL_SLOT, set, DiamondDescriptor::new, NetherShovel::new);
        add(HOE_SLOT, set, DiamondDescriptor::new, NetherHoe::new);
        add(SWORD_SLOT, set, DiamondDescriptor::new, NetherSword::new);

    }

    @Override
    protected @NotNull ResourceLocation buildID(Map.Entry<String, EquipmentDescription<?>> desc) {
        return new ResourceLocation(modID, baseName + "_" + desc.getKey() + "_diamond");
    }

    public DiamondSet init() {
        super.init(NetherItems.getItemRegistry());
        return this;
    }
}
