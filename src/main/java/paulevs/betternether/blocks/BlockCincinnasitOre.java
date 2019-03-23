package paulevs.betternether.blocks;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import paulevs.betternether.BetterNether;
import paulevs.betternether.items.ItemsRegister;

public class BlockCincinnasitOre extends BlockOre
{
	public BlockCincinnasitOre()
	{
		super(MapColor.GOLD);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.STONE);
		this.setUnlocalizedName("cincinnasite_ore");
		this.setRegistryName("cincinnasite_ore");
		this.setCreativeTab(BetterNether.BN_TAB);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		return ItemsRegister.CINCINNASITE;
    }
}
