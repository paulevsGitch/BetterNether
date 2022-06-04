package org.betterx.betternether.world.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.SoundsRegistry;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;
import org.betterx.betternether.world.surface.NetherNoiseCondition;

public class NetherGravelDesert extends NetherBiome {
    public static final SurfaceRules.RuleSource GRAVEL = SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());

    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(170, 48, 0)
                   .mood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
                   .loop(SoundsRegistry.AMBIENT_GRAVEL_DESERT)
                   .additions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
                   .music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
                   .particles(ParticleTypes.ASH, 0.02F)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(BiomeFeatures.GRAVEL_DESERT_FLOOR)
//                   .feature(NetherFeatures.NETHER_CACTUS)
//                   .feature(NetherFeatures.BARREL_CACTUS)
//                   .feature(NetherFeatures.AGAVE)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherGravelDesert::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface()
                        .ceil(Blocks.NETHERRACK.defaultBlockState())
                        .floor(Blocks.GRAVEL.defaultBlockState())
                        .rule(3, SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, GRAVEL))
                        .belowFloor(Blocks.GRAVEL.defaultBlockState(), 4, NetherNoiseCondition.DEFAULT)
                    ;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case NAGA -> res = 20;
            }
            return res;
        }
    }

    public NetherGravelDesert(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}
