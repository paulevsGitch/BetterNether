package paulevs.betternether.world.structures;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
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

	protected ChunkPos getStart(ChunkGenerator<?> chunkGenerator, Random random, int chunkX, int chunkZ, int scaleX, int scaleZ)
	{
		int m = 64;//chunkGenerator.getConfig().getVillageDistance();
		int n = 32;//chunkGenerator.getConfig().getVillageSeparation();
		int o = chunkX + m * scaleX;
		int p = chunkZ + m * scaleZ;
		int q = o < 0 ? o - m + 1 : o;
		int r = p < 0 ? p - m + 1 : p;
		int s = q / m;
		int t = r / m;
		((ChunkRandom)random).setStructureSeed(chunkGenerator.getSeed(), s, t, 897527);
		s *= m;
		t *= m;
		s += random.nextInt(m - n);
		t += random.nextInt(m - n);
		return new ChunkPos(s, t);
	}

	@Override
	public boolean shouldStartAt(BiomeAccess biomeAccess, ChunkGenerator<?> chunkGenerator, Random random, int chunkZ, int i, Biome biome)
	{
		ChunkPos chunkPos = this.getStart(chunkGenerator, random, chunkZ, i, 0, 0);
		return chunkZ == chunkPos.x && i == chunkPos.z && chunkGenerator.hasStructure(biome, this);
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
		return 12;
	}

	public static class CityStart extends StructureStart
	{
		public CityStart(StructureFeature<?> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l)
		{
			super(structureFeature, chunkX, chunkZ, blockBox, i, l);
		}

		public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome)
		{
			BlockPos center = new BlockPos(x << 4, 40, z << 4);
			System.out.println("New generator for " + center);
			
			List<CityPiece> buildings = GENERATOR.generate(center, this.random);
			BlockBox cityBox = BlockBox.empty();
			for (CityPiece p: buildings)
				cityBox.encompass(p.getBoundingBox());
			
			int radius = (int) (Math.max(cityBox.maxX - cityBox.minX, cityBox.maxZ - cityBox.minZ) * 1.2F * 0.5F);
			if (cityBox.maxY > radius * 0.5F + center.getY())
			{
				radius = (int) (cityBox.maxY * 1.2F * 0.5F);
			}
			System.out.println("Radius: " + radius + " " + Math.ceil(radius / 16));
			
			this.children.add(new CavePiece(center, radius, random));
			this.children.addAll(buildings);
			this.setBoundingBoxFromChildren();
			this.boundingBox.minX -= 12;
			this.boundingBox.maxX += 12;
			this.boundingBox.minZ -= 12;
			this.boundingBox.maxZ += 12;
		}
	}
}
