package paulevs.betternether.mixin.common;

import java.util.List;
import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import paulevs.betternether.registry.BiomesRegistry;
import paulevs.betternether.world.BNWorldGenerator;
import paulevs.betternether.world.NetherBiomeAccessType;

@Mixin(ServerWorld.class)
public class ServerWorldMixin
{
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onInit(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> registryKey, RegistryKey<DimensionType> registryKey2, DimensionType dimensionType, WorldGenerationProgressListener generationProgressListener, ChunkGenerator chunkGenerator, boolean bl, long seed, List<Spawner> list, boolean bl2, CallbackInfo info)
	{
		BiomesRegistry.registerAllOtherBiomes();
		BNWorldGenerator.init(seed);
		NetherBiomeAccessType.reInitMap(seed);
	}
}