package paulevs.betternether;

import java.util.Random;

public class MHelper
{
	public static final float PI2 = (float) (Math.PI * 2);
	private static final int ALPHA = 255 << 24;
	
	public static int color(int r, int g, int b)
	{
		return ALPHA | (r << 16) | (g << 8) | b;
	}
	
	public static int randRange(int min, int max, Random random)
	{
		return min + random.nextInt(max - min + 1);
	}
	
	public static float randRange(float min, float max, Random random)
	{
		return min + random.nextFloat() * (max - min);
	}
}
