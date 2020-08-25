package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import paulevs.betternether.structures.StructureType;

public class NetherBiomeWrapper extends NetherBiome
{
	final Biome biome;
	
	public NetherBiomeWrapper(String name, String group, Biome biome)
	{
		super(new BiomeDefenition(name, group));
		this.biome = biome;
		
		if (biome.getGenerationSettings().getSurfaceBuilder() == ConfiguredSurfaceBuilders.BASALT_DELTAS)
		{
			addStructure("blackstone_stalactite", STALACTITE_BLACKSTONE, StructureType.FLOOR, 0.2F, true);
			addStructure("stalactite_stalactite", STALACTITE_BASALT, StructureType.FLOOR, 0.2F, true);
			
			addStructure("blackstone_stalagmite", STALAGMITE_BLACKSTONE, StructureType.CEIL, 0.1F, true);
			addStructure("basalt_stalagmite", STALAGMITE_BASALT, StructureType.CEIL, 0.1F, true);
		}
	}
	
	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random) {}
	
	/*@Override
	public void addEntitySpawn(EntityType<?> type, int weight, int minGroupSize, int maxGroupSize)
	{
		((IBiome) biome).addEntitySpawn(type, weight, minGroupSize, maxGroupSize);
	}*/
}