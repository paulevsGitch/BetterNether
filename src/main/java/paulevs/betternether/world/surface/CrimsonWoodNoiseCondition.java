package paulevs.betternether.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.BetterNether;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.api.surface.rules.SurfaceNoiseCondition;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class CrimsonWoodNoiseCondition extends SurfaceNoiseCondition {
	public static final CrimsonWoodNoiseCondition DEFAULT = new CrimsonWoodNoiseCondition();
	static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(614);
	private CrimsonWoodNoiseCondition(){}

	public static final Codec<CrimsonWoodNoiseCondition> CODEC = Codec.BYTE.fieldOf("nether_noise").xmap(CrimsonWoodNoiseCondition::create, obj -> (byte)0).codec();
	private static CrimsonWoodNoiseCondition create(byte dummy){ return DEFAULT; }

	@Override
	public Codec<? extends SurfaceRules.ConditionSource> codec() {
		return CrimsonWoodNoiseCondition.CODEC;
	}

	@Override
	public boolean test(SurfaceRulesContextAccessor context) {
		final int x = context.getBlockX();
		final int z = context.getBlockZ();
		return TERRAIN.eval(x * 0.1, z * 0.1) > MHelper.randRange(0.5F, 0.7F, MHelper.RANDOM);
	}

	static {
		Registry.register(Registry.CONDITION , BetterNether.makeID("crimson_wood_noise"), CrimsonWoodNoiseCondition.CODEC);
	}
}

