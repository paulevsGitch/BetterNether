package org.betterx.betternether.world.surface;

import net.minecraft.core.Registry;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.surface.rules.SurfaceNoiseCondition;
import org.betterx.bclib.mixin.common.SurfaceRulesContextAccessor;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.noise.OpenSimplexNoise;

public class UpsideDownForrestCeilCondition extends SurfaceNoiseCondition {
    public static final UpsideDownForrestCeilCondition DEFAULT = new UpsideDownForrestCeilCondition();
    private static final OpenSimplexNoise TERRAIN = CrimsonWoodNoiseCondition.TERRAIN;

    private UpsideDownForrestCeilCondition() {
    }

    public static final Codec<UpsideDownForrestCeilCondition> CODEC = Codec.BYTE.fieldOf("nether_noise")
                                                                                .xmap(UpsideDownForrestCeilCondition::create,
                                                                                      obj -> (byte) 0)
                                                                                .codec();
    private static final KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> KEY_CODEC = KeyDispatchDataCodec.of(
            CODEC);

    private static UpsideDownForrestCeilCondition create(byte dummy) {
        return DEFAULT;
    }

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return UpsideDownForrestCeilCondition.KEY_CODEC;
    }

    @Override
    public boolean test(SurfaceRulesContextAccessor context) {
        final int x = context.getBlockX();
        final int z = context.getBlockZ();
        return TERRAIN.eval(x * 0.1, z * 0.1) > MHelper.randRange(0.0F, 0.2F, MHelper.RANDOM);
    }

    static {
        Registry.register(Registry.CONDITION,
                          BetterNether.makeID("upside_down_ceil"),
                          UpsideDownForrestCeilCondition.CODEC);
    }
}