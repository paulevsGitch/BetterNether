package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.client.IRenderTypeable;

public class BNPane extends PaneBlock implements IRenderTypeable
{
	private boolean dropSelf;
	
	public BNPane(Block block, boolean dropSelf)
	{
		super(FabricBlockSettings.copy(block).strength(0.3F, 0.3F).nonOpaque().build());
		this.dropSelf = dropSelf;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (dropSelf)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDroppedStacks(state, builder);
	}

	@Override
	public BNRenderLayer getRenderLayer()
	{
		return BNRenderLayer.TRANSLUCENT;
	}
}
