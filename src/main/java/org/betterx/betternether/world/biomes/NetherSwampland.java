package org.betterx.betternether.world.biomes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.Conditions;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.registry.features.TerrainFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

public class NetherSwampland extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(137, 19, 78)
                   .loop(SoundsRegistry.AMBIENT_SWAMPLAND)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(BiomeFeatures.NETHER_SWAMPLAND_FLOOR)
                   .feature(BiomeFeatures.NETHER_SWAMPLAND_CEIL)
                   .feature(BiomeFeatures.NETHER_SWAMPLAND_WALL)
            ;
            this.addCustomSwamplandBuildData(builder);
        }

        protected void addCustomSwamplandBuildData(BCLBiomeBuilder builder) {
            builder.feature(TerrainFeatures.LAVA_SWAMP);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherSwampland::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case ENDERMAN, GHAST, ZOMBIFIED_PIGLIN, PIGLIN, HOGLIN, PIGLIN_BRUTE -> res = 0;
                case MAGMA_CUBE, STRIDER -> res = 40;
            }
            return res;
        }

        public boolean hasDefaultOres() {
            return false;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super
                    .surface()
                    .rule(SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.ON_FLOOR,
                                    SurfaceRules.ifTrue(
                                            Conditions.NETHER_VOLUME_NOISE_LARGE,
                                            SurfaceRules.state(NetherBlocks.SWAMPLAND_GRASS.defaultBlockState())
                                    )
                            ),
                            SurfaceRules.ifTrue(
                                    Conditions.NETHER_SURFACE_NOISE_LARGE,
                                    NetherGrasslands.SOUL_SOIL
                            ),
                            NetherGrasslands.SOUL_SAND
                    ));
        }
    }

    public NetherSwampland(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}
