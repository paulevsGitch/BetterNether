package paulevs.betternether.world.surface;

import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.api.surface.rules.VolumeNoiseCondition;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class NetherNoiseCondition extends VolumeNoiseCondition {
	public static final NetherNoiseCondition DEFAULT = new NetherNoiseCondition();
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(245);
	
	@Override
	public boolean test(SurfaceRulesContextAccessor context) {
		final int x = context.getBlockX();
		final int y = context.getBlockY();
		final int z = context.getBlockZ();
		return TERRAIN.eval(x * 0.1, y * 0.2, z * 0.1) > MHelper.randRange(-0.2F, 0.1F, MHelper.RANDOM);
	}
}
