package paulevs.betternether.items;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.ItemsRegistry;

public class BNItemHoe extends HoeItem {
	protected float speed;

	public BNItemHoe(Tier material, int durability, float speed) {
		super(material, 1, -2.8F, ItemsRegistry.defaultSettings().fireResistant());
		this.speed = speed;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state) * speed;
	}
}
