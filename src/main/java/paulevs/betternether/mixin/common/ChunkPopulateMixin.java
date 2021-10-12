package paulevs.betternether.mixin.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.world.BNWorldGenerator;

import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(ChunkGenerator.class)
public abstract class ChunkPopulateMixin {
	// TODO: Seting the seed to a fixed value might break things, but it should never get used in this state
	private static final WorldgenRandom RANDOM = new WorldgenRandom(new LegacyRandomSource(Instant.now().getEpochSecond()));
	private static final MutableBlockPos POS = new MutableBlockPos();
	@Shadow @Final protected BiomeSource biomeSource;

	@Inject(method = "applyBiomeDecoration", at = @At("HEAD"), cancellable = true)
	public void bclib_applyBiomeDecoration(WorldGenLevel worldGenLevel, ChunkPos chunkPos, StructureFeatureManager manager, CallbackInfo ci) {
		ChunkGenerator self = (ChunkGenerator)(Object)this;
		int chunkX = chunkPos.x;
		int chunkZ = chunkPos.z;
		int blockX = chunkPos.getMinBlockX();
		int blockZ = chunkPos.getMinBlockZ();
		if (!worldGenLevel.isClientSide() && isNetherBiome(worldGenLevel, chunkX, chunkZ) && worldGenLevel.getChunk(chunkX, chunkZ) != null) {
			if (!SharedConstants.debugVoidTerrain(blockX, blockZ)) {
				BlockPos blockPos = new BlockPos(blockX, worldGenLevel.getMinBuildHeight(), blockZ);
				int sectionX = SectionPos.blockToSectionCoord(blockPos.getX());
				int sectionZ = SectionPos.blockToSectionCoord(blockPos.getZ());
				int sx = SectionPos.sectionToBlockCoord(sectionX);
				int sz = SectionPos.sectionToBlockCoord(sectionZ);
				int minY = worldGenLevel.getMinBuildHeight() + 1;
				int maxY = worldGenLevel.getMaxBuildHeight() - 1;
				BNWorldGenerator.prePopulate(worldGenLevel, sx, sz, RANDOM);

				Map<Integer, List<StructureFeature<?>>> map = Registry.STRUCTURE_FEATURE.stream().collect(Collectors.groupingBy((item) -> item.step().ordinal()));

				WorldgenRandom random = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
				long seed = random.setDecorationSeed(worldGenLevel.getSeed(), blockX, blockZ);

				//TODO: This loop does currently not depend on biome
				//for (Biome biome : BNWorldGenerator.getPopulateBiomes())
				{
					ImmutableList<ImmutableList<ConfiguredFeature<?, ?>>> featuresPerStep = biomeSource.featuresPerStep();
					try {
						Registry<ConfiguredFeature<?, ?>> configured = worldGenLevel.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
						Registry<StructureFeature<?>> structureFeatures = worldGenLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_FEATURE_REGISTRY);
						int decors = Math.max(GenerationStep.Decoration.values().length, featuresPerStep.size());

						for (int i = 0; i < decors; ++i) {
							int j = 0;
							CrashReportCategory category;
							if (manager.shouldGenerateFeatures()) {
								List<StructureFeature<?>> features = (List) map.getOrDefault(i, Collections.emptyList());

								for (Iterator iter = features.iterator(); iter.hasNext(); ++j) {
									StructureFeature<?> feature = (StructureFeature) iter.next();
									random.setFeatureSeed(seed, j, i);
									Supplier getName = () -> {
										Optional optionalKey = structureFeatures.getResourceKey(feature).map(Object::toString);
										Objects.requireNonNull(feature);
										return (String) optionalKey.orElseGet(feature::toString);
									};

									try {
										worldGenLevel.setCurrentlyGenerating(getName);
										manager.startsForFeature(SectionPos.of(blockPos), feature).forEach((item) -> {
											item.placeInChunk(worldGenLevel, manager, self, random, new BoundingBox(sx, minY, sz, sx + 15, maxY, sz + 15), new ChunkPos(sectionX, sectionZ));
										});
									} catch (Exception ee) {
										CrashReport crashReport = CrashReport.forThrowable(ee, "Feature placement");
										category = crashReport.addCategory("Feature");
										Objects.requireNonNull(getName);
										category.setDetail("Description", getName.get());
										throw new ReportedException(crashReport);
									}
								}
							}

							if (featuresPerStep.size() > i) {
								for (UnmodifiableIterator iter = ((ImmutableList) featuresPerStep.get(i)).iterator(); iter.hasNext(); ++j) {
									ConfiguredFeature<?, ?> feature = (ConfiguredFeature) iter.next();
									Supplier<String> getName = () -> {
										Optional optionalKey = configured.getResourceKey(feature).map(Object::toString);
										Objects.requireNonNull(feature);
										return (String) optionalKey.orElseGet(feature::toString);
									};
									random.setFeatureSeed(seed, j, i);

									try {
										worldGenLevel.setCurrentlyGenerating(getName);
										feature.placeWithBiomeCheck(Optional.of(feature), worldGenLevel, self, random, blockPos);
									} catch (Exception ee) {
										CrashReport crashReport = CrashReport.forThrowable(ee, "Feature placement");
										category = crashReport.addCategory("Feature");
										Objects.requireNonNull(getName);
										category.setDetail("Description", getName::get);
										throw new ReportedException(crashReport);
									}
								}
							}
						}

						worldGenLevel.setCurrentlyGenerating((Supplier) null);
					} catch (Exception e) {
						CrashReport crashReport = CrashReport.forThrowable(e, "Biome decoration");
						crashReport.addCategory("Generation").setDetail("CenterX", (Object) chunkX).setDetail("CenterZ", (Object) chunkZ).setDetail("Seed", (Object) seed);
						throw new ReportedException(crashReport);
					}
				}

				BNWorldGenerator.populate(worldGenLevel, sx, sz, RANDOM);
				BNWorldGenerator.cleaningPass(worldGenLevel, sx, sz);
			}




			ci.cancel();
		}
	}

