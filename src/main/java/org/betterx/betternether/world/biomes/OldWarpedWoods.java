package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.placed.NetherObjectsPlaced;
import org.betterx.betternether.registry.features.placed.NetherTreesPlaced;
import org.betterx.betternether.registry.features.placed.NetherVegetationPlaced;
import org.betterx.betternether.registry.features.placed.NetherVinesPlaced;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;

public class OldWarpedWoods extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(26, 5, 26)
                   .loop(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_WARPED_FOREST_MOOD)
                   .particles(ParticleTypes.WARPED_SPORE, 0.025F)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(NetherTreesPlaced.BIG_WARPED_TREE)
                   .feature(NetherObjectsPlaced.STALAGMITE)
                   .feature(NetherVegetationPlaced.VEGETATION_OLD_WARPED_WOODS)
                   .feature(NetherVinesPlaced.BLACK_VINE)
                   .feature(NetherVinesPlaced.TWISTING_VINES)
                   .feature(NetherObjectsPlaced.STALACTITE)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return OldWarpedWoods::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case GHAST, ZOMBIFIED_PIGLIN, MAGMA_CUBE, PIGLIN, HOGLIN, PIGLIN_BRUTE -> res = 0;
                case ENDERMAN, STRIDER -> res = type.weight;
            }
            return res;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().floor(Blocks.WARPED_NYLIUM.defaultBlockState());
        }
    }

    public OldWarpedWoods(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }

}
