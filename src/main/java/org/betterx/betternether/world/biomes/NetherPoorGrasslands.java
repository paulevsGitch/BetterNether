package org.betterx.betternether.world.biomes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.surface.rules.Conditions;
import org.betterx.bclib.api.surface.rules.SwitchRuleSource;
import org.betterx.betternether.registry.features.BiomeFeatures;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import java.util.List;

public class NetherPoorGrasslands extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(113, 73, 133)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .structure(BiomeTags.HAS_BASTION_REMNANT)
                   .structure(BiomeTags.HAS_NETHER_FORTRESS)
                   .feature(BiomeFeatures.POOR_GRASSLAND_FLOOR)
                   .genChance(0.3F)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherPoorGrasslands::new;
        }

        @Override
        public SurfaceRuleBuilder surface() {
            SurfaceRules.RuleSource soilStoneDist
                    = SurfaceRules.sequence(SurfaceRules.ifTrue(Conditions.NETHER_VOLUME_NOISE,
                            NetherGrasslands.SOUL_SOIL),
                    SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState()));
            return super
                    .surface()
                    .rule(SurfaceRules.sequence(
                            SurfaceRules.ifTrue(
                                    SurfaceRules.ON_FLOOR,
                                    new SwitchRuleSource(
                                            Conditions.NETHER_NOISE,
                                            List.of(NetherGrasslands.MOSS, NetherGrasslands.SOUL_SOIL, NETHERRACK)
                                    )
                            ),
                            soilStoneDist
                    ));
        }
    }

    public NetherPoorGrasslands(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}