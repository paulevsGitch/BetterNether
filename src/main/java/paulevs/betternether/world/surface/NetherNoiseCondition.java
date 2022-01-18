package paulevs.betternether.world.surface;

import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.api.surface.rules.VolumeNoiseCondition;
import ru.bclib.interfaces.NumericProvider;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class NetherNoiseCondition extends VolumeNoiseCondition implements NumericProvider {
	public static final NetherNoiseCondition DEFAULT = new NetherNoiseCondition();
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(245);
	private NetherNoiseCondition(){}

	@Override
	public boolean test(SurfaceRulesContextAccessor context) {
		final int x = context.getBlockX();
		final int y = context.getBlockY();
		final int z = context.getBlockZ();
		return TERRAIN.eval(x * 0.1, y * 0.2, z * 0.1) > MHelper.randRange(-0.2F, 0.1F, MHelper.RANDOM);
	}
	
	@Override
	public int getNumber(SurfaceRulesContextAccessor context) {
		final int x = context.getBlockX();
		final int y = context.getBlockY();
		final int z = context.getBlockZ();
		double value = TERRAIN.eval(x * 0.1, y * 0.2, z * 0.1);
		
		int offset = MHelper.RANDOM.nextInt(20)==0?3:0;
		float cmp = MHelper.randRange(0.4F, 0.5F, MHelper.RANDOM);
		if (value > cmp || value < -cmp) return 2+offset;
		
		if (value > MHelper.randRange(-0.2F, 0.1F, MHelper.RANDOM)) return 0+offset;
		
		return 1+offset;
	}
}
