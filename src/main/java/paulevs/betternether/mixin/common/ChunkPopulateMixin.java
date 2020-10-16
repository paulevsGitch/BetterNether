package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin {
	private static final ChunkRandom RANDOM = new ChunkRandom();
	private static final Mutable POS = new Mutable();

	@Inject(method = "generateFeatures", at = @At("HEAD"), cancellable = true)
	private void customPopulate(ChunkRegion region, StructureAccessor accessor, CallbackInfo info) {
		int chunkX = region.getCenterChunkX();
		int chunkZ = region.getCenterChunkZ();
		if (!region.isClient() && isNetherBiome(region, chunkX, chunkZ)) {
			RANDOM.setTerrainSeed(chunkX, chunkZ);
			int sx = chunkX << 4;
			int sz = chunkZ << 4;
			BNWorldGenerator.prePopulate(region, sx, sz, RANDOM);

			long featureSeed = RANDOM.setPopulationSeed(region.getSeed(), chunkX, chunkZ);
			ChunkGenerator generator = (ChunkGenerator) (Object) this;
			for (Biome biome : BNWorldGenerator.getPopulateBiomes()) {
				try {
					biome.generateFeatureStep(accessor, generator, region, featureSeed, RANDOM, new BlockPos(sx, 0, sz));
				}
				catch (Exception e) {
					CrashReport crashReport = CrashReport.create(e, "Biome decoration");
					crashReport
							.addElement("Generation")
							.add("CenterX", region.getCenterChunkX())
							.add("CenterZ", region.getCenterChunkZ())
							.add("Seed", featureSeed)
							.add("Biome", biome);
					throw new CrashException(crashReport);
				}
			}

			BNWorldGenerator.populate(region, sx, sz, RANDOM);
			BNWorldGenerator.cleaningPass(region, sx, sz);

			info.cancel();
		}
	}

	private boolean isNetherBiome(ChunkRegion world, int cx, int cz) {
		POS.set(cx << 4, 0, cx << 4);
		return isNetherBiome(world.getBiome(POS)) ||
				isNetherBiome(world.getBiome(POS.add(0, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.add(0, 0, 15))) ||
				isNetherBiome(world.getBiome(POS.add(7, 0, 0))) ||
				isNetherBiome(world.getBiome(POS.add(7, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.add(7, 0, 15))) ||
				isNetherBiome(world.getBiome(POS.add(15, 0, 0))) ||
				isNetherBiome(world.getBiome(POS.add(15, 0, 7))) ||
				isNetherBiome(world.getBiome(POS.add(15, 0, 15)));
	}

	private boolean isNetherBiome(Biome biome) {
		return biome.getCategory() == Category.NETHER;
	}
}
