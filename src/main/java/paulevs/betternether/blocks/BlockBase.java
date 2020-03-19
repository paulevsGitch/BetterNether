package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import paulevs.betternether.client.IRenderTypeable;

public class BlockBase extends Block implements IRenderTypeable
{
	private boolean dropItself = true;
	private BNRenderLayer layer = BNRenderLayer.SOLID;
	
	public BlockBase(Settings settings)
	{
		super(settings);
	}
	
	public void setRenderLayer(BNRenderLayer layer)
	{
		this.layer = layer;
	}
	
	@Override
	public BNRenderLayer getRenderLayer()
	{
		return layer;
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		if (dropItself)
			return Collections.singletonList(new ItemStack(this.asItem()));
		else
			return super.getDroppedStacks(state, builder);
	}
	
	public void setDropItself(boolean drop)
	{
		this.dropItself = drop;
	}
	
	/*public int getLuminance(BlockState state)
	{
		return 0;
	}*/
}
