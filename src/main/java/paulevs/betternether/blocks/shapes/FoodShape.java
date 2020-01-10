package paulevs.betternether.blocks.shapes;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public enum FoodShape implements StringIdentifiable
{
	NONE,
	WART,
	MUSHROOM,
	APPLE;
	
	private Item item;
	
	public void setItem(Item item)
	{
		this.item = item;
	}
	
	@Override
	public String asString()
	{
		return this.toString().toLowerCase();
	}
	
	public Item getItem()
	{
		return item;
	}
}