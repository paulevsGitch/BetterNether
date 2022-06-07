package org.betterx.betternether.world.biomes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.Conditions;
import org.betterx.bclib.api.v2.levelgen.surface.rules.SwitchRuleSource;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import java.util.List;

public class NetherWartForestEdge extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(191, 28, 28)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(BiomeFeatures.NETHER_WART_FORREST_EDGE_FLOOR);
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super
                    .surface()
                    .rule(
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.ON_FLOOR,
                                            new SwitchRuleSource(Conditions.NETHER_NOISE,
                                                    List.of(NetherGrasslands.SOUL_SOIL,
                                                            NetherGrasslands.SOUL_SAND,
                                                            NetherGrasslands.MOSS,
                                                            NETHERRACK))
                                    ),
                                    SurfaceRules.ifTrue(
                                            Conditions.NETHER_VOLUME_NOISE,
                                            NetherGrasslands.SOUL_SAND
                                    ),
                                    NETHERRACK
                            )
                    )
                    ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherWartForestEdge::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case FLYING_PIG -> res = type.weight;
                case NAGA -> res = 0;
            }
            return res;
        }
    }

    public NetherWartForestEdge(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }

}