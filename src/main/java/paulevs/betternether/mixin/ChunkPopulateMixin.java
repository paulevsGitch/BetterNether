package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin<C extends ChunkGeneratorConfig>
{
	private static final ChunkRandom RANDOM = new ChunkRandom();
	
	@Shadow
	@Final
	protected IWorld world;

	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onConstructed(IWorld world, BiomeSource biomeSource, C config, CallbackInfo ci)
	{
		BNWorldGenerator.init(world);
	}
	
	@Inject(method = "generateFeatures", at = @At("HEAD"))
    private void customPrePopulate(ChunkRegion region, CallbackInfo info)
	{
		int chunkX = region.getCenterChunkX();
		int chunkZ = region.getCenterChunkZ();
		if (!region.isClient() && isNetherBiome(region, chunkX, chunkZ))
		{
			if (region.getDimension().isNether())
				BNWorldGenerator.prePopulate(region, chunkX, chunkZ);
		}
    }
	
	@Inject(method = "generateFeatures", at = @At("RETURN"))
    private void customPopulate(ChunkRegion region, CallbackInfo info)
	{
		int chunkX = region.getCenterChunkX();
		int chunkZ = region.getCenterChunkZ();
		if (!region.isClient() && isNetherBiome(region, chunkX, chunkZ))
		{
			RANDOM.setSeed(chunkX, chunkZ);
			int sx = chunkX << 4;
			int sz = chunkZ << 4;
			BNWorldGenerator.populate(region, sx, sz, RANDOM);
			BNWorldGenerator.cleaningPass(region, sx, sz);
			BNWorldGenerator.clearCache();
		}
	}

	private boolean isNetherBiome(IWorld world, int cx, int cz)
	{
		BlockPos start = new BlockPos(cx << 4, 0, cx << 4);
		return  isNetherBiome(world.getBiome(start)) ||
				isNetherBiome(world.getBiome(start.add(0, 0, 7))) ||
				isNetherBiome(world.getBiome(start.add(0, 0, 15))) ||
				isNetherBiome(world.getBiome(start.add(7, 0, 0))) ||
				isNetherBiome(world.getBiome(start.add(7, 0, 7))) ||
				isNetherBiome(world.getBiome(start.add(7, 0, 15))) ||
				isNetherBiome(world.getBiome(start.add(15, 0, 0))) ||
				isNetherBiome(world.getBiome(start.add(15, 0, 7))) ||
				isNetherBiome(world.getBiome(start.add(15, 0, 15)));
	}

	private boolean isNetherBiome(Biome biome)
	{
		return  biome == Biomes.NETHER_WASTES ||
				biome == Biomes.CRIMSON_FOREST ||
				biome == Biomes.WARPED_FOREST;
	}
}
