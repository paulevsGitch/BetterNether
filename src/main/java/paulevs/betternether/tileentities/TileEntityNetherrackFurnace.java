package paulevs.betternether.tileentities;

import net.minecraft.item.ItemStack;

public class TileEntityNetherrackFurnace extends TileEntityForge
{
	public String getName()
	{
		return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
	}
	
	public int getCookTime(ItemStack stack)
	{
		return 200;
	}
}
