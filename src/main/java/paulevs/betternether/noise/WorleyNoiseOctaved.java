package paulevs.betternether.noise;

public class WorleyNoiseOctaved
{
	private WorleyNoise noise;
	private int octaves;
	private double[] multipliers;
	private double[] negativeMultipliers;
	private int[] offsets;

	public WorleyNoiseOctaved(long seed, int octaves)
	{
		this.octaves = octaves;
		noise = new WorleyNoise(seed);
		octaves--;
		multipliers = new double[octaves];
		negativeMultipliers = new double[octaves];
		offsets = new int[octaves];
		for (int i = 0; i < octaves; i++)
		{
			offsets[i] = 2 << i;
			negativeMultipliers[i] = 1D / (double) offsets[i];
			multipliers[i] = 1D - negativeMultipliers[i];
		}
	}

	public double GetValue(double x, double y)
	{
		double result = noise.GetValue(x, y);
		for (int i = 0; i < octaves - 1; i++)
			result = result * multipliers[i] + noise.GetValue(x * offsets[i], y * offsets[i]) * negativeMultipliers[i];
		return result;
	}
}
