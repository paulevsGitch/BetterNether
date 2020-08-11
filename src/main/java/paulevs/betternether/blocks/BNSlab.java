package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

public class BNSlab extends SlabBlock
{
	public BNSlab(Block block)
	{
		super(FabricBlockSettings.copyOf(block).nonOpaque());
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Collections.singletonList(new ItemStack(this.asItem(), state.get(TYPE) == SlabType.DOUBLE ? 2 : 1));
	}
}
