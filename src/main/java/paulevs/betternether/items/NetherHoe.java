package paulevs.betternether.items;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.NetherItems;
import ru.bclib.interfaces.TagProvider;
import ru.bclib.items.tool.BaseHoeItem;

import java.util.List;

public class NetherHoe extends BaseHoeItem {

	public NetherHoe(Tier material) {
		super(material, 1, -2.8F, NetherItems.defaultSettings().fireResistant());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return super.getDestroySpeed(stack, state);
	}
}
