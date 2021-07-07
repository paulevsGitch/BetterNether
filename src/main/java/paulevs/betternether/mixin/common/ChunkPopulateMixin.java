package paulevs.betternether.mixin.common;

import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin {
	private static final WorldgenRandom RANDOM = new WorldgenRandom();
	private static final MutableBlockPos POS = new MutableBlockPos();

	@Inject(method = "generateFeatures", at = @At("HEAD"), cancellable = true)
	private void customPopulate(WorldGenRegion region, StructureFeatureManager accessor, CallbackInfo info) {
		int chunkX = region.getCenter().x;
		int chunkZ = region.getCenter().z;
		if (!region.isClientSide() && isNetherBiome(region, chunkX, chunkZ) && region.getChunk(chunkX, chunkZ) != null) {
			RANDOM.setBaseChunkSeed(chunkX, chunkZ);
			int sx = chunkX << 4;
			int sz = chunkZ << 4;
			BNWorldGenerator.prePopulate(region, sx, sz, RANDOM);

			long featureSeed = RANDOM.setDecorationSeed(region.getSeed(), chunkX, chunkZ);
			ChunkGenerator generator = (ChunkGenerator) (Object) this;
			for (Biome biome : BNWorldGenerator.getPopulateBiomes()) {
				try {
					biome.generate(accessor, generator, region, featureSeed, RANDOM, new BlockPos(sx, 0, sz));
				}
				catch (Exception e) {
					CrashReport crashReport = CrashReport.forThrowable(e, "Biome decoration");
					crashReport
							.addCategory("Generation")
							.setDetail("CenterX", region.getCenter().x)
							.setDetail("CenterZ", region.getCenter().z)
							.setDetail("Seed", featureSeed)
							.setDetail("Biome", biome);
					throw new ReportedException(crashReport);
				}
			}

			BNWorldGenerator.populate(region, sx, sz, RANDOM);
			BNWorldGenerator.cleaningPass(region, sx, sz);

			info.cancel();
		}
	}

	private boolean isNetherBiome(WorldGenRegion world, int cx, int cz) {
		POS.set(cx << 4, 0, cx << 4);
		return isNetherBiome(world.getBiome(POS)) ||
				isNetherBiome(world.getBiome(POS.offset(0, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.offset(0, 0, 15))) ||
				isNetherBiome(world.getBiome(POS.offset(7, 0, 0))) ||
				isNetherBiome(world.getBiome(POS.offset(7, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.offset(7, 0, 15))) ||
				isNetherBiome(world.getBiome(POS.offset(15, 0, 0))) ||
				isNetherBiome(world.getBiome(POS.offset(15, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.offset(15, 0, 15)));
	}

	private boolean isNetherBiome(Biome biome) {
		return biome.getBiomeCategory() == BiomeCategory.NETHER;
	}
}
