package paulevs.betternether.noise;

public class Dither
{
	private PermutationTable ditherX;
	private PermutationTable ditherY;
	private PermutationTable ditherZ;
	
	public Dither(long seed)
	{
		ditherX = new PermutationTable(seed + 41L);
		ditherY = new PermutationTable(seed + 59L);
		ditherZ = new PermutationTable(seed + 71L);
	}
	
	public int ditherX(int x, int y, int z)
	{
		return x + (ditherX.PosChar(x, y, z) & 3);
	}
	
	public int ditherY(int x, int y, int z)
	{
		return y + (ditherY.PosChar(x, y, z) & 3);
	}
	
	public int ditherZ(int x, int y, int z)
	{
		return z + (ditherZ.PosChar(x, y, z) & 3);
	}
}
