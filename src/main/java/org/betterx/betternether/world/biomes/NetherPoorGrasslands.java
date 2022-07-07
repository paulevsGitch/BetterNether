package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.Conditions;
import org.betterx.bclib.api.v2.levelgen.surface.rules.SwitchRuleSource;
import org.betterx.betternether.registry.features.placed.NetherObjectsPlaced;
import org.betterx.betternether.registry.features.placed.NetherVegetationPlaced;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

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
                   .feature(NetherVegetationPlaced.NETHER_REED)
                   .feature(NetherVegetationPlaced.BLACK_BUSH_SPARSE)
                   .feature(NetherVegetationPlaced.VEGETATION_POOR_GRASSLANDS)
                   .feature(NetherObjectsPlaced.SMOKER_SPARSE)
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
                    = SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            Conditions.NETHER_VOLUME_NOISE,
                            NetherGrasslands.SOUL_SOIL
                    ),
                    SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState())
            );
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
}