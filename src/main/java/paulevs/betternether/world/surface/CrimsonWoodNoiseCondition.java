package paulevs.betternether.world.surface;

import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.api.surface.rules.SurfaceNoiseCondition;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class CrimsonWoodNoiseCondition extends SurfaceNoiseCondition {
	public static final CrimsonWoodNoiseCondition DEFAULT = new CrimsonWoodNoiseCondition();
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);
	
	@Override
	public boolean test(SurfaceRulesContextAccessor context) {
		final int x = context.getBlockX();
		final int z = context.getBlockZ();
		return TERRAIN.eval(x * 0.1, z * 0.1) > MHelper.randRange(0.0F, 0.2F, MHelper.RANDOM);
	}
}

