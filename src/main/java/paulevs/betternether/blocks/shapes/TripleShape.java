package paulevs.betternether.blocks.shapes;

import net.minecraft.util.StringIdentifiable;

public enum TripleShape implements StringIdentifiable
{
	TOP("top"),
	MIDDLE("middle"),
	BOTTOM("bottom");
	
	final String name;
	
	TripleShape(String name)
	{
		this.name = name;
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
}