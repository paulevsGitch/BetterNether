package paulevs.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.BetterNether;
import paulevs.betternether.world.BNWorldGenerator;
import ru.bclib.world.features.DefaultFeature;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class NetherChunkPopulatorFeature extends DefaultFeature {
	private static final ConcurrentHashMap<Thread, BNWorldGenerator> generatorPool = new ConcurrentHashMap<>();
	
	public static void clearGeneratorPool(){
		generatorPool.clear();
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		final Random random = featurePlaceContext.random();
		final BlockPos worldPos = featurePlaceContext.origin();
		final WorldGenLevel level = featurePlaceContext.level();
		//final ChunkPos cPos = level.getChunk(pos).getPos();
		
		final int sx = (worldPos.getX() >> 4) << 4;
		final int sz = (worldPos.getZ() >> 4) << 4;
		
		BNWorldGenerator generator = generatorPool.computeIfAbsent(Thread.currentThread(), t-> new BNWorldGenerator());
		
		//BetterNether.LOGGER.info(" +++ Starting populate " + sx + "/" + sz + " on " + Thread.currentThread() + " (" + generatorPool.size()  +")");
		generator.prePopulate(level, sx, sz, random);
		generator.populate(level, sx, sz, random);
		//BetterNether.LOGGER.info(" --- Finished populate " + sx + "/" + sz);
		
		return true;
	}
}
