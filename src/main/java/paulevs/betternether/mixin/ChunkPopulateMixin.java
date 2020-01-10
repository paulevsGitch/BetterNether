package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
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
    private void customPopulate(ChunkRegion region, CallbackInfo info)
	{
		if (region.getDimension().isNether())
		{
			int chunkX = region.getCenterChunkX();
			int chunkZ = region.getCenterChunkZ();
			RANDOM.setSeed(chunkX, chunkZ);
			BNWorldGenerator.smoothChunk(region, chunkX, chunkZ);
			BNWorldGenerator.generate(region, chunkX, chunkZ, RANDOM);
		}
    }
}
