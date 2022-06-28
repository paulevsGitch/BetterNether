package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.Conditions;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class OldSwampland extends NetherBiome {
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
                   .feature(NetherTerrainPlaced.LAVA_PITS_SPARSE)
                   .feature(NetherVegetationPlaced.NETHER_REED)
                   .feature(NetherTreesPlaced.OLD_WILLOW_TREE)
                   .feature(NetherTreesPlaced.WILLOW_TREE)
                   .feature(NetherVegetationPlaced.WILLOW_BUSH)
                   .feature(NetherVegetationPlaced.BLACK_BUSH_SPARSE)
                   .feature(NetherObjectsPlaced.STALAGMITE)
                   .feature(NetherVegetationPlaced.VEGETATION_OLD_SWAMPLAND)
                   .feature(NetherVegetationPlaced.JELLYFISH_MUSHROOM_DENSE)
                   .feature(NetherObjectsPlaced.SMOKER)
                   .feature(NetherVinesPlaced.BLACK_VINE)
                   .feature(NetherVinesPlaced.BLOOMING_VINE)
                   .feature(NetherObjectsPlaced.STALACTITE)
                   .feature(NetherVegetationPlaced.WALL_MUSHROOMS_WITH_MOSS)
                   .feature(NetherObjectsPlaced.SCULK_TOP)
                   .feature(NetherObjectsPlaced.SCULK_HIDDEN)
                   .feature(NetherVegetationPlaced.SCULK_VEGETATION)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return OldSwampland::new;
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

        @Override
        public SurfaceRuleBuilder surface() {
            return super
                    .surface()
                    .rule(SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.ON_FLOOR,
                                    SurfaceRules.ifTrue(
                                            new RoughNoiseCondition(Noises.NETHERRACK, 0.19),
                                            SurfaceRules.state(NetherBlocks.SWAMPLAND_GRASS.defaultBlockState())
                                    )
                            ),
                            SurfaceRules.ifTrue(
                                    Conditions.NETHER_SURFACE_NOISE_LARGE,
                                    NetherGrasslands.SOUL_SAND
                            ),
                            SurfaceRules.state(Blocks.SCULK.defaultBlockState())
                    ));
        }
    }

    public OldSwampland(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}