package paulevs.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.BetterNether;
import paulevs.betternether.world.BNWorldGenerator;
import ru.bclib.BCLib;
import ru.bclib.registry.BaseRegistry;
import ru.bclib.world.features.DefaultFeature;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NetherChunkPopulatorFeature extends DefaultFeature {
	private static final ConcurrentHashMap<Thread, BNWorldGenerator> generatorPool = new ConcurrentHashMap<>();
	
	public static void clearGeneratorPool(){
		generatorPool.clear();
	}

	public static BNWorldGenerator generatorForThread() { return generatorPool.computeIfAbsent(Thread.currentThread(), t-> new BNWorldGenerator()); }
	public static final AtomicInteger callCounter = new AtomicInteger();
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		if (generatorPool.size()>15){
			if (callCounter.incrementAndGet()>5000){
				callCounter.set(0);
				long deadCount = generatorPool.keySet().stream().filter((thread)->!thread.isAlive()).count();
				BetterNether.LOGGER.info("Cleanup GeneratorPool: " + deadCount + "/" + generatorPool.size() );
				;
				generatorPool.entrySet().removeIf(e -> !e.getKey().isAlive());
			}
		}

		final BlockPos worldPos = featurePlaceContext.origin();
		final WorldGenLevel level = featurePlaceContext.level();
		final int sx = (worldPos.getX() >> 4) << 4;
		final int sz = (worldPos.getZ() >> 4) << 4;
		
		BNWorldGenerator generator = generatorForThread();

		//BetterNether.LOGGER.info(" +++ Starting populate " + sx + "/" + sz + " on " + Thread.currentThread() + " (" + generatorPool.size()  +")");
		generator.prePopulate(level, sx, sz, featurePlaceContext);
		generator.populate(level, sx, sz, featurePlaceContext);
		//BetterNether.LOGGER.info(" --- Finished populate " + sx + "/" + sz);
		
		return true;
	}
}
