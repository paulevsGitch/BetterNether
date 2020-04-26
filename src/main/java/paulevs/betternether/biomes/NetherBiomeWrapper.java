package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import paulevs.betternether.BlocksHelper;

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
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		SurfaceConfig config = biome.getSurfaceConfig();
		BlocksHelper.setWithoutUpdate(world, pos, config.getTopMaterial());
	}
}