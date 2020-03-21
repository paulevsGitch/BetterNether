package paulevs.betternether.mixin.common;

import java.util.HashSet;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin<C extends ChunkGeneratorConfig>
{
	private static final ChunkRandom RANDOM = new ChunkRandom();
	private static final HashSet<Biome> BIOMES = new HashSet<Biome>();
	private static final Mutable POS = new Mutable();
	
	@Shadow
	@Final
	protected IWorld world;

	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onConstructed(IWorld world, BiomeSource biomeSource, C config, CallbackInfo ci)
	{
		BNWorldGenerator.init(world);
	}

	@Inject(method = "populateBiomes", at = @At("RETURN"))
	public void customBiomes(Chunk chunk, CallbackInfo info)
	{
		if (isNetherBiome(chunk))
			BNWorldGenerator.generateCustomBiomes(world, chunk);
	}

	@Inject(method = "generateFeatures", at = @At("HEAD"), cancellable = true)
    private void customPopulate(ChunkRegion region, CallbackInfo info)
	{
		int chunkX = region.getCenterChunkX();
		int chunkZ = region.getCenterChunkZ();
		if (!region.isClient() && isNetherBiome(region, chunkX, chunkZ))
		{
			RANDOM.setTerrainSeed(chunkX, chunkZ);
			int sx = chunkX << 4;
			int sz = chunkZ << 4;
			BNWorldGenerator.prePopulate(region, sx, sz);
			
			getBiomeSet(region, sx, sz);
			GenerationStep.Feature[] steps = GenerationStep.Feature.values();
			long featureSeed = RANDOM.setPopulationSeed(region.getSeed(), chunkX, chunkZ);
			@SuppressWarnings("unchecked")
			ChunkGenerator<C> generator = (ChunkGenerator<C>) (Object) this;
			for (Biome biome: BIOMES)
			{
				for (int step = 0; step < steps.length; step ++)
				{
					GenerationStep.Feature feature = steps[step];

					try
					{
						biome.generateFeatureStep(feature, generator, region, featureSeed, RANDOM, new BlockPos(sx, 0, sz));
					}
					catch (Exception e)
					{
						CrashReport crashReport = CrashReport.create(e, "Biome decoration");
						crashReport
							.addElement("Generation")
							.add("CenterX", chunkX)
							.add("CenterZ", chunkZ)
							.add("Step", feature)
							.add("Seed", featureSeed)
							.add("Biome", Registry.BIOME.getId(biome));
						throw new CrashException(crashReport);
					}
				}
			}
			
			BNWorldGenerator.populate(region, sx, sz, RANDOM);
			BNWorldGenerator.cleaningPass(region, sx, sz);
			BNWorldGenerator.clearCache();

			info.cancel();
		}
	}

	private boolean isNetherBiome(IWorld world, int cx, int cz)
	{
		POS.set(cx << 4, 0, cx << 4);
		return  isNetherBiome(world.getBiome(POS)) ||
				isNetherBiome(world.getBiome(POS.add(0, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.add(0, 0, 15))) ||
				isNetherBiome(world.getBiome(POS.add(7, 0, 0))) ||
				isNetherBiome(world.getBiome(POS.add(7, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.add(7, 0, 15))) ||
				isNetherBiome(world.getBiome(POS.add(15, 0, 0))) ||
				isNetherBiome(world.getBiome(POS.add(15, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.add(15, 0, 15)));
	}
	
	private boolean isNetherBiome(Chunk chunk)
	{
		for (int x = 0; x < 4; x++)
			for (int z = 0; z < 4; z++)
				if (isNetherBiome(chunk.getBiomeArray().getBiomeForNoiseGen(x, 0, z)))
					return  true;
		return false;
	}
	
	private void getBiomeSet(IWorld world, int sx, int sz)
	{
		BIOMES.clear();
		for (int x = 0; x < 16; x += 4)
		{
			POS.setX(sx | x);
			for (int z = 0; z < 16; z += 4)
			{
				POS.setZ(sz | z);
				for (int y = 0; y < 128; y += 4)
				{
					POS.setY(y);
					BIOMES.add(world.getBiome(POS));
				}
			}
		}
	}

	private boolean isNetherBiome(Biome biome)
	{
		return biome.getCategory() == Category.NETHER;
	}
}
