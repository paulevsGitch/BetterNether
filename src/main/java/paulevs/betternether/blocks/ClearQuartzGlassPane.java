package paulevs.betternether.blocks;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;

public class ClearQuartzGlassPane extends BlockPane
{
	public ClearQuartzGlassPane(String name)
	{
		super(Material.GLASS, true);
		this.setHardness(0.5F);
		this.setResistance(3.0F);
		this.setSoundType(SoundType.GLASS);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(BetterNether.BN_TAB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
