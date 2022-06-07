package org.betterx.betternether.world.biomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

import com.mojang.serialization.Codec;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.Conditions;
import org.betterx.bclib.api.surface.rules.SurfaceNoiseCondition;
import org.betterx.bclib.mixin.common.SurfaceRulesContextAccessor;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.MHelper;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

class UpsideDownFloorCondition extends SurfaceNoiseCondition {
    public static final UpsideDownFloorCondition DEFAULT = new UpsideDownFloorCondition();
    public static final Codec<UpsideDownFloorCondition> CODEC = Codec.BYTE.fieldOf("nether_noise")
                                                                          .xmap(UpsideDownFloorCondition::create,
                                                                                  obj -> (byte) 0)
                                                                          .codec();
    private static final KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> KEY_CODEC = KeyDispatchDataCodec.of(
            CODEC);

    private static UpsideDownFloorCondition create(byte dummy) {
        return DEFAULT;
    }

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return UpsideDownFloorCondition.KEY_CODEC;
    }

    @Override
    public boolean test(SurfaceRulesContextAccessor context) {
        return MHelper.RANDOM.nextInt(3) == 0;
    }

    static {
        Registry.register(Registry.CONDITION, BetterNether.makeID("upside_down_floor"), UpsideDownFloorCondition.CODEC);
    }
}

public class UpsideDownForest extends NetherBiome {
    static final SurfaceRules.RuleSource CEILEING_MOSS = SurfaceRules.state(NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
    static final SurfaceRules.RuleSource NETHERRACK_MOSS = SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
    static final SurfaceRules.ConditionSource NOISE_CEIL_LAYER = SurfaceRules.noiseCondition(Noises.NETHER_STATE_SELECTOR,
            0.0);

    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(111, 188, 111)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .structure(BiomeTags.HAS_NETHER_FOSSIL)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(BiomeFeatures.UPSIDE_DOWN_FORREST_FLOOR)
                   .feature(BiomeFeatures.UPSIDE_DOWN_FORREST_CEIL)
                   .feature(BiomeFeatures.UPSIDE_DOWN_FORREST_WALL)
                   .vertical()
                   .genChance(0.25f);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return UpsideDownForest::new;
        }

        @Override
        public boolean hasStalactites() {
            return false;
        }

        @Override
        public boolean hasBNStructures() {
            return false;
        }

        @Override
        public boolean hasBNFeatures() {
            return false;
        }


        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().rule(2,
                    SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
                            SurfaceRules.sequence(SurfaceRules.ifTrue(
                                    Conditions.FORREST_FLOOR_SURFACE_NOISE_A,
                                    CEILEING_MOSS), NETHERRACK)
                    )
            ).rule(2,
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.sequence(SurfaceRules.ifTrue(
                                    UpsideDownFloorCondition.DEFAULT,
                                    NETHERRACK_MOSS), NETHERRACK)
                    )
            );
        }
    }


    public UpsideDownForest(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}
