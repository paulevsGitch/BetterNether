package paulevs.betternether.world.surface;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.kinds.K1;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.api.surface.rules.VolumeNoiseCondition;
import ru.bclib.interfaces.NumericProvider;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class NetherNoiseCondition extends VolumeNoiseCondition implements NumericProvider {
	public static final NetherNoiseCondition DEFAULT = new NetherNoiseCondition();
	public static final Codec<NetherNoiseCondition> CODEC = Codec.BYTE.fieldOf("nether_noise").xmap(NetherNoiseCondition::create, obj -> (byte)0).codec();
	private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(245);

	private static NetherNoiseCondition create(byte dummy){ return DEFAULT; }
	private NetherNoiseCondition(){ }

	@Override
	public Codec<? extends SurfaceRules.ConditionSource> codec() {
		return NetherNoiseCondition.CODEC;
	}

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
