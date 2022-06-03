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
import org.betterx.bclib.api.features.placement.FindSolidInDirection;
import org.betterx.bclib.api.features.placement.OnEveryLayer;
import org.betterx.betternether.BetterNether;

import java.util.List;

public class BiomeFeatures {
    private static final List<PlacementModifier> defaultCeilModifiers = List.of(CountPlacement.of(16),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_4_4,
            FindSolidInDirection.up(12),
            BiomeFilter.biome());
    private static final List<PlacementModifier> defaultFloorModifiers = List.of(CountPlacement.of(16),
            InSquarePlacement.spread(),
            OnEveryLayer.simple(),
            BiomeFilter.biome());
    public static final BCLFeature BONE_REEF_FLOOR = BCLFeatureBuilder
            .start(BetterNether.makeID("bone_reef_floor"), BCLFeature.RANDOM_SELECT)
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .modifier(defaultFloorModifiers)
            .buildAndRegister(new RandomFeatureConfiguration(List.of(
                    new WeightedPlacedFeature(FloorFeatures.BONES.getPlacedFeature(), 0.015f),
                    new WeightedPlacedFeature(FloorFeatures.BONE_GRASS_PATCH.getPlacedFeature(), 0.10f),
                    new WeightedPlacedFeature(FloorFeatures.FEATHER_FERN_PATCH.getPlacedFeature(), 0.05f),
                    new WeightedPlacedFeature(FloorFeatures.JELLYFISH_MUSHROOM.getPlacedFeature(), 0.01f),
                    new WeightedPlacedFeature(VineLikeFeatures.NETHER_REED.getPlacedFeature(), 0.03f),
                    new WeightedPlacedFeature(VineLikeFeatures.STALAGMITE_BONE_CLUSTER.getPlacedFeature(), 0.01f)
            ), FloorFeatures.BONE_GRASS_PATCH.getPlacedFeature()));

    public static final BCLFeature BONE_REEF_CEIL = BCLFeatureBuilder
            .start(BetterNether.makeID("bone_reef_ceil"), BCLFeature.RANDOM_SELECT)
            .decoration(GenerationStep.Decoration.VEGETAL_DECORATION)
            .modifier(defaultCeilModifiers)
            .buildAndRegister(new RandomFeatureConfiguration(List.of(),
                    VineLikeFeatures.LUMBUS_VINE.getPlacedFeature()));

    public static void ensureStaticInitialization() {
    }
}
