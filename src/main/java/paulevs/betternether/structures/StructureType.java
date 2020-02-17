package paulevs.betternether.structures;

public enum StructureType
{
	FLOOR,
	WALL,
	CEIL,
	LAVA,
	UNDER;

	public static StructureType fromString(String a)
	{
		if (a.equals("floor"))
			return FLOOR;
		else if (a.equals("wall"))
			return WALL;
		else if (a.equals("ceil"))
			return CEIL;
		else if (a.equals("lava"))
			return LAVA;
		else if (a.equals("under"))
			return UNDER;
		return FLOOR;
	}
	
	public String getName()
	{
		return this.toString().toLowerCase();
	}
}