package paulevs.betternether.world.surface;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.MHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import ru.bclib.api.surface.rules.SurfaceNoiseCondition;
import ru.bclib.mixin.common.SurfaceRulesContextAccessor;

public class UpsideDownForrestCeilCondition extends SurfaceNoiseCondition {
    public static final UpsideDownForrestCeilCondition DEFAULT = new UpsideDownForrestCeilCondition();
    private static final OpenSimplexNoise TERRAIN = CrimsonWoodNoiseCondition.TERRAIN;
    private UpsideDownForrestCeilCondition(){}

    public static final Codec<UpsideDownForrestCeilCondition> CODEC = Codec.BYTE.fieldOf("nether_noise").xmap(UpsideDownForrestCeilCondition::create, obj -> (byte)0).codec();
    private static UpsideDownForrestCeilCondition create(byte dummy){ return DEFAULT; }

    @Override
    public Codec<? extends SurfaceRules.ConditionSource> codec() {
        return UpsideDownForrestCeilCondition.CODEC;
    }
    @Override
    public boolean test(SurfaceRulesContextAccessor context) {
        final int x = context.getBlockX();
        final int z = context.getBlockZ();
        return TERRAIN.eval(x * 0.1, z * 0.1) > MHelper.randRange(0.0F, 0.2F, MHelper.RANDOM);
    }
}