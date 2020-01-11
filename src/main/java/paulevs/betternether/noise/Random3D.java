package paulevs.betternether.noise;

import java.util.Random;

public class Random3D
{
	private Random random = new Random();
	
	public void setSeed(int x, int y, int z)
	{
		random.setSeed(mix(x, y, z));
	}
	
	public Random getRandom()
	{
		return random;
	}

	private int mix(int a, int b, int c)
	{
		a = a - b;
		a = a - c;
		a = a ^ (c >>> 13);
		b = b - c;
		b = b - a;
		b = b ^ (a << 8);
		c = c - a;
		c = c - b;
		c = c ^ (b >>> 13);
		a = a - b;
		a = a - c;
		a = a ^ (c >>> 12);
		b = b - c;
		b = b - a;
		b = b ^ (a << 16);
		c = c - a;
		c = c - b;
		c = c ^ (b >>> 5);
		a = a - b;
		a = a - c;
		a = a ^ (c >>> 3);
		b = b - c;
		b = b - a;
		b = b ^ (a << 10);
		c = c - a;
		c = c - b;
		c = c ^ (b >>> 15);
		return c;
	}
}
