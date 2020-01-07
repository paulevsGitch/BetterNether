package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

public class BNButtom extends WoodButtonBlock
{
	public BNButtom(Block block)
	{
		super(FabricBlockSettings.copy(block).nonOpaque().build());
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Collections.singletonList(new ItemStack(this.asItem()));
	}
}
