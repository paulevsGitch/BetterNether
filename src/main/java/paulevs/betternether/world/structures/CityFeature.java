package paulevs.betternether.world.structures;

import java.util.List;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import paulevs.betternether.world.structures.city.CityGenerator;
import paulevs.betternether.world.structures.city.palette.Palettes;
import paulevs.betternether.world.structures.piece.CavePiece;
import paulevs.betternether.world.structures.piece.CityPiece;

public class CityFeature extends StructureFeature<NoneFeatureConfiguration> {
	private static CityGenerator generator;
	public static final int RADIUS = 8 * 8;

	public CityFeature() {
		super(NoneFeatureConfiguration.CODEC, CityFeature::generatePieces);
	}

	public static void initGenerator() {
		generator = new CityGenerator();
	}

	private static void generatePieces(StructurePiecesBuilder build, NoneFeatureConfiguration prob, PieceGenerator.Context context){
		final ChunkPos cpos = context.chunkPos();
		final ChunkGenerator chunkGenerator = context.chunkGenerator();
		final WorldgenRandom random = context.random();
		final LevelHeightAccessor heightLimitView = context.heightAccessor();

		int px = cpos.getBlockX(8);
		int pz = cpos.getBlockZ(8);
		int y = 40;
		if (chunkGenerator instanceof FlatLevelSource) {
			y = chunkGenerator.getBaseHeight(px, pz, Types.WORLD_SURFACE, heightLimitView);
		}
		BlockPos center = new BlockPos(px, y, pz);


		// CityPalette palette = Palettes.getRandom(random);
		List<CityPiece> buildings = generator.generate(center, random, Palettes.EMPTY);
		BoundingBox cityBox = BoundingBox.infinite();
		for (CityPiece p : buildings)
			cityBox.encapsulate(p.getBoundingBox());

		int d1 = Math.max((center.getX() - cityBox.minX()), (cityBox.maxX() - center.getX()));
		int d2 = Math.max((center.getZ() - cityBox.minZ()), (cityBox.maxZ() - center.getZ()));
		int radius = Math.max(d1, d2);
		if (radius / 2 + center.getY() < cityBox.maxY()) {
			radius = (cityBox.maxY() - center.getY()) / 2;
		}

		if (!(chunkGenerator instanceof FlatLevelSource)) {
			CavePiece cave = new CavePiece(center, radius, random, cityBox);
			build.addPiece(cave);
			buildings.forEach(b -> build.addPiece(b));
			//this.boundingBox = cave.getBoundingBox();
		}
		else {
			buildings.forEach(b -> build.addPiece(b));
			//this.getBoundingBox();
		}

		//this.getBoundingBox();

			/*this.boundingBox.maxZ -= 12;
			this.boundingBox.minZ += 12;
			this.boundingBox.minY -= 12;
			this.boundingBox.maxY += 12;*/
	}
}
