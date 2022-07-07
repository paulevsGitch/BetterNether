package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.RoughNoiseCondition;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.registry.features.placed.*;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

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
                   .feature(NetherVegetationPlaced.NETHER_REED)
                   .feature(NetherTreesPlaced.WILLOW_TREE)
                   .feature(NetherVegetationPlaced.WILLOW_BUSH)
                   .feature(NetherVegetationPlaced.BLACK_BUSH_SPARSE)
                   .feature(NetherObjectsPlaced.STALAGMITE)
                   .feature(NetherVegetationPlaced.VEGETATION_SWAMPLAND)
                   .feature(NetherVegetationPlaced.JELLYFISH_MUSHROOM)
                   .feature(NetherObjectsPlaced.SMOKER)
                   .feature(NetherVinesPlaced.BLACK_VINE)
                   .feature(NetherVinesPlaced.BLOOMING_VINE)
                   .feature(NetherObjectsPlaced.STALACTITE)
                   .feature(NetherVegetationPlaced.WALL_MUSHROOMS_WITH_MOSS)
            ;
            this.addCustomSwamplandBuildData(builder);
        }

        protected void addCustomSwamplandBuildData(BCLBiomeBuilder builder) {
            builder.feature(NetherTerrainPlaced.LAVA_SWAMP);
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
                                            new RoughNoiseCondition(Noises.NETHERRACK, 0.14),
                                            SurfaceRules.state(NetherBlocks.SWAMPLAND_GRASS.defaultBlockState())
                                    )
                            ),
                            SurfaceRules.ifTrue(
                                    new RoughNoiseCondition(Noises.NETHER_WART, 0.19),
                                    NetherGrasslands.SOUL_SAND
                            ),
                            NetherGrasslands.SOUL_SOIL
                    ));
        }
    }

    public NetherSwampland(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }
}
