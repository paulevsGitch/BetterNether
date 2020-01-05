package paulevs.betternether.blocks;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;

public class BlockBase extends Block
{
	private boolean isClimmable = false;
	private boolean dropItself = true;
	private BNRenderLayer layer = BNRenderLayer.SOLID;
	
	public BlockBase(Settings settings)
	{
		super(settings);
	}
	
	public void setClimmable(boolean climmable)
	{
		this.isClimmable = climmable;
	}
	
	public boolean isClimmable()
	{
		return isClimmable;
	}
	
	public void setRenderLayer(BNRenderLayer layer)
	{
		this.layer = layer;
	}
	
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
}
