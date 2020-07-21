package paulevs.betternether.blocks.shapes;

import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;

public enum FoodShape implements StringIdentifiable
{
	NONE("none"),
	WART("wart"),
	MUSHROOM("mushroom"),
	APPLE("apple");
	
	private final String name;
	private Item item;
	
	FoodShape(String name)
	{
		this.name = name;
	}
	
	public void setItem(Item item)
	{
		this.item = item;
	}
	
	@Override
	public String asString()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public Item getItem()
	{
		return item;
	}
}