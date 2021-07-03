package paulevs.betternether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.BlocksHelper;

import java.util.Collections;
import java.util.List;

public class BNSlab extends SlabBlock {
	public BNSlab(Block block) {
		super(BlocksHelper.copySettingsOf(block));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this.asItem(), state.get(TYPE) == SlabType.DOUBLE ? 2 : 1));
	}
}
