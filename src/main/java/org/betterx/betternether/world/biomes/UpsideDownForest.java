package org.betterx.betternether.world.biomes;

import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder.BiomeSupplier;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeSettings;
import org.betterx.bclib.api.v2.levelgen.surface.SurfaceRuleBuilder;
import org.betterx.bclib.api.v2.levelgen.surface.rules.Conditions;
import org.betterx.bclib.api.v2.levelgen.surface.rules.RoughNoiseCondition;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.features.placed.NetherObjectsPlaced;
import org.betterx.betternether.registry.features.placed.NetherTreesPlaced;
import org.betterx.betternether.registry.features.placed.NetherVegetationPlaced;
import org.betterx.betternether.registry.features.placed.NetherVinesPlaced;
import org.betterx.betternether.world.NetherBiome;
import org.betterx.betternether.world.NetherBiomeConfig;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class UpsideDownForest extends NetherBiome {
    static final SurfaceRules.RuleSource CEILEING_MOSS = SurfaceRules.state(NetherBlocks.CEILING_MUSHROOMS.defaultBlockState());
    static final SurfaceRules.RuleSource NETHERRACK_MOSS = SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
    static final SurfaceRules.ConditionSource NOISE_CEIL_LAYER = SurfaceRules.noiseCondition(
            Noises.NETHER_STATE_SELECTOR,
            0.0
    );

    public static class Config extends NetherBiomeConfig {
        public Config(String name) {
            super(name);
        }

        @Override
        protected void addCustomBuildData(BCLBiomeBuilder builder) {
            builder.fogColor(111, 188, 111)
                   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
                   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
                   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
                   .structure(BiomeTags.HAS_NETHER_FOSSIL)
                   .feature(NetherFeatures.NETHER_RUBY_ORE)
                   .feature(NetherTreesPlaced.ANCHOR_TREE)
                   .feature(NetherTreesPlaced.SAKURA_TREE)
                   .feature(NetherTreesPlaced.ANCHOR_TREE_BRANCH)
                   .feature(NetherTreesPlaced.ANCHOR_TREE_ROOT)
                   .feature(NetherObjectsPlaced.FOREST_LITTER)
                   .feature(NetherObjectsPlaced.STALAGMITE)
                   .feature(NetherVegetationPlaced.SAKURA_BUSH)
                   .feature(NetherVegetationPlaced.MOSS_COVER)
                   .feature(NetherVinesPlaced.NEON_EQUISETUM)
                   .feature(NetherVinesPlaced.WHISPERING_GOURD_VINE)
                   .feature(NetherVegetationPlaced.HOOK_MUSHROOM)
                   .feature(NetherObjectsPlaced.STALACTITE)
                   .feature(NetherVegetationPlaced.WALL_LUCIS)
                   .feature(NetherVegetationPlaced.WALL_UPSIDE_DOWN)
                   .vertical()
                   .genChance(0.25f);
        }

        @Override
        public BiomeSupplier<NetherBiome> getSupplier() {
            return UpsideDownForest::new;
        }

        @Override
        public boolean hasStalactites() {
            return false;
        }

        @Override
        public boolean hasBNStructures() {
            return false;
        }

        @Override
        public boolean hasBNFeatures() {
            return false;
        }


        @Override
        public SurfaceRuleBuilder surface() {
            return super.surface().rule(
                    2,
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_CEILING,
                            SurfaceRules.sequence(SurfaceRules.ifTrue(
                                    Conditions.FORREST_FLOOR_SURFACE_NOISE_A,
                                    CEILEING_MOSS
                            ), NETHERRACK)
                    )
            ).rule(
                    2,
                    SurfaceRules.ifTrue(
                            SurfaceRules.ON_FLOOR,
                            SurfaceRules.sequence(SurfaceRules.ifTrue(
                                    new RoughNoiseCondition(Noises.NETHERRACK, 0.021),
                                    NETHERRACK_MOSS
                            ), SurfaceRules.state(
                                    NetherBlocks.MUSHROOM_GRASS.defaultBlockState()))
                    )
            );
        }
    }


    public UpsideDownForest(ResourceLocation biomeID, Biome biome, BCLBiomeSettings settings) {
        super(biomeID, biome, settings);
    }

    @Override
    protected void onInit() {
    }
}
