package paulevs.betternether.mixin.common;

import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.world.BNWorldGenerator;

import java.time.Instant;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin {
	// TODO: Seting the seed to a fixed value might break things, but it should never get used in this state
	private static final WorldgenRandom RANDOM = new WorldgenRandom(new LegacyRandomSource(Instant.now().getEpochSecond()));
	private static final MutableBlockPos POS = new MutableBlockPos();

	@Inject(method = "applyBiomeDecoration", at = @At("HEAD"), cancellable = true)
	private void customPopulate(WorldGenLevel worldGenLevel, ChunkPos chunkPos, StructureFeatureManager structureFeatureManager, CallbackInfo ci) {
		//ChunkPos chunkPos = region.getCenter();
		final int chunkX = chunkPos.x;
		final int chunkZ = chunkPos.z;
		if (!worldGenLevel.isClientSide() && isNetherBiome(worldGenLevel, chunkX, chunkZ) && worldGenLevel.getChunk(chunkX, chunkZ) != null) {
			RANDOM.setBaseChunkSeed(chunkX, chunkZ);
			final int sx = chunkX << 4;
			final int sz = chunkZ << 4;
			BNWorldGenerator.prePopulate(worldGenLevel, sx, sz, RANDOM);

			long featureSeed = RANDOM.setDecorationSeed(worldGenLevel.getSeed(), chunkX, chunkZ);
			ChunkGenerator generator = (ChunkGenerator) (Object) this;
			for (Biome biome : BNWorldGenerator.getPopulateBiomes()) {
				try {
					biome.generate(accessor, generator, worldGenLevel, featureSeed, RANDOM, new BlockPos(sx, worldGenLevel.getMinBuildHeight(), sz));
				}
				catch (Exception e) {
					CrashReport crashReport = CrashReport.forThrowable(e, "Biome decoration");
					crashReport
							.addCategory("Generation")
							.setDetail("CenterX", chunkX)
							.setDetail("CenterZ", chunkZ)
							.setDetail("Seed", featureSeed)
							.setDetail("Biome", biome);
					throw new ReportedException(crashReport);
				}
			}

			BNWorldGenerator.populate(worldGenLevel, sx, sz, RANDOM);
			BNWorldGenerator.cleaningPass(worldGenLevel, sx, sz);

			ci.cancel();
		}
	}

	private boolean isNetherBiome(WorldGenLevel world, int cx, int cz) {
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
