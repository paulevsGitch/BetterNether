package paulevs.betternether.mixin.common;

import java.util.List;
import java.util.concurrent.Executor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.world.BNWorldGenerator;
import paulevs.betternether.world.structures.CityFeature;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onInit(MinecraftServer server, Executor workerExecutor, LevelStorageSource.LevelStorageAccess session, ServerLevelData properties, ResourceKey<Level> registryKey, DimensionType dimensionType,
		ChunkProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean bl, long seed, List<CustomSpawner> list, boolean bl2, CallbackInfo info) {
		BNWorldGenerator.init(seed);
		CityFeature.initGenerator();
	}
}