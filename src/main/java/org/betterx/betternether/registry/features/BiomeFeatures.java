package org.betterx.betternether.registry.features;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.BCLFeatureBuilder;
import org.betterx.bclib.api.features.config.PlaceFacingBlockConfig;
import org.betterx.bclib.api.features.placement.FindSolidInDirection;
import org.betterx.bclib.api.features.placement.OnEveryLayer;
import org.betterx.betternether.BetterNether;

import java.util.List;

public class BiomeFeatures {
    public static final List<PlacementModifier> defaultCeilModifiers = List.of(CountPlacement.of(16),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_4_4,
            FindSolidInDirection.up(12),
            BiomeFilter.biome());
    public static final List<PlacementModifier> defaultFloorModifiers = List.of(CountPlacement.of(16),
            InSquarePlacement.spread(),
            OnEveryLayer.min4(),
            BiomeFilter.biome());

    public static final List<PlacementModifier> defaultWallModifiers = List.of(CountPlacement.of(128),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_4_4,
            new FindSolidInDirection(PlaceFacingBlockConfig.HORIZONTAL, 12, false),
            BiomeFilter.biome());
    public static final BCLFeature MAGMA_LAND_FLOOR = floorFromWeighted("magma_land", List.of(
            new WeightedPlacedFeature(FloorFeatures.GEYSER.getPlacedFeature(), 0.02f),
            new WeightedPlacedFeature(FloorFeatures.MAGMA_FLOWER.getPlacedFeature(), 0.1f),
            new WeightedPlacedFeature(FloorFeatures.CRYSTAL.getPlacedFeature(), 0.03f),
            new WeightedPlacedFeature(FloorFeatures.BLACK_BUSH_PATCH.getPlacedFeature(), 0.03f)
    ), 2);

    public static final BCLFeature MAGMA_LAND_CEIL = ceilFromWeighted("magma_land", List.of(
            new WeightedPlacedFeature(VineLikeFeatures.GOLDEN_VINE.getPlacedFeature(), 1f)
    ), 0);
    public static final BCLFeature CRIMSON_GLOWING_WOODS_CEIL = MAGMA_LAND_CEIL;
    public static final BCLFeature CRIMSON_PINEWOOD_CEIL = CRIMSON_GLOWING_WOODS_CEIL;

    public static final BCLFeature BONE_REEF_FLOOR = floorFromWeighted("bone_reef", List.of(
            new WeightedPlacedFeature(FloorFeatures.BONES.getPlacedFeature(), 0.015f),
            new WeightedPlacedFeature(FloorFeatures.BONE_GRASS_PATCH.getPlacedFeature(), 0.10f),
            new WeightedPlacedFeature(FloorFeatures.FEATHER_FERN_PATCH.getPlacedFeature(), 0.05f),
            new WeightedPlacedFeature(FloorFeatures.JELLYFISH_MUSHROOM.getPlacedFeature(), 0.01f),
            new WeightedPlacedFeature(VineLikeFeatures.NETHER_REED.getPlacedFeature(), 0.03f),
            new WeightedPlacedFeature(VineLikeFeatures.STALAGMITE_BONE_CLUSTER.getPlacedFeature(), 0.01f)
    ), 1);

    public static final BCLFeature BONE_REEF_CEIL = ceilFromWeighted("bone_reef", List.of(
            new WeightedPlacedFeature(VineLikeFeatures.LUMBUS_VINE.getPlacedFeature(), 1f)
    ), 0);

    public static final BCLFeature SOUL_PLAIN_FLOOR = floorFromWeighted("soul_plain", List.of(
            new WeightedPlacedFeature(VineLikeFeatures.SOUL_VEIN.getPlacedFeature(), 0.3f),
            new WeightedPlacedFeature(FloorFeatures.SOUL_GRASS_PATCH.getPlacedFeature(), 0.2f),
            new WeightedPlacedFeature(FloorFeatures.BLACK_BUSH_PATCH.getPlacedFeature(), 0.2f)
    ), 0);

