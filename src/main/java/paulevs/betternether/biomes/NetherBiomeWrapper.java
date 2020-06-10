package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import paulevs.betternether.IBiome;

public class NetherBiomeWrapper extends NetherBiome
{
	final Biome biome;
	
	public NetherBiomeWrapper(String name, Biome biome)
	{
		super(new BiomeDefenition(name));
		this.biome = biome;
	}
	
	@Override
	public Biome getBiome()
	{
		return biome;
	}
	
	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random)
	{
		//SurfaceConfig config = biome.getSurfaceConfig();
		//BlocksHelper.setWithoutUpdate(world, pos, config.getTopMaterial());
	}
	
	@Override
	public void addEntitySpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize)
	{
		((IBiome) biome).addEntitySpawn(type, weight, minGroupSize, maxGroupSize);
	}
}