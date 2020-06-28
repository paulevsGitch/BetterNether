package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.BasaltDeltasBiome;
import net.minecraft.world.biome.Biome;
import paulevs.betternether.IBiome;
import paulevs.betternether.structures.StructureType;

public class NetherBiomeWrapper extends NetherBiome
{
	final Biome biome;
	
	public NetherBiomeWrapper(String name, String group, Biome biome)
	{
		super(new BiomeDefenition(name, group));
		this.biome = biome;
		
		if (biome instanceof BasaltDeltasBiome)
		{
			addStructure("blackstone_stalactite", STALACTITE_BLACKSTONE, StructureType.FLOOR, 0.2F, true);
			addStructure("stalactite_stalactite", STALACTITE_BASALT, StructureType.FLOOR, 0.2F, true);
			
			addStructure("blackstone_stalagmite", STALAGMITE_BLACKSTONE, StructureType.CEIL, 0.1F, true);
			addStructure("basalt_stalagmite", STALAGMITE_BASALT, StructureType.CEIL, 0.1F, true);
		}
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