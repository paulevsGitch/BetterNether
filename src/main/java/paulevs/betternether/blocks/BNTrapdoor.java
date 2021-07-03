package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.client.IRenderTypeable;

import java.util.Collections;
import java.util.List;

public class BNTrapdoor extends TrapdoorBlock implements IRenderTypeable {
	public BNTrapdoor(Block block) {
		super(BlocksHelper.copySettingsOf(block).nonOpaque().strength(3.0F));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this.asItem()));
	}

	@Override
	public BNRenderLayer getRenderLayer() {
		return BNRenderLayer.CUTOUT;
	}
}
