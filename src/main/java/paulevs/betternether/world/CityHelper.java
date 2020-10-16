package paulevs.betternether.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import paulevs.betternether.world.structures.CityFeature;

public class CityHelper {
	private static final Set<ChunkPos> POSITIONS = new HashSet<ChunkPos>(16);

	public static boolean stopStructGen(int chunkX, int chunkZ, ChunkGenerator chunkGenerator, long worldSeed, ChunkRandom chunkRandom) {
		StructureConfig config = chunkGenerator.getStructuresConfig().getForType(BNWorldGenerator.CITY);
		if (config != null && config.getSpacing() > 0) collectNearby(chunkX, chunkZ, config, worldSeed, chunkRandom);
		return stopGeneration(chunkX, chunkZ);
	}

	private static void collectNearby(int chunkX, int chunkZ, StructureConfig config, long worldSeed, ChunkRandom chunkRandom) {
		int x1 = chunkX - 16;
		int x2 = chunkX + 16;
		int z1 = chunkZ - 16;
		int z2 = chunkZ + 16;

		POSITIONS.clear();
		for (int x = x1; x <= x2; x += 8) {
			for (int z = z1; z <= z2; z += 8) {
				ChunkPos chunk = BNWorldGenerator.CITY.getStartChunk(config, worldSeed, chunkRandom, x, z);
				POSITIONS.add(chunk);
			}
		}
	}

	private static boolean stopGeneration(int chunkX, int chunkZ) {
		for (ChunkPos p : POSITIONS) {
			int dx = p.x - chunkX;
			int dz = p.z - chunkZ;
			if (dx * dx + dz * dz < CityFeature.RADIUS)
				return true;
		}
		return false;
	}

	private static long sqr(int x) {
		return (long) x * (long) x;
	}

	public static BlockPos getNearestCity(BlockPos pos, ServerWorld world) {
		int cx = pos.getX() >> 4;
		int cz = pos.getZ() >> 4;

		StructureConfig config = world.getChunkManager().getChunkGenerator().getStructuresConfig().getForType(BNWorldGenerator.CITY);
		if (config == null || config.getSpacing() < 1)
			return null;

		collectNearby(cx, cz, config, world.getSeed(), new ChunkRandom());
		Iterator<ChunkPos> iterator = POSITIONS.iterator();
		if (iterator.hasNext()) {
			ChunkPos nearest = POSITIONS.iterator().next();
			long d = sqr(nearest.x - cx) + sqr(nearest.z - cz);
			while (iterator.hasNext()) {
				ChunkPos n = iterator.next();
				long d2 = sqr(n.x - cx) + sqr(n.z - cz);
				if (d2 < d) {
					d = d2;
					nearest = n;
				}
			}
			return nearest.getStartPos();
		}
		return null;
	}
}
