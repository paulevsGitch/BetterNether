package paulevs.betternether.interfaces;

import net.minecraft.world.item.ItemStack;

public interface InitialStackStateProvider {
	public void initializeState(ItemStack stack);
}
