package paulevs.betternether.mixin;

import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(ServerWorld.class)
public class WorldSaveMixin
{
	@Inject(method = "save", at = @At("HEAD"))
	private void onSave(ProgressListener progressListener, boolean flush, boolean bl, CallbackInfo info)
	{
		BNWorldGenerator.save((ServerWorld) (Object) this);
	}
	
	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onLoad(MinecraftServer server, Executor workerExecutor, WorldSaveHandler worldSaveHandler, LevelProperties properties, DimensionType dimensionType, Profiler profiler, WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo info)
	{
		ServerWorld world = (ServerWorld) (Object) this;
		if (world.getDimension().isNether())
			BNWorldGenerator.load(world);
	}
}