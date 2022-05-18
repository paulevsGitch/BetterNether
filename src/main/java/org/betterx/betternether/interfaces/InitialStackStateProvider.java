package org.betterx.betternether.interfaces;

import net.minecraft.world.item.ItemStack;

public interface InitialStackStateProvider {
    void initializeState(ItemStack stack);
}
