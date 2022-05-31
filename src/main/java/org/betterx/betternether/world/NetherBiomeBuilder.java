package org.betterx.betternether.world;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.NetherBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import org.betterx.bclib.api.biomes.BCLBiome;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.util.MHelper;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.registry.NetherEntities;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.registry.NetherStructures;
import org.betterx.betternether.registry.NetherTags;

public class NetherBiomeBuilder {
    private static final RandomSource RANDOM = new LegacyRandomSource(130520221830l);
    private static Biome BASE_BIOME;
    static final SurfaceRules.RuleSource BEDROCK = SurfaceRules.state(Blocks.BEDROCK.defaultBlockState());
    //(ResourceLocation randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove)
    static final SurfaceRules.VerticalGradientConditionSource BEDROCK_BOTTOM =
            new SurfaceRules.VerticalGradientConditionSource(BetterNether.makeID("bedrock_floor"),
                                                             VerticalAnchor.bottom(),
                                                             VerticalAnchor.aboveBottom(5));
    static final SurfaceRules.VerticalGradientConditionSource BEDROCK_TOP =
            new SurfaceRules.VerticalGradientConditionSource(BetterNether.makeID("bedrock_roof"),
                                                             VerticalAnchor.belowTop(5),
                                                             VerticalAnchor.top());

    private static void addVanillaStructures(BCLBiomeBuilder builder) {
        builder.carver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        builder.structure(BiomeTags.HAS_RUINED_PORTAL_NETHER);
    }

    private static void addVanillaFeatures(BCLBiomeBuilder builder) {
        builder
                .feature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
                .defaultMushrooms()
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
                .feature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
    }

    private static void addBNStructures(BCLBiomeBuilder builder) {
        builder.structure(NetherStructures.PYRAMID_1);
    }


    public static NetherBiome create(NetherBiomeConfig data) {
        return create(data, null);
    }


    public static NetherBiome create(NetherBiomeConfig data, BCLBiome edgeBiome) {
        if (BASE_BIOME == null) {
            BASE_BIOME = NetherBiomes.netherWastes();
        }
        final ResourceLocation ID = data.ID;

        BCLBiomeBuilder builder = BCLBiomeBuilder
                .start(ID)
                //.category(BiomeCategory.NETHER)
                .surface(data.surface().build())
                .tag(NetherTags.BETTER_NETHER)
                .temperature(BASE_BIOME.getBaseTemperature())
                .wetness(BASE_BIOME.getDownfall())
                .precipitation(Precipitation.NONE)
                .waterColor(BASE_BIOME.getWaterColor())
                .waterFogColor(BASE_BIOME.getWaterFogColor())
                .skyColor(BASE_BIOME.getSkyColor())
                .music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
                .mood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
                .loop(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
                .additions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
                .edge(edgeBiome)
                .addNetherClimateParamater(MHelper.randRange(-1.5F, 1.5F, RANDOM),
                                           MHelper.randRange(-1.5F, 1.5F, RANDOM));


        if (data.hasVanillaStructures()) addVanillaStructures(builder);
        if (data.hasBNStructures()) addBNStructures(builder);
        if (data.hasVanillaFeatures()) addVanillaFeatures(builder);
        if (data.hasVanillaOres()) builder.netherDefaultOres();

        if (data.hasBNFeatures()) NetherFeatures.addDefaultBNFeatures(builder);

        for (NetherEntities.KnownSpawnTypes spawnType : NetherEntities.KnownSpawnTypes.values()) {
            spawnType.addSpawn(builder, data);
        }

        NetherFeatures.addDefaultFeatures(builder);

        if (data.hasStalactites()) {
            builder.feature(NetherFeatures.STALAGNATE_NETHERRACK_CLUSTER);
            builder.feature(NetherFeatures.STALAGNATE_GLOWSTONE_CLUSTER);
        }

        if (data.hasDefaultOres()) NetherFeatures.addDefaultOres(builder);
        NetherStructures.addDefaultFeatures(builder);

        data.addCustomBuildData(builder);

        NetherBiome b = builder.build(data.getSupplier());
        return b;
    }
}
