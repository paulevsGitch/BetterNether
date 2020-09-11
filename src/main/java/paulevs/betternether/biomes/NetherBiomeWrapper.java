package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import paulevs.betternether.structures.StructureType;

public class NetherBiomeWrapper extends NetherBiome
{
	final Biome biome;
	
	public NetherBiomeWrapper(Identifier id)
	{
		super(new BiomeDefinition(id));
		this.biome = BuiltinRegistries.BIOME.get(id);
		
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
}