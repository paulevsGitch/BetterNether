package paulevs.betternether.blocks;

import net.minecraft.block.Block;

public class BlockBase extends Block
{
	private boolean isClimmable = false;
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
}
