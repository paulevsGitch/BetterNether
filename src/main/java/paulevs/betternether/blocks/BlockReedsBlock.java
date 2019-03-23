package paulevs.betternether.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import paulevs.betternether.BetterNether;

public class BlockReedsBlock extends BlockRotatedPillar
{
	protected BlockReedsBlock()
	{
		super(Material.WOOD, MapColor.CYAN);
		this.setRegistryName("reeds_block");
		this.setUnlocalizedName("reeds_block");
		this.setSoundType(SoundType.WOOD);
		this.setHardness(2.0F);
		this.setCreativeTab(BetterNether.BN_TAB);
	}
}