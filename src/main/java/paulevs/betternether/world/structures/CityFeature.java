package paulevs.betternether.world.structures;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import paulevs.betternether.world.structures.city.CityGenerator;
import paulevs.betternether.world.structures.city.palette.Palettes;
import paulevs.betternether.world.structures.piece.CavePiece;
import paulevs.betternether.world.structures.piece.CityPiece;

public class CityFeature extends StructureFeature<NoneFeatureConfiguration> {
	private static CityGenerator generator;
	public static final int RADIUS = 8 * 8;

	public CityFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	public static void initGenerator() {
		generator = new CityGenerator();
	}

	@Override
	public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
		return CityFeature.CityStart::new;
	}

	public static class CityStart extends StructureStart<NoneFeatureConfiguration> {
		public CityStart(StructureFeature<NoneFeatureConfiguration> structureFeature, ChunkPos chunkPos, int i, long l) {
			super(structureFeature, chunkPos, i, l);
		}

		@Override
		public void init(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos cpos, Biome biome, NoneFeatureConfiguration featureConfig, LevelHeightAccessor heightLimitView) {
			int px = cpos.getBlockX(8);
			int pz = cpos.getBlockZ(8);
			int y = 40;
			if (chunkGenerator instanceof FlatLevelSource) {
				y = chunkGenerator.getBaseHeight(px, pz, Types.WORLD_SURFACE, heightLimitView);
			}
			BlockPos center = new BlockPos(px, y, pz);


			// CityPalette palette = Palettes.getRandom(random);
			List<CityPiece> buildings = generator.generate(center, this.random, Palettes.EMPTY);
			BoundingBox cityBox = BoundingBox.infinite();
			for (CityPiece p : buildings)
				cityBox.encapsulate(p.getBoundingBox());

			int d1 = Math.max((center.getX() - cityBox.maxZ()), (cityBox.minZ() - center.getX()));
			int d2 = Math.max((center.getZ() - cityBox.minY()), (cityBox.maxY() - center.getZ()));
			int radius = Math.max(d1, d2);
			if (radius / 2 + center.getY() < cityBox.maxX()) {
				radius = (cityBox.maxX() - center.getY()) / 2;
			}

			if (!(chunkGenerator instanceof FlatLevelSource)) {
				CavePiece cave = new CavePiece(center, radius, random, cityBox);
				this.pieces.add(cave);
				this.pieces.addAll(buildings);
				//this.boundingBox = cave.getBoundingBox();
			}
			else {
				this.pieces.addAll(buildings);
				this.getBoundingBox();
			}

			this.getBoundingBox();

			/*this.boundingBox.maxZ -= 12;
			this.boundingBox.minZ += 12;
			this.boundingBox.minY -= 12;
			this.boundingBox.maxY += 12;*/
		}
	}
}
