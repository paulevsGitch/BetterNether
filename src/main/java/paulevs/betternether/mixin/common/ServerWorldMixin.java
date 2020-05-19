package paulevs.betternether.mixin.common;

import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.class_5268;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.storage.LevelStorage.Session;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ServerWorld.class)
public class ServerWorldMixin
{
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onInit(MinecraftServer minecraftServer, Executor workerExecutor, Session session, class_5268 properties, DimensionType dimensionType, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean bl, long seed, CallbackInfo info)
	{
		BNWorldGenerator.init(seed);
	}
}
