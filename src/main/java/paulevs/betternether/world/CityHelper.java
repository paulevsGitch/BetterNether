package paulevs.betternether.world;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import paulevs.betternether.world.structures.CityFeature;

public class CityHelper
{
	private static final Set<ChunkPos> POSITIONS = new HashSet<ChunkPos>(16);
	
	public static boolean stopStructGen(int chunkX, int chunkZ, ChunkGenerator chunkGenerator, long worldSeed, ChunkRandom chunkRandom)
	{
		StructureConfig config = chunkGenerator.getStructuresConfig().getForType(BNWorldGenerator.CITY);
		if (config != null) collectNearby(chunkX, chunkZ, config, worldSeed, chunkRandom);
		return stopGeneration(chunkX, chunkZ);
	}
	
	private static void collectNearby(int chunkX, int chunkZ, StructureConfig config, long worldSeed, ChunkRandom chunkRandom)
	{
		int x1 = chunkX - 16;
		int x2 = chunkX + 16;
		int z1 = chunkZ - 16;
		int z2 = chunkZ + 16;
		
		POSITIONS.clear();
		for (int x = x1; x <= x2; x += 8)
		{
			for (int z = z1; z <= z2; z += 8)
			{
				ChunkPos chunk = BNWorldGenerator.CITY.getStartChunk(config, worldSeed, chunkRandom, x, z);
				POSITIONS.add(chunk);
			}
		}
	}
	
	private static boolean stopGeneration(int chunkX, int chunkZ)
	{
		for (ChunkPos p: POSITIONS)
		{
			int dx = p.x - chunkX;
			int dz = p.z - chunkZ;
			if (dx * dx + dz * dz < CityFeature.RADIUS)
				return true;
		}
		return false;
	}
}
