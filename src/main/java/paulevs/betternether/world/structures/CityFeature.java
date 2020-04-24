package paulevs.betternether.world.structures;

import java.util.List;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import paulevs.betternether.world.structures.city.CityGenerator;
import paulevs.betternether.world.structures.piece.CavePiece;
import paulevs.betternether.world.structures.piece.CityPiece;

public class CityFeature extends StructureFeature<DefaultFeatureConfig>
{
	private static final CityGenerator GENERATOR = new CityGenerator();
	
	public CityFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	protected int getSpacing(DimensionType dimensionType, ChunkGeneratorConfig chunkGeneratorConfig)
	{
		return 64;
	}

	protected int getSeparation(DimensionType dimensionType, ChunkGeneratorConfig chunkGeneratorConfig)
	{
		return 32;
	}

	protected int getSeedModifier(ChunkGeneratorConfig chunkGeneratorConfig)
	{
		return 897527;
	}

	@Override
	public net.minecraft.world.gen.feature.StructureFeature.StructureStartFactory getStructureStartFactory()
	{
		return CityFeature.CityStart::new;
	}

	@Override
	public String getName()
	{
		return "Nether City";
	}

	@Override
	public int getRadius()
	{
		return 16;
	}

	public static class CityStart extends StructureStart
	{
		public CityStart(StructureFeature<?> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l)
		{
			super(structureFeature, chunkX, chunkZ, blockBox, i, l);
		}

		public void init(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome)
		{
			int px = (x << 4) | 8;
			int pz = (z << 4) | 8;
			int y = 40;
			if (chunkGenerator instanceof FlatChunkGenerator)
			{
				BlockState[] layers = ((FlatChunkGenerator) chunkGenerator).getConfig().getLayerBlocks();
				for (int i = 255; i >= 0; i--)
					if (layers[i] != null && !layers[i].isAir())
					{
						y = i + 10;
						break;
					}
			}
			
			BlockPos center = new BlockPos(px, y, pz);
			
			List<CityPiece> buildings = GENERATOR.generate(center, this.random);
			BlockBox cityBox = BlockBox.empty();
			for (CityPiece p: buildings)
				cityBox.encompass(p.getBoundingBox());
			
			int d1 = Math.max((center.getX() - cityBox.minX), (cityBox.maxX - center.getX()));
			int d2 = Math.max((center.getZ() - cityBox.minZ), (cityBox.maxZ - center.getZ()));
			int radius = Math.max(d1, d2);
			if (radius / 2 + center.getY() < cityBox.maxY)
			{
				radius = (cityBox.maxY - center.getY()) / 2;
			}
			
			if (!(chunkGenerator instanceof FlatChunkGenerator))
			{
				CavePiece cave = new CavePiece(center, radius, random);
				this.children.add(cave);
				this.children.addAll(buildings);
				this.boundingBox = cave.getBoundingBox();
			}
			else
			{
				this.children.addAll(buildings);
				this.setBoundingBoxFromChildren();
			}
			this.boundingBox.minX -= 12;
			this.boundingBox.maxX += 12;
			this.boundingBox.minZ -= 12;
			this.boundingBox.maxZ += 12;
		}
	}
}
