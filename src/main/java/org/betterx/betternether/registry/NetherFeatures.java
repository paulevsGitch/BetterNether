package org.betterx.betternether.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import com.google.common.collect.Lists;
import org.betterx.bclib.api.LifeCycleAPI;
import org.betterx.bclib.api.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.biomes.BiomeAPI;
import org.betterx.bclib.api.features.*;
import org.betterx.bclib.api.features.config.PlaceFacingBlockConfig;
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.features.config.TemplateFeatureConfig;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockNetherReed;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.registry.features.*;
import org.betterx.betternether.world.features.*;
import org.betterx.betternether.world.structures.city.CityFeature;

import java.util.List;
import java.util.function.Supplier;

public class NetherFeatures {
    private static final List<BCLFeature> defaultFeatures = Lists.newArrayList();

    // Surface Features //
    public static final Feature<ScatterFeatureConfigs.WithSize> SCATTER_WITH_SIZE = BCLFeature.register(
            BetterNether.makeID("scatter_with_size"),
            new ScatterFeature<>(ScatterFeatureConfigs.WithSize.CODEC)
    );


    public static final Feature<ScatterFeatureConfigs.WithPlantAge> SCATTER_WITH_PLANT_AGE = BCLFeature.register(
            BetterNether.makeID("scatter_with_age"),
            new ScatterFeature<>(ScatterFeatureConfigs.WithPlantAge.CODEC)
    );

    // Veins //

