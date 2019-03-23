package paulevs.betternether.blocks;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import paulevs.betternether.BetterNether;

public class BlockPlateWooden extends BlockPressurePlate
{

	public BlockPlateWooden(String name)
	{
		super(Material.WOOD, Sensitivity.EVERYTHING);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setSoundType(SoundType.WOOD);
		this.setHardness(2.0F);
		this.setCreativeTab(BetterNether.BN_TAB);
	}

}
