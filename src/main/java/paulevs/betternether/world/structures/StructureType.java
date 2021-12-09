package paulevs.betternether.world.structures;

public enum StructureType {
	FLOOR, WALL, CEIL, LAVA, UNDER;

	public static StructureType fromString(String a) {
		if (a.contains("floor"))
			return FLOOR;
		else if (a.contains("wall"))
			return WALL;
		else if (a.contains("ceil"))
			return CEIL;
		else if (a.contains("lava"))
			return LAVA;
		else if (a.contains("under"))
			return UNDER;
		return FLOOR;
	}

	public String getName() {
		return this.toString().toLowerCase();
	}
}