    public static final BCLFeature GRAVEL_DESERT_FLOOR = floorFromWeighted("gravel_desert", List.of(
            new WeightedPlacedFeature(FloorFeatures.AGAVE.getPlacedFeature(), 0.5f),
            new WeightedPlacedFeature(FloorFeatures.BARREL_CACTUS.getPlacedFeature(), 0.1f),
            new WeightedPlacedFeature(VineLikeFeatures.NETHER_CACTUS.getPlacedFeature(), 0.2f)
    ), 0);

    public static final BCLFeature CRIMSON_GLOWING_WOODS_FLOOR = floorFromWeighted("crimson_glowing_woods", List.of(
            new WeightedPlacedFeature(TreeFeatures.CRIMSON_GLOWING.getPlacedFeature(), 0.50f),
            new WeightedPlacedFeature(FloorFeatures.CRIMSON_ROOTS.getPlacedFeature(), 0.30f),
            new WeightedPlacedFeature(FloorFeatures.CRIMSON_FUNGUS.getPlacedFeature(), 0.20f),
            new WeightedPlacedFeature(FloorFeatures.WART_BUSH_PATCH.getPlacedFeature(), 0.15f),
            new WeightedPlacedFeature(FloorFeatures.WART_SEED_PATCH.getPlacedFeature(), 0.15f)
    ), 1);
    public static final BCLFeature CRIMSON_GLOWING_WOODS_WALL = wallFromWeighted("crimson_glowing_woods", List.of(
            new WeightedPlacedFeature(WallFeatures.WALL_MOSS.getPlacedFeature(), 0.4f),
            new WeightedPlacedFeature(WallFeatures.WALL_MUSHROOM_RED.getPlacedFeature(), 0.6f)
    ), 1);
    public static final BCLFeature CRIMSON_PINEWOOD_WALL = CRIMSON_GLOWING_WOODS_WALL;
    public static final BCLFeature CRIMSON_PINEWOOD_FLOOR = floorFromWeighted("crimson_pinewood", List.of(
            new WeightedPlacedFeature(TreeFeatures.CRIMSON_PINEWOOD.getPlacedFeature(), 0.50f),
            new WeightedPlacedFeature(FloorFeatures.CRIMSON_ROOTS.getPlacedFeature(), 0.30f),
            new WeightedPlacedFeature(FloorFeatures.CRIMSON_FUNGUS.getPlacedFeature(), 0.20f),
            new WeightedPlacedFeature(FloorFeatures.WART_BUSH_PATCH.getPlacedFeature(), 0.15f),
            new WeightedPlacedFeature(FloorFeatures.WART_SEED_PATCH.getPlacedFeature(), 0.15f)
    ), 1);


    public static BCLFeature floorFromWeighted(String name, List<WeightedPlacedFeature> features, int defaultIndex) {
        return fromWeighted(name, features, defaultIndex, "floor", defaultFloorModifiers);
    }

    public static BCLFeature ceilFromWeighted(String name, List<WeightedPlacedFeature> features, int defaultIndex) {
        return fromWeighted(name, features, defaultIndex, "deil", defaultCeilModifiers);
    }

    public static BCLFeature wallFromWeighted(String name, List<WeightedPlacedFeature> features, int defaultIndex) {
        return fromWeighted(name, features, defaultIndex, "wall", defaultWallModifiers);
    }

    public static BCLFeature fromWeighted(String name,
                                          List<WeightedPlacedFeature> features,
                                          int defaultIndex,
                                          String postFix,
                                          List<PlacementModifier> modifiers) {
        return BCLFeatureBuilder
                .start(BetterNether.makeID(name + "_" + postFix), BCLFeature.RANDOM_SELECTOR)
                .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
                .modifier(modifiers)
                .buildAndRegister(features.size() == 1
                        ? new RandomFeatureConfiguration(List.of(),
                        features.get(0).feature)
                        : new RandomFeatureConfiguration(features, features.get(defaultIndex).feature));
    }

    public static void ensureStaticInitialization() {
    }
}
