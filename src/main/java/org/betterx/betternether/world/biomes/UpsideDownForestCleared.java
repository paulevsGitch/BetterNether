package org.betterx.betternether.world.biomes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.SurfaceNoiseCondition;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

public class UpsideDownForestCleared extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(111, 188, 121)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(BiomeFeatures.UPSIDE_DOWN_FORREST_CLEARED_FLOOR)
                   .feature(BiomeFeatures.UPSIDE_DOWN_FORREST_CLEARED_CEIL)
                   .genChance(0.5f)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return UpsideDownForestCleared::new;
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
            final SurfaceNoiseCondition noise = UpsideDownFloorCondition.DEFAULT;
            return super.surface().rule(3,
                    SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
                            SurfaceRules.sequence(SurfaceRules.ifTrue(UpsideDownForest.NOISE_CEIL_LAYER,
                                            UpsideDownForest.CEILEING_MOSS),
                                    NETHERRACK)
                    )
            ).rule(2,
                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.sequence(SurfaceRules.ifTrue(noise,
                                            UpsideDownForest.NETHERRACK_MOSS),
                                    SurfaceRules.state(
                                            NetherBlocks.MUSHROOM_GRASS.defaultBlockState()))
                    )
            );
        }
    }

    public UpsideDownForestCleared(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }


}
