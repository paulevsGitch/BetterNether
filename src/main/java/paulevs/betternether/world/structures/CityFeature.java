package paulevs.betternether.world.structures;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import paulevs.betternether.world.structures.city.CityGenerator;
import paulevs.betternether.world.structures.city.palette.Palettes;
import paulevs.betternether.world.structures.piece.CavePiece;
import paulevs.betternether.world.structures.piece.CityPiece;

import java.util.List;

public class CityFeature extends StructureFeature<DefaultFeatureConfig> {
	private static CityGenerator generator;
	public static final int RADIUS = 8 * 8;

	public CityFeature() {
		super(DefaultFeatureConfig.CODEC);
	}

	public static void initGenerator() {
		generator = new CityGenerator();
	}

	@Override
	public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
		return CityFeature.CityStart::new;
	}

	public static class CityStart extends StructureStart<DefaultFeatureConfig> {
		public CityStart(StructureFeature<DefaultFeatureConfig> structureFeature, ChunkPos chunkPos, int i, long l) {
			super(structureFeature, chunkPos, i, l);
		}

		@Override
		public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos cpos, Biome biome, DefaultFeatureConfig featureConfig, HeightLimitView heightLimitView) {
			int px = cpos.getOffsetX(8);
			int pz = cpos.getOffsetZ(8);
			int y = 40;
			if (chunkGenerator instanceof FlatChunkGenerator) {
				y = chunkGenerator.getHeight(px, pz, Type.WORLD_SURFACE, heightLimitView);
			}
			BlockPos center = new BlockPos(px, y, pz);


			// CityPalette palette = Palettes.getRandom(random);
			List<CityPiece> buildings = generator.generate(center, this.random, Palettes.EMPTY);
			BlockBox cityBox = BlockBox.infinite();
			for (CityPiece p : buildings)
				cityBox.encompass(p.getBoundingBox());

            int d1 = Math.max((center.getX() - cityBox.getMinX()), (cityBox.getMaxX() - center.getX()));
            int d2 = Math.max((center.getZ() - cityBox.getMinZ()), (cityBox.getMaxZ() - center.getZ()));
            int radius = Math.max(d1, d2);
            if (radius / 2 + center.getY() < cityBox.getMaxY()) {
				radius = (cityBox.getMaxY() - center.getY()) / 2;
			}

			if (!(chunkGenerator instanceof FlatChunkGenerator)) {
				CavePiece cave = new CavePiece(center, radius, random, cityBox);
				this.children.add(cave);
				this.children.addAll(buildings);
				//this.boundingBox = cave.getBoundingBox();
			}
			else {
				this.children.addAll(buildings);
				this.setBoundingBoxFromChildren();
			}

			this.setBoundingBoxFromChildren();

			/*this.boundingBox.maxZ -= 12;
			this.boundingBox.minZ += 12;
			this.boundingBox.minY -= 12;
			this.boundingBox.maxY += 12;*/
		}
	}
}
