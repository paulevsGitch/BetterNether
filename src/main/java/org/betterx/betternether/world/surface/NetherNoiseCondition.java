package org.betterx.betternether.world.surface;

import net.minecraft.core.Registry;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.surface.rules.VolumeNoiseCondition;
import org.betterx.bclib.interfaces.NumericProvider;
import org.betterx.bclib.mixin.common.SurfaceRulesContextAccessor;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.noise.OpenSimplexNoise;

public class NetherNoiseCondition extends VolumeNoiseCondition implements NumericProvider {
    public static final NetherNoiseCondition DEFAULT = new NetherNoiseCondition();
    public static final Codec<NetherNoiseCondition> CODEC = Codec.BYTE.fieldOf("nether_noise")
                                                                      .xmap((obj) -> DEFAULT, obj -> (byte) 0)
                                                                      .codec();
    private static final KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> KEY_CODEC = KeyDispatchDataCodec.of(
            CODEC);
    private static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(245);

    private NetherNoiseCondition() {
    }

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return NetherNoiseCondition.KEY_CODEC;
    }

    @Override
    public Codec<? extends NumericProvider> pcodec() {
        return CODEC;
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

        int offset = MHelper.RANDOM.nextInt(20) == 0 ? 3 : 0;
        float cmp = MHelper.randRange(0.4F, 0.5F, MHelper.RANDOM);
        if (value > cmp || value < -cmp) return 2 + offset;

        if (value > MHelper.randRange(-0.2F, 0.1F, MHelper.RANDOM)) return 0 + offset;

        return 1 + offset;
    }

    static {
        Registry.register(NumericProvider.NUMERIC_PROVIDER,
                          BetterNether.makeID("nether_noise"),
                          NetherNoiseCondition.CODEC);
        Registry.register(Registry.CONDITION, BetterNether.makeID("noise"), NetherNoiseCondition.CODEC);
    }
}