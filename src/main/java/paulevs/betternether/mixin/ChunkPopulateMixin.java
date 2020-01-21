package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin<C extends ChunkGeneratorConfig>
{
	private static final ChunkRandom RANDOM = new ChunkRandom();

	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onConstructed(IWorld world, BiomeSource biomeSource, C config, CallbackInfo ci)
	{
		if (world.getDimension().isNether())
			BNWorldGenerator.init(world);
	}
	
	@Inject(method = "generateFeatures", at = @At("HEAD"))
    private void customPrePopulate(ChunkRegion region, CallbackInfo info)
	{
		if (!region.isClient() && region.getDimension().isNether())
		{
			int chunkX = region.getCenterChunkX();
			int chunkZ = region.getCenterChunkZ();
			BNWorldGenerator.smoothChunk(region, chunkX, chunkZ);
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
			BNWorldGenerator.generate(region, chunkX, chunkZ, RANDOM);
			BNWorldGenerator.cleaningPass(region, chunkX, chunkZ);
			BNWorldGenerator.clearCache();
		}
    }
	
	private boolean isNetherBiome(IWorld world, int cx, int cz)
	{
		BlockPos start = new BlockPos(cx << 4, 0, cx << 4);
		return  world.getBiome(start) instanceof NetherBiome ||
				world.getBiome(start.add(0, 0, 7)) instanceof NetherBiome ||
				world.getBiome(start.add(0, 0, 15)) instanceof NetherBiome ||
				world.getBiome(start.add(7, 0, 0)) instanceof NetherBiome ||
				world.getBiome(start.add(7, 0, 7)) instanceof NetherBiome ||
				world.getBiome(start.add(7, 0, 15)) instanceof NetherBiome ||
				world.getBiome(start.add(15, 0, 0)) instanceof NetherBiome ||
				world.getBiome(start.add(15, 0, 7)) instanceof NetherBiome ||
				world.getBiome(start.add(15, 0, 15)) instanceof NetherBiome;
	}
}
