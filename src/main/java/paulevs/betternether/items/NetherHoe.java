package paulevs.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.items.tool.BaseHoeItem;

public class NetherHoe extends BaseHoeItem {

	public NetherHoe(Tier material) {
		super(material, 1, -2.8F, NetherItems.defaultSettings().fireResistant());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state);
	}
}
