package org.betterx.betternether.world.structures.city;

import org.betterx.bclib.api.v2.levelgen.structures.BCLStructure;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.registry.NetherBiomes;
import org.betterx.betternether.registry.NetherStructures;
import org.betterx.betternether.world.structures.city.palette.Palettes;
import org.betterx.betternether.world.structures.piece.CavePiece;
import org.betterx.betternether.world.structures.piece.CityPiece;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class CityFeature extends Structure {
    private static CityGenerator generator;
    public static final int RADIUS = 8 * 8;

    public CityFeature(Structure.StructureSettings structureSettings) {
        super(structureSettings);
    }

    public static void initGenerator() {
        generator = new CityGenerator();
    }

    private static final int DEFAULT_HEIGHT = 40;

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (Configs.GENERATOR.getBoolean("generator.world.cities", "generate", true)
                && BCLStructure.isValidBiome(context, DEFAULT_HEIGHT)) {
            final ChunkPos cPos = context.chunkPos();
            final ChunkGenerator chunkGenerator = context.chunkGenerator();
            final LevelHeightAccessor heightAccessor = context.heightAccessor();

            final BlockPos center = getCenter(context, cPos, chunkGenerator, heightAccessor);
            return Optional.of(new Structure.GenerationStub(center, (structurePiecesBuilder) -> {
                generatePieces(structurePiecesBuilder, context);
            }));
        }
        return Optional.empty();
    }

    private static void generatePieces(
            StructurePiecesBuilder structurePiecesBuilder,
            Structure.GenerationContext context
    ) {
        final ChunkPos cPos = context.chunkPos();
        final ChunkGenerator chunkGenerator = context.chunkGenerator();
        final LevelHeightAccessor heightAccessor = context.heightAccessor();
        final WorldgenRandom random = context.random();

        final BlockPos center = getCenter(context, cPos, chunkGenerator, heightAccessor);

        //CityPalette palette = Palettes.getRandom(random);
        final List<CityPiece> buildings = generator.generate(center, random, Palettes.EMPTY);

        BoundingBox cityBox = new BoundingBox(center);
        for (CityPiece p : buildings)
            cityBox = cityBox.encapsulate(p.getBoundingBox());

        final int d1 = Math.max((center.getX() - cityBox.minX()), (cityBox.maxX() - center.getX()));
        final int d2 = Math.max((center.getZ() - cityBox.minZ()), (cityBox.maxZ() - center.getZ()));
        int radius = Math.max(d1, d2);
        if (radius / 2 + center.getY() < cityBox.maxY()) {
            radius = (cityBox.maxY() - center.getY()) / 2;
        }

        if (!(chunkGenerator instanceof FlatLevelSource)) {
            CavePiece cave = new CavePiece(
                    center,
                    radius + (NetherBiomes.useLegacyGeneration ? 0 : 8),
                    random,
                    cityBox
            );
            structurePiecesBuilder.addPiece(cave);
        }
        buildings.forEach(b -> structurePiecesBuilder.addPiece(b));

        //BetterNether.LOGGER.info("BBox after Cave:" + structurePiecesBuilder.getBoundingBox().toString());
    }

    @NotNull
    private static BlockPos getCenter(
            GenerationContext context,
            ChunkPos cPos,
            ChunkGenerator chunkGenerator,
            LevelHeightAccessor heightAccessor
    ) {
        final int px = cPos.getBlockX(8);
        final int pz = cPos.getBlockZ(8);
        final int y = chunkGenerator instanceof FlatLevelSource
                ? chunkGenerator.getBaseHeight(px, pz, Types.WORLD_SURFACE, heightAccessor, context.randomState())
                : DEFAULT_HEIGHT;
        final BlockPos center = new BlockPos(px, y, pz);
        return center;
    }


    @Override
    public StructureType<?> type() {
        return NetherStructures.CITY_STRUCTURE.structureType;
    }
}
