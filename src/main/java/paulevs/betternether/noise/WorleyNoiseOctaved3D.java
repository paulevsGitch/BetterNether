package paulevs.betternether.noise;

public class WorleyNoiseOctaved3D
{
	private PermutationTable randX;
	private PermutationTable randY;
	private PermutationTable randZ;
	private PermutationTable ids;
	private int maxID;

	public WorleyNoiseOctaved3D(long seed, int maxID)
	{
		randX = new PermutationTable(seed);
		randY = new PermutationTable((seed + 1337L));
		randZ = new PermutationTable((seed + 2673L));
		ids = new PermutationTable((seed + 135L));
		this.maxID = maxID;
	}

	public double GetValue(double x, double y, double z)
	{
		int px = (int) x;
		int py = (int) y;
		int pz = (int) z;
		x -= (double) px;
		y -= (double) py;
		z -= (double) pz;
		int indexX = px;
		int indexY = py;
		int indexZ = pz;
		double d = 10;
		for (int i = -1; i < 2; i++)
		{
			int cx = (px + i) & 255;
			for (int j = -1; j < 2; j++)
			{
				int cy = (py + j) & 255;
				for (int k = -1; k < 2; k++)
				{
					int cz = (pz + k) & 255;
					double ox = sqr(x - randX.PosReal(cx, -cy) - (double) i);
					double oy = sqr(y - randY.PosReal(-cz, -cx) - (double) j);
					double oz = sqr(z - randZ.PosReal(-cx, cy) - (double) k);
					double nd = Math.sqrt(ox + oy + oz);
					if (nd < d)
					{
						d = nd;
						indexX = cx;
						indexY = cy;
						indexZ = cz;
					}
				}
			}
		}
		return ids.PosReal(indexX, indexY, indexZ);
	}
	
	private double sqr(double x)
	{
		return x * x;
	}
}
