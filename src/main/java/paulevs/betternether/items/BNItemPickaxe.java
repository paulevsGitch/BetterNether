package paulevs.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.NetherItems;

public class BNItemPickaxe extends PickaxeItem {
	protected float speed;

	public BNItemPickaxe(Tier material, int durability, float speed) {
		super(material, 1, -2.8F, NetherItems.defaultSettings().fireResistant());
		this.speed = speed;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state) * speed;
	}
}
