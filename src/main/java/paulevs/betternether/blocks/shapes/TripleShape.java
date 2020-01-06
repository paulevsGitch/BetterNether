package paulevs.betternether.blocks.shapes;

import net.minecraft.util.StringIdentifiable;

public enum TripleShape implements StringIdentifiable
{
	TOP,
	MIDDLE,
	BOTTOM;
	
	@Override
	public String asString()
	{
		return this.toString().toLowerCase();
	}
}