package paulevs.betternether.biomes;

import net.minecraft.world.biome.Biome;

public class NetherBiomeWrapper extends NetherBiome
{
	final Biome biome;
	
	public NetherBiomeWrapper(String name, Biome biome)
	{
		super(name);
		this.biome = biome;
	}
	
	@Override
	public Biome getBiome()
	{
		return biome;
	}
}
