package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.Conditions;
import org.betterx.bclib.api.v2.levelgen.surface.rules.SwitchRuleSource;
import org.betterx.betternether.blocks.BlockSoulSandstone;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.placed.NetherObjectsPlaced;
import org.betterx.betternether.registry.features.placed.NetherTerrainPlaced;
import org.betterx.betternether.registry.features.placed.NetherTreesPlaced;
import org.betterx.betternether.registry.features.placed.NetherVegetationPlaced;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;

public class NetherWartForest extends NetherBiome {
    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(151, 6, 6)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .particles(ParticleTypes.CRIMSON_SPORE, 0.05F)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(NetherTerrainPlaced.REPLACE_SOUL_SANDSTONE)
                   .feature(NetherObjectsPlaced.WART_DEADWOOD)
                   .feature(NetherTreesPlaced.SOUL_LILY)
                   .feature(NetherTreesPlaced.WART_TREE)
                   .feature(NetherObjectsPlaced.BASALT_STALAGMITE_SPARSE)
                   .feature(NetherVegetationPlaced.BLACK_BUSH)
                   .feature(NetherVegetationPlaced.VEGETATION_WART_FOREST)
                   .feature(NetherObjectsPlaced.BASALT_STALACTITE_SPARSE)
                   .edgeSize(9)
            ;
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return NetherWartForest::new;
        }

        @Override
        public <M extends Mob> int spawnWeight(NetherEntities.KnownSpawnTypes type) {
            int res = super.spawnWeight(type);
            switch (type) {
                case FLYING_PIG -> res = type.weight;
                case NAGA -> res = 0;
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
                                    new SwitchRuleSource(
                                            Conditions.NETHER_NOISE,
                                            List.of(
                                                    NetherGrasslands.SOUL_SOIL,
                                                    NetherGrasslands.SOUL_SAND,
                                                    NetherGrasslands.MOSS,
                                                    NetherGrasslands.SOUL_SAND,
                                                    NETHERRACK
                                            )
                                    )
                            ),
                            SurfaceRules.ifTrue(
                                    SurfaceRules.stoneDepthCheck(4, true, 1, CaveSurface.FLOOR),
                                    new SwitchRuleSource(
                                            Conditions.NETHER_NOISE,
                                            List.of(
                                                    SurfaceRules.state(
                                                            NetherBlocks.SOUL_SANDSTONE
                                                                    .defaultBlockState()
                                                                    .setValue(BlockSoulSandstone.UP, true)
                                                    ),
                                                    SurfaceRules.state(
                                                            NetherBlocks.SOUL_SANDSTONE
                                                                    .defaultBlockState()
                                                                    .setValue(BlockSoulSandstone.UP, false)
                                                    )
                                            )
                                    )
                            ),
                            new SwitchRuleSource(
                                    Conditions.NETHER_NOISE,
                                    List.of(
                                            NetherGrasslands.SOUL_SOIL,
                                            NetherGrasslands.SOUL_SAND,
                                            NETHERRACK
                                    )
                            )
                    ))
                    ;
        }
    }

    public NetherWartForest(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }
}
