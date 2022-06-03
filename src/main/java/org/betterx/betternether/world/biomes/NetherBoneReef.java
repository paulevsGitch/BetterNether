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
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.registry.features.TerrainFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

public class NetherBoneReef extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(47, 221, 202)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .particles(ParticleTypes.WARPED_SPORE, 0.01F)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(TerrainFeatures.LAVA_PITS_SPARE)
                   .feature(BiomeFeatures.BONE_REEF_CEIL)
                   .feature(BiomeFeatures.BONE_REEF_FLOOR)
            //.feature(NetherFeatures.STALAGMITE_BONE_CLUSTER)
            //.feature(NetherFeatures.BONES)
            //.feature(NetherFeatures.BONE_GRASS)
            //.feature(NetherFeatures.FEATHER_FERN)
            //.feature(NetherFeatures.LUMBUS_VINE)
            //.feature(NetherFeatures.NETHER_REEED)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherBoneReef::new;
        }

        @Override
        public boolean hasStalactites() {
            return false;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super
                    .surface()
                    .floor(NetherBlocks.MUSHROOM_GRASS.defaultBlockState());
        }
    }

    public NetherBoneReef(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {

    }
}
