package org.betterx.betternether.world.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.Conditions;
import org.betterx.bclib.api.surface.rules.SwitchRuleSource;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import java.util.List;

public class NetherSoulPlain extends NetherBiome {
    private static final SurfaceRules.RuleSource SOUL_SAND = SurfaceRules.state(Blocks.SOUL_SAND.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
    private static final SurfaceRules.RuleSource SOUL_SANDSTONE = SurfaceRules.state(NetherBlocks.SOUL_SANDSTONE.defaultBlockState());
    private static final SurfaceRules.RuleSource LAVA = SurfaceRules.state(Blocks.MAGMA_BLOCK.defaultBlockState());

    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(196, 113, 239)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY)
                   .particles(ParticleTypes.PORTAL, 0.02F)
                   .structure(BiomeTags.HAS_NETHER_FOSSIL)
                   .feature(NetherFeatures.NETHER_RUBY_ORE_SOUL)
                   .feature(BiomeFeatures.SOUL_PLAIN_FLOOR)
                   .feature(BiomeFeatures.SOUL_PLAIN_CEIL)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherSoulPlain::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            RuleSource soilSandDist
                    = SurfaceRules.sequence(SurfaceRules.ifTrue(Conditions.NETHER_VOLUME_NOISE, SOUL_SOIL), SOUL_SAND);

            RuleSource soilSandStoneDist
                    = SurfaceRules.sequence(new SwitchRuleSource(Conditions.NETHER_NOISE,
                    List.of(SOUL_SOIL,
                            SOUL_SAND,
                            SOUL_SANDSTONE,
                            LAVA,
                            LAVA,
                            SOUL_SAND)));

            RuleSource soilStoneDist
                    = SurfaceRules.sequence(SurfaceRules.ifTrue(Conditions.NETHER_VOLUME_NOISE, SOUL_SOIL),
                    SOUL_SANDSTONE);
            return super
                    .surface()
                    .rule(2, SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, soilSandDist))
                    .ceil(NetherBlocks.SOUL_SANDSTONE.defaultBlockState())
                    .rule(4, SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, soilStoneDist))
                    .rule(5, soilSandStoneDist);

        }
    }

    public NetherSoulPlain(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}