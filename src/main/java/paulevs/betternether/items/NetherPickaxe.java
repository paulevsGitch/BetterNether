package paulevs.betternether.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.registry.NetherItems;
import paulevs.betternether.registry.NetherTags;
import ru.bclib.api.TagAPI;
import ru.bclib.items.tool.BasePickaxeItem;

public class NetherPickaxe extends BasePickaxeItem {
	public NetherPickaxe(Tier material) {
		super(material, 1, -2.8F, NetherItems.defaultSettings().fireResistant());
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if ((state.is(TagAPI.BLOCK_NETHER_STONES) || state.is(TagAPI.BLOCK_NETHER_PORTAL_FRAME)) && this.getTier().getLevel() > 2) {
			return this.speed * 3;
		}
		return super.getDestroySpeed(stack, state);
	}
}
