package paulevs.betternether.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQuartzGlass extends BlockCincinnasiteFrame
{
	public BlockQuartzGlass(String name)
	{
		super(name);
		this.setSoundType(SoundType.GLASS);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
