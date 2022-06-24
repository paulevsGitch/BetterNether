package org.betterx.betternether.world.structures.city;

import org.betterx.betternether.registry.NetherStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;

import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CityHelper {
    private static final Set<ChunkPos> POSITIONS = Collections.newSetFromMap(new ConcurrentHashMap<ChunkPos, Boolean>(16));

    public static boolean stopStructGen(
            int chunkX,
            int chunkZ,
            ChunkGenerator chunkGenerator,
            long worldSeed,
            WorldgenRandom chunkRandom
    ) {
        if (NetherStructures.CITY_STRUCTURE.spreadConfig instanceof RandomSpreadStructurePlacement config) {
            if (config != null && config.spacing() > 0) collectNearby(chunkX, chunkZ, config, worldSeed, chunkRandom);
        }
        return stopGeneration(chunkX, chunkZ);
    }

    private static void collectNearby(
            int chunkX,
            int chunkZ,
            RandomSpreadStructurePlacement config,
            long worldSeed,
            WorldgenRandom chunkRandom
    ) {
        int x1 = chunkX - 16;
        int x2 = chunkX + 16;
        int z1 = chunkZ - 16;
        int z2 = chunkZ + 16;

        POSITIONS.clear();
        for (int x = x1; x <= x2; x += 8) {
            for (int z = z1; z <= z2; z += 8) {
                ChunkPos chunk = config.getPotentialStructureChunk(worldSeed, x, z);
                POSITIONS.add(chunk);
            }
        }
    }

    private static void collectNearby(
            ServerLevel world,
            int chunkX,
            int chunkZ,
            RandomSpreadStructurePlacement config,
            long worldSeed,
            WorldgenRandom chunkRandom
    ) {
        final MutableBlockPos POS = new MutableBlockPos();

        int x1 = chunkX - 16;
        int x2 = chunkX + 16;
        int z1 = chunkZ - 16;
        int z2 = chunkZ + 16;

        POSITIONS.clear();
        POS.setY(64);
        for (int x = x1; x <= x2; x += 8) {
            POS.setX(x << 4);
            for (int z = z1; z <= z2; z += 8) {
                POS.setZ(z << 4);
                ChunkPos chunk = config.getPotentialStructureChunk(worldSeed, x, z);
                POSITIONS.add(chunk);
            }
        }
    }

    private static boolean stopGeneration(int chunkX, int chunkZ) {
        for (ChunkPos p : POSITIONS) {
            int dx = p.x - chunkX;
            int dz = p.z - chunkZ;
            if (dx * dx + dz * dz < CityStructure.RADIUS)
                return true;
        }
        return false;
    }

    private static long sqr(int x) {
        return (long) x * (long) x;
    }

    public static BlockPos getNearestCity(BlockPos pos, ServerLevel world) {
        int cx = pos.getX() >> 4;
        int cz = pos.getZ() >> 4;

        if (NetherStructures.CITY_STRUCTURE.spreadConfig instanceof RandomSpreadStructurePlacement config) {
            if (config == null || config.spacing() < 1)
                return null;

            collectNearby(
                    world,
                    cx,
                    cz,
                    config,
                    world.getSeed(),
                    new WorldgenRandom(new LegacyRandomSource(Instant.now().getEpochSecond()))
            );
        }

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
            return nearest.getWorldPosition();
        }
        return null;
    }
}
