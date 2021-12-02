package paulevs.betternether.world.structures;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.JunglePyramidFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier.Context;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import paulevs.betternether.config.Configs;
import paulevs.betternether.registry.NetherBiomes;
import paulevs.betternether.world.structures.city.CityGenerator;
import paulevs.betternether.world.structures.city.palette.Palettes;
import paulevs.betternether.world.structures.piece.CavePiece;
import paulevs.betternether.world.structures.piece.CityPiece;

public class CityFeature extends StructureFeature<NoneFeatureConfiguration> {
	private static CityGenerator generator;
	public static final int RADIUS = 8 * 8;
	
	public CityFeature() {
		super(
			NoneFeatureConfiguration.CODEC,
			PieceGeneratorSupplier.simple(CityFeature::checkLocation, CityFeature::generatePieces)
		);
	}
	
	public static void initGenerator() {
		generator = new CityGenerator();
	}
	
	private static <C extends FeatureConfiguration> boolean checkLocation(Context<C> context) {
		return Configs.GENERATOR.getBoolean("generator.world.cities", "generate", true) &&
			context.getLowestY(12, 15) >= context.chunkGenerator().getSeaLevel();
	}
	
	private static void generatePieces(StructurePiecesBuilder structurePiecesBuilder, net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator.Context<NoneFeatureConfiguration> context) {
		final ChunkPos cPos = context.chunkPos();
		final ChunkGenerator chunkGenerator = context.chunkGenerator();
		final LevelHeightAccessor heightAccessor = context.heightAccessor();
		final WorldgenRandom random = context.random();
		
		final int px = cPos.getBlockX(8);
		final int pz = cPos.getBlockZ(8);
		final int y = chunkGenerator instanceof FlatLevelSource
			? chunkGenerator.getBaseHeight(px, pz, Types.WORLD_SURFACE, heightAccessor)
			: 40;
		final BlockPos center = new BlockPos(px, y, pz);

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
			CavePiece cave = new CavePiece(center, radius+(NetherBiomes.useLegacyGeneration?0:8), random, cityBox);
			structurePiecesBuilder.addPiece(cave);
			
		}
		buildings.forEach(b -> structurePiecesBuilder.addPiece(b));
		
		//BetterNether.LOGGER.info("BBox after Cave:" + this.getBoundingBox().toString());
	}
	
}