//	private void customPopulate(WorldGenLevel worldGenLevel, ChunkPos chunkPos, StructureFeatureManager structureFeatureManager, CallbackInfo ci) {
//		//ChunkPos chunkPos = region.getCenter();
//		final int chunkX = chunkPos.x;
//		final int chunkZ = chunkPos.z;
//		if (!worldGenLevel.isClientSide() && isNetherBiome(worldGenLevel, chunkX, chunkZ) && worldGenLevel.getChunk(chunkX, chunkZ) != null) {
//			RANDOM.setBaseChunkSeed(chunkX, chunkZ);
//			final int sx = chunkX << 4;
//			final int sz = chunkZ << 4;
//			BNWorldGenerator.prePopulate(worldGenLevel, sx, sz, RANDOM);
//
//			long featureSeed = RANDOM.setDecorationSeed(worldGenLevel.getSeed(), chunkX, chunkZ);
//			ChunkGenerator generator = (ChunkGenerator) (Object) this;
//			for (Biome biome : BNWorldGenerator.getPopulateBiomes()) {
//				try {
//					biome.generate(accessor, generator, worldGenLevel, featureSeed, RANDOM, new BlockPos(sx, worldGenLevel.getMinBuildHeight(), sz));
//				}
//				catch (Exception e) {
//					CrashReport crashReport = CrashReport.forThrowable(e, "Biome decoration");
//					crashReport
//							.addCategory("Generation")
//							.setDetail("CenterX", chunkX)
//							.setDetail("CenterZ", chunkZ)
//							.setDetail("Seed", featureSeed)
//							.setDetail("Biome", biome);
//					throw new ReportedException(crashReport);
//				}
//			}
//
//			BNWorldGenerator.populate(worldGenLevel, sx, sz, RANDOM);
//			BNWorldGenerator.cleaningPass(worldGenLevel, sx, sz);
//
//			ci.cancel();
//		}
//	}

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
