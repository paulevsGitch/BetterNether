package paulevs.betternether.items;

import net.minecraft.item.Item;
import paulevs.betternether.BetterNether;

public class ItemStandart extends Item
{
	public ItemStandart(String name)
	{
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(BetterNether.BN_TAB);
	}
}
