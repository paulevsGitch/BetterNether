package paulevs.betternether.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import paulevs.betternether.BetterNether;

public class BlockStalagnateBark extends BlockRotatedPillar
{

	protected BlockStalagnateBark()
	{
		super(Material.WOOD, MapColor.LIME_STAINED_HARDENED_CLAY);
		this.setRegistryName("stalagnate_bark");
		this.setUnlocalizedName("stalagnate_bark");
		this.setSoundType(SoundType.WOOD);
		this.setHardness(2.0F);
		this.setCreativeTab(BetterNether.BN_TAB);
	}

}
