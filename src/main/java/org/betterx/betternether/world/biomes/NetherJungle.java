package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherStructures;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;

public class NetherJungle extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(62, 169, 61)
                   .loop(SoundsRegistry.AMBIENT_NETHER_JUNGLE)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_WARPED_FOREST)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(NetherStructures.JUNGLE_TEMPLES)
                   .feature(BiomeFeatures.JUNGLE_FLOOR)
                   .feature(BiomeFeatures.JUNGLE_CEIL)
                   .feature(BiomeFeatures.JUNGLE_WALL)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherJungle::new;
        }

        @Override
        public boolean hasBNStructures() {
            return false;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case GHAST, ZOMBIFIED_PIGLIN, MAGMA_CUBE, ENDERMAN, PIGLIN, STRIDER, HOGLIN, PIGLIN_BRUTE -> res = 0;
                case JUNGLE_SKELETON -> res = 40;
            }
            return res;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(NetherBlocks.JUNGLE_GRASS.defaultBlockState());
        }
    }

    public NetherJungle(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {

    }
}