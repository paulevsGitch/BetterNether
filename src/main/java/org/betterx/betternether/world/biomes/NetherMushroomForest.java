package org.betterx.betternether.world.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

public class NetherMushroomForest extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(166, 38, 95)
                   .loop(SoundsRegistry.AMBIENT_MUSHROOM_FOREST)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .particles(ParticleTypes.MYCELIUM, 0.1F)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(BiomeFeatures.MUSHROOM_FORREST_FLOOR)
                   .feature(BiomeFeatures.MUSHROOM_FORREST_WALL)
                   .edgeSize(6)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherMushroomForest::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(NetherBlocks.NETHER_MYCELIUM.defaultBlockState());
        }
    }

    public NetherMushroomForest(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
        this.setNoiseDensity(0.5F);
    }

}