    public static final BCLFeature SOUL_VINE = BCLFeatureBuilder
            .start(BetterNether.makeID("soul_vine"), BCLFeature.SCATTER_ON_SOLID)
            .countRange(1, 6)
            .squarePlacement()
            .noiseBasedCount(-0.3f, 0, 4)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.1f, -3, 3))
            .onEveryLayer()
            .isEmptyAbove2()
            .onlyInBiome()
            .buildAndRegister(ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .singleBlock(NetherBlocks.SOUL_VEIN)
                    .generateBaseBlock(NetherBlocks.VEINED_SAND.defaultBlockState(), 0.4f, 0.6f, 0.2f)
                    .spread(2, 0.75f)
                    .onFloor()
                    .growWhileFree()
                    .build()
            );

    public static final BCLFeature NETHER_REEED = BCLFeatureBuilder
            .start(BetterNether.makeID("old_nether_reed"), BCLFeature.SCATTER_ON_SOLID)
            .countRange(2, 12)
            .squarePlacement()
            .noiseBasedCount(-0.3f, 0, 4)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.1f, -3, 3))
            .onEveryLayer()
            .isEmptyAbove2()
            .onlyInBiome()
            .buildAndRegister(ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .block(NetherBlocks.MAT_REED.getStem().defaultBlockState().setValue(BlockNetherReed.TOP, false))
                    .tipBlock(NetherBlocks.MAT_REED.getStem().defaultBlockState())
                    .spread(3, 0.75f, ConstantInt.of(24))
                    .heightRange(1, 3)
                    .onFloor()
                    .growWhileFree()
                    .build()
            );


    public static final BCLFeature BLACK_BUSH = BCLFeatureBuilder
            .start(BetterNether.makeID("old_black_bush"), Feature.SIMPLE_BLOCK)
            .countRange(2, 10)
            .squarePlacement()
            .noiseBasedCount(0.25f, 0, 4)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.2f, -6, 6))
            .onEveryLayer()
            .isEmptyAbove2()
            .onlyInBiome()
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(NetherBlocks.BLACK_BUSH)));

    public static final BCLFeature BLACK_BUSH_SPARSE = BCLFeatureBuilder
            .start(BetterNether.makeID("black_bush_sparse"), Feature.SIMPLE_BLOCK)
            .countRange(1, 4)
            .squarePlacement()
            .noiseBasedCount(0.25f, 0, 2)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.2f, -2, 2))
            .onEveryLayer()
            .isEmptyAbove2()
            .onlyInBiome()
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(NetherBlocks.BLACK_BUSH)));


    public static final BCLFeature FEATHER_FERN = BCLFeatureBuilder
            .start(BetterNether.makeID("old_feather_fern"), Feature.SIMPLE_BLOCK)
            .countRange(3, 15)
            .squarePlacement()
            .noiseBasedCount(0.76f, 0, 4)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.8f, -6, 6))
            .findSolidFloor(12)
            .isEmptyAbove2()
            .onlyInBiome()
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(NetherBlocks.FEATHER_FERN)));

    // Landscape //
    public static final BCLFeature WART_CAP_FEATURE = BCLFeatureBuilder
            .start(BetterNether.makeID("wart_cap"), new WartCapFeature())
            .count(32)
            .squarePlacement()
            .randomHeight10FromFloorCeil()
            .findSolidSurface(PlaceFacingBlockConfig.HORIZONTAL, 12, false)
            .buildAndRegister();

    // Ores //
    public static final BCLFeature CINCINNASITE_ORE =
            registerOre("cincinnasite", NetherBlocks.CINCINNASITE_ORE,
                    10, 8, 0.0f,
                    PlacementUtils.RANGE_10_10,
                    false);

    public static final BCLFeature NETHER_RUBY_ORE =
            registerOre("nether_ruby", NetherBlocks.NETHER_RUBY_ORE,
                    3, 8, 0, //0.6f,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.belowTop(32)),
                    false);

    public static final BCLFeature NETHER_RUBY_ORE_SOUL =
            registerOre("nether_ruby_soul", NetherBlocks.NETHER_RUBY_ORE, Blocks.SOUL_SOIL,
                    16, 12, 0, //0.6f,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.top()),
                    false);

    public static final BCLFeature NETHER_RUBY_ORE_LARGE =
            registerOre("nether_ruby_large", NetherBlocks.NETHER_RUBY_ORE,
                    16, 12, 0, //0.6f,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.top()),
                    false);

    public static final BCLFeature NETHER_RUBY_ORE_RARE =
            registerOre("nether_ruby_rare", NetherBlocks.NETHER_RUBY_ORE,
                    2, 12, 0.0f,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(70), VerticalAnchor.top()),
                    true);

    public static final BCLFeature NETHER_LAPIS_ORE =
            registerOre("nether_lapis", NetherBlocks.NETHER_LAPIS_ORE,
                    18, 4, 0.0f,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(32), VerticalAnchor.belowTop(10)),
                    false);

    public static final BCLFeature NETHER_REDSTONE_ORE =
            registerOre("nether_redstone", NetherBlocks.NETHER_REDSTONE_ORE,
                    1, 16, 0.3f,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(40)),
                    true);

    // Maintainance //
    public static final BCLFeature CLEANUP_FEATURE = registerChunkFeature("nether_clean",
            Decoration.RAW_GENERATION,
            CleanupFeature::new);
    public static final BCLFeature FIX_FEATURE = registerChunkFeature("nether_fix",
            Decoration.TOP_LAYER_MODIFICATION,
            BlockFixFeature::new);

    // Terrain //
    public static final BCLFeature CAVES_FEATURE = registerChunkFeature("nether_caves",
            Decoration.UNDERGROUND_STRUCTURES,
            CavesFeature::new);
    public static final BCLFeature PATHS_FEATURE = registerChunkFeature("nether_paths",
            Decoration.LAKES,
            PathsFeature::new);
    public static final BCLFeature POPULATOR_FEATURE = registerChunkFeature("nether_populator",
            Decoration.VEGETAL_DECORATION,
            NetherChunkPopulatorFeature::new);

    // Cached Config data //
    public static final boolean HAS_CLEANING_PASS = Configs.GENERATOR.getBoolean("generator.world.terrain",
            "terrain_cleaning_pass",
            true);
    public static final boolean HAS_CAVES = Configs.GENERATOR.getBoolean("generator.world.environment",
            "generate_caves",
            true);
    public static final boolean HAS_PATHS = Configs.GENERATOR.getBoolean("generator.world.environment",
            "generate_paths",
            true);
    public static final boolean HAS_FIXING_PASS = Configs.GENERATOR.getBoolean("generator.world.terrain",
            "world_fixing_pass",
            true);

    private static <T extends DefaultFeature> BCLFeature registerChunkFeature(String name,
                                                                              Decoration step,
                                                                              Supplier<T> feature) {
        return BCLCommonFeatures.makeChunkFeature(
                BetterNether.makeID("feature_" + name),
                step,
                feature.get()
        );
    }

    private static BCLFeature registerOre(String name,
                                          Block blockOre,
                                          Block baseBlock,
                                          int veins,
                                          int veinSize,
                                          float airDiscardChance,
                                          PlacementModifier placement,
                                          boolean rare) {
        return _registerOre(
                name + "_ore",
                blockOre,
                baseBlock,
                Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_count", veins),
                Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_size", veinSize),
                Configs.GENERATOR.getFloat("generator.world.ores." + name, "air_discard_chance", airDiscardChance),
                placement,
                rare
        );
    }

    private static BCLFeature registerOre(String name,
                                          Block blockOre,
                                          int veins,
                                          int veinSize,
                                          float airDiscardChance,
                                          PlacementModifier placement,
                                          boolean rare) {
        return _registerOre(
                name + "_ore",
                blockOre,
                Blocks.NETHERRACK,
                Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_count", veins),
                Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_size", veinSize),
                Configs.GENERATOR.getFloat("generator.world.ores." + name, "air_discard_chance", airDiscardChance),
                placement,
                rare
        );
    }

    private static BCLFeature _registerOre(String name,
                                           Block blockOre,
                                           Block baseBlock,
                                           int veins,
                                           int veinSize,
                                           float airDiscardChance,
                                           PlacementModifier placementModifier,
                                           boolean rare) {
        return BCLCommonFeatures.makeOreFeature(
                BetterNether.makeID(name),
                blockOre,
                baseBlock,
                veins,
                veinSize,
                airDiscardChance,
                placementModifier,
                rare);
    }

    // MANAGE DEFAULT FEATURES //
    public static StructureWorldNBT cfg(ResourceLocation location,
                                        int offsetY,
                                        StructurePlacementType type,
                                        float chance) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }

    private static BCLFeature registerDefault(BCLFeature f) {
        defaultFeatures.add(f);
        return f;
    }

    private static BCLFeature registerDefault(ResourceLocation location,
                                              List<StructureWorldNBT> structures,
                                              int onceEveryChunk) {
        return registerDefault(TemplateFeature.createAndRegister(
                location,
                new TemplateFeatureConfig(structures),
                onceEveryChunk));
    }

    public static BCLBiomeBuilder addDefaultFeatures(BCLBiomeBuilder builder) {
        //if (NetherFeatures.HAS_CLEANING_PASS) builder.feature(CLEANUP_FEATURE);
        //if (NetherFeatures.HAS_CAVES) builder.feature(CAVES_FEATURE);
        //if (NetherFeatures.HAS_PATHS) builder.feature(PATHS_FEATURE);
        //if (NetherFeatures.HAS_FIXING_PASS) builder.feature(FIX_FEATURE);

        //builder.feature(POPULATOR_FEATURE);

        return builder;
    }

    public static void addDefaultBNFeatures(BCLBiomeBuilder builder) {
        for (BCLFeature f : defaultFeatures) {
            builder.feature(f);
        }
    }

    public static BCLBiomeBuilder addDefaultOres(BCLBiomeBuilder builder) {
        return builder
                .feature(CINCINNASITE_ORE)
                .feature(NETHER_RUBY_ORE_RARE)
                .feature(NETHER_LAPIS_ORE)
                .feature(NETHER_REDSTONE_ORE);
    }

    public static void modifyNonBNBiome(ResourceLocation biomeID, Holder<Biome> biome) {
//        if (NetherFeatures.HAS_CAVES) {
//            BiomeAPI.addBiomeFeature(biome, CAVES_FEATURE);
//        }
//        if (NetherFeatures.HAS_PATHS) {
//            BiomeAPI.addBiomeFeature(biome, PATHS_FEATURE);
//        }

        //BiomeAPI.addBiomeFeature(biome, POPULATOR_FEATURE);

        BiomeAPI.addBiomeFeature(biome, CINCINNASITE_ORE);
        BiomeAPI.addBiomeFeature(biome, NETHER_RUBY_ORE_RARE);
        BiomeAPI.addBiomeFeature(biome, NETHER_LAPIS_ORE);
        BiomeAPI.addBiomeFeature(biome, NETHER_REDSTONE_ORE);

        if (biomeID.equals(BiomeAPI.SOUL_SAND_VALLEY_BIOME.getID())) {
            BiomeAPI.addBiomeFeature(biome, NETHER_RUBY_ORE_LARGE);
        }

        if (biomeID.equals(BiomeAPI.CRIMSON_FOREST_BIOME.getID()) || biomeID.equals(BiomeAPI.WARPED_FOREST_BIOME.getID())) {
            BiomeAPI.addBiomeFeature(biome, NETHER_RUBY_ORE);
        }
    }

    public static void register() {
        FloorFeatures.ensureStaticInitialization();
        VineLikeFeatures.ensureStaticInitialization();
        WallFeatures.ensureStaticInitialization();
        TreeFeatures.ensureStaticInitialization();
        TerrainFeatures.ensureStaticInitialization();
        BiomeFeatures.ensureStaticInitialization();
        LifeCycleAPI.onLevelLoad(NetherFeatures::onWorldLoad);
    }

    public static void onWorldLoad(ServerLevel level, long seed, Registry<Biome> registry) {
        CavesFeature.onLoad(seed);
        PathsFeature.onLoad(seed);

        CityFeature.initGenerator();
    }

}
