package paulevs.betternether.noise;

public class WorleyNoiseIDDistorted3D
{
	private WorleyNoiseID3D idNoise;
	private WorleyNoiseOctaved noiseX;
	private WorleyNoiseOctaved noiseY;
	private WorleyNoiseOctaved noiseZ;
	
	public WorleyNoiseIDDistorted3D(long seed, int maxID)
	{
		idNoise = new WorleyNoiseID3D(seed, maxID);
		noiseX = new WorleyNoiseOctaved(seed + 9L, 3);
		noiseY = new WorleyNoiseOctaved(seed + 19L, 3);
		noiseZ = new WorleyNoiseOctaved(seed + 29L, 3);
	}
	
	public int GetValue(double x, double y, double z)
	{
		double nx = x;
		double ny = y;
		double nz = z;
		x *= 0.5;
		y *= 0.5;
		z *= 0.5;
		nx += noiseX.GetValue(y, z);
		ny += noiseY.GetValue(x, -z);
		nz += noiseZ.GetValue(-x, -y);
		return idNoise.GetValue(nx, ny, nz);
	}
}
