package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.client.IRenderTypeable;

public class BNChain extends ChainBlock implements IRenderTypeable
{
	public BNChain()
	{
		super(FabricBlockSettings.copyOf(Blocks.CHAIN));
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		return Collections.singletonList(new ItemStack(this.asItem()));
	}
	
	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.CUTOUT;
	}
}
