package paulevs.betternether.world.features;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import paulevs.betternether.world.BNWorldGenerator;
import ru.bclib.registry.BaseRegistry;
import ru.bclib.world.features.DefaultFeature;

import java.util.Random;

public class NetherChunkPopulatorFeature extends DefaultFeature {
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		final BlockPos worldPos = featurePlaceContext.origin();
		final WorldGenLevel level = featurePlaceContext.level();
		final int sx = (worldPos.getX() >> 4) << 4;
		final int sz = (worldPos.getZ() >> 4) << 4;
		
		BNWorldGenerator.prePopulate(level, sx, sz, featurePlaceContext);
		BNWorldGenerator.populate(level, sx, sz, featurePlaceContext);
		
		return true;
	}
}
