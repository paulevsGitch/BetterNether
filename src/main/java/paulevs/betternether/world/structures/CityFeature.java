package paulevs.betternether.world.structures;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.VillageGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.VillageFeatureConfig;

public class CityFeature extends StructureFeature<VillageFeatureConfig>
{
	public CityFeature(Function<Dynamic<?>, ? extends VillageFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	protected ChunkPos getStart(ChunkGenerator<?> chunkGenerator, Random random, int i, int j, int k, int l)
	{
		int m = chunkGenerator.getConfig().getVillageDistance();
		int n = chunkGenerator.getConfig().getVillageSeparation();
		int o = i + m * k;
		int p = j + m * l;
		int q = o < 0 ? o - m + 1 : o;
		int r = p < 0 ? p - m + 1 : p;
		int s = q / m;
		int t = r / m;
		((ChunkRandom)random).setStructureSeed(chunkGenerator.getSeed(), s, t, 10387312);
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
		return chunkZ == chunkPos.x && i == chunkPos.z ? chunkGenerator.hasStructure(biome, this) : false;
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
		return 8;
	}

	public static class CityStart extends StructureStart
	{
		public CityStart(StructureFeature<?> structureFeature, int chunkX, int chunkZ, BlockBox blockBox, int i, long l)
		{
			super(structureFeature, chunkX, chunkZ, blockBox, i, l);
		}

		public void initialize(ChunkGenerator<?> chunkGenerator, StructureManager structureManager, int x, int z, Biome biome)
		{
			VillageFeatureConfig villageFeatureConfig = (VillageFeatureConfig) chunkGenerator.getStructureConfig(biome, Feature.VILLAGE);
			BlockPos blockPos = new BlockPos(x * 16, 0, z * 16);
			VillageGenerator.addPieces(chunkGenerator, structureManager, blockPos, this.children, this.random, villageFeatureConfig);
			this.setBoundingBoxFromChildren();
		}
	}
}
