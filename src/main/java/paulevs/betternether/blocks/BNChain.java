package paulevs.betternether.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.client.IRenderTypeable;

import java.util.Collections;
import java.util.List;

public class BNChain extends ChainBlock implements IRenderTypeable {
	public BNChain() {
		super(BlocksHelper.copySettingsOf(Blocks.CHAIN));
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
