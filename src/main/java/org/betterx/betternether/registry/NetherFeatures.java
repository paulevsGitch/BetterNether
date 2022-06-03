package org.betterx.betternether.registry;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.features.config.TemplateFeatureConfig;
import org.betterx.bclib.api.tag.CommonBlockTags;
import org.betterx.bclib.world.structures.StructurePlacementType;
import org.betterx.bclib.world.structures.StructureWorldNBT;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockProperties;
import org.betterx.betternether.config.Configs;
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
    public static final Feature<ScatterFeatureConfigs.WithSizeOnBase> SCATTER_WITH_SIZE_ON_BASE = BCLFeature.register(
            BetterNether.makeID("scatter_with_size_on_base"),
            new ScatterFeature<>(ScatterFeatureConfigs.WithSizeOnBase.CODEC)
                                                                                                                     );

    public static final Feature<ScatterFeatureConfigs.WithPlantAge> SCATTER_WITH_PLANT_AGE = BCLFeature.register(
            BetterNether.makeID("scatter_with_age"),
            new ScatterFeature<>(ScatterFeatureConfigs.WithPlantAge.CODEC)
                                                                                                                );

    public static final BCLFeature CRYSTAL_FATURE = CrystalFeature.createAndRegister();
    public static final BCLFeature STALAGNATE_NETHERRACK_CLUSTER = ScatterFeature.createAndRegister(
            BetterNether.makeID("stalagmite_netherrack_cluster"),
            5,
            20,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.NETHERRACK_STALACTITE)
                                          .heightRange(2, 7)
                                          .spread(2, 0.3f)
                                          .floorChance(0.7f)
                                          .build(),
            NetherFeatures.SCATTER_WITH_SIZE);

    public static final BCLFeature STALAGNATE_BLACKSTONE_CLUSTER = ScatterFeature.createAndRegister(
            BetterNether.makeID("stalagmite_blackstone_cluster"),
            5,
            40,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BLACKSTONE_STALACTITE)
                                          .heightRange(2, 7)
                                          .spread(2, 0.5f)
                                          .floorChance(0.8f)
                                          .build(),
            NetherFeatures.SCATTER_WITH_SIZE);

    public static final BCLFeature STALAGNATE_BASALT_CLUSTER = ScatterFeature.createAndRegister(
            BetterNether.makeID("stalagmite_basalt_cluster"),
            5,
            40,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BASALT_STALACTITE)
                                          .heightRange(2, 7)
                                          .spread(2, 0.5f)
                                          .floorChance(0.8f)
                                          .build(),
            NetherFeatures.SCATTER_WITH_SIZE);

    public static final BCLFeature STALAGNATE_GLOWSTONE_CLUSTER = ScatterFeature.createAndRegister(
            BetterNether.makeID("stalagmite_glowstone_cluster"),
            2,
            4,
            ScatterFeatureConfigs.WithSizeOnBase.startWithSizeOnBase()
                                                .block(NetherBlocks.GLOWSTONE_STALACTITE)
                                                .generateBaseBlock(Blocks.GLOWSTONE.defaultBlockState())
                                                .heightRange(3, 8)
                                                .spread(3, 0.8f)
                                                .floorChance(0.5f)
                                                .build(),
            NetherFeatures.SCATTER_WITH_SIZE_ON_BASE);

    public static final BCLFeature STALAGMITE_BONE_CLUSTER = ScatterFeature.createAndRegister(
            BetterNether.makeID("stalagmite_bone_cluster"),
            6,
            10,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BONE_STALACTITE)
                                          .generateBaseBlock(Blocks.BONE_BLOCK.defaultBlockState(),
                                                             0.75f,
                                                             0.3f,
                                                             0.75f,
                                                             0.5f)
                                          .heightRange(3, 7)
                                          .spread(3, 0.2f)
                                          .onFloor()
                                          .build(),
            NetherFeatures.SCATTER_WITH_SIZE);

    public static final BCLFeature STALACTITE_BONE_CLUSTER = ScatterFeature.createAndRegister(
            BetterNether.makeID("stalagmite_bone_cluster"),
            6,
            10,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BONE_STALACTITE)
                                          .generateBaseBlock(Blocks.BONE_BLOCK.defaultBlockState(),
                                                             0.75f,
                                                             0.3f,
                                                             0.75f,
                                                             0.5f)
                                          .heightRange(3, 7)
                                          .spread(3, 0.2f)
                                          .onCeil()
                                          .build(),
            NetherFeatures.SCATTER_WITH_SIZE);

    // Veins //
    public static final BCLFeature GOLDEN_VINE = BCLFeatureBuilder
            .start(BetterNether.makeID("golden_vine"), BCLFeature.SCATTER_ON_SOLID)
            .count(16)
            .squarePlacement()
            .randomHeight4FromFloorCeil()
            .findSolidCeil(12)
            .isEmptyBelow2()
            .onlyInBiome()
            .buildAndRegister(ScatterFeatureConfig.OnSolid
                                      .startOnSolid()
                                      .block(NetherBlocks.GOLDEN_VINE.defaultBlockState()
                                                                     .setValue(BlockProperties.BOTTOM, false))
                                      .tipBlock(NetherBlocks.GOLDEN_VINE.defaultBlockState()
                                                                        .setValue(BlockProperties.BOTTOM, true))
                                      .heightRange(2, 12)
                                      .spread(3, 0.75f)
                                      .onCeil()
                                      .growWhileFree()
                                      .build()
                             );

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
    private static final ScatterFeatureConfig MAGMA_FLOWER_CONFIG = ScatterFeatureConfigs.WithPlantAge
            .startWithPlantAge()
            .singleBlock(NetherBlocks.MAGMA_FLOWER)
            .spread(4, 0, UniformInt.of(1, 16))
            .onFloor()
            .build();
    public static final BCLFeature MAGMA_FLOWER = BCLFeatureBuilder
            .start(BetterNether.makeID("magma_flower"), SCATTER_WITH_PLANT_AGE)
            .countRange(1, 6)
            .squarePlacement()
            .noiseBasedCount(0.3f, 4, 16)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.2f, -6, 6))
            .onEveryLayer()
            .onlyInBiome()
            .buildAndRegister(MAGMA_FLOWER_CONFIG);
    public static final BCLFeature MAGMA_FLOWER_SPARSE = BCLFeatureBuilder
            .start(BetterNether.makeID("magma_flower_sparse"), BCLFeature.SCATTER_ON_SOLID)
            .countRange(0, 2)
            .squarePlacement()
            .noiseBasedCount(0.3f, 4, 16)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.1f, -4, 4))
            .onEveryLayer()
            .onlyInBiome()
            .buildAndRegister(MAGMA_FLOWER_CONFIG);


    public static final BCLFeature GEYSER = BCLFeatureBuilder
            .start(BetterNether.makeID("geyser"), BCLFeature.SCATTER_ON_SOLID)
            .count(16)
            .squarePlacement()
            .randomHeight4FromFloorCeil()
            .findSolidFloor(12)
            .isEmptyAbove4()
            .onlyInBiome()
            .buildAndRegister(ScatterFeatureConfig.OnSolid
                                      .startOnSolid()
                                      .singleBlock(NetherBlocks.GEYSER)
                                      .spread(2, 0, UniformInt.of(1, 3))
                                      .onFloor()
                                      .build()
                             );

    public static final BCLFeature BLACK_BUSH = BCLFeatureBuilder
            .start(BetterNether.makeID("black_bush"), Feature.SIMPLE_BLOCK)
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

    public static final BCLFeature SOUL_GRASS = BCLFeatureBuilder
            .start(BetterNether.makeID("soul_grass"), Feature.SIMPLE_BLOCK)
            .countRange(4, 10)
            .squarePlacement()
            .noiseBasedCount(-0.2f, 0, 4)
            .spreadHorizontal(ClampedNormalInt.of(0, 1.2f, -6, 6))
            .onEveryLayer()
            .isEmptyAbove2()
            .onlyInBiome()
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(NetherBlocks.BLACK_BUSH)));

    // Landscape //
    public static final BCLFeature LAVA_BLOBS = BCLFeatureBuilder
            .start(BetterNether.makeID("lava_blobs"), Feature.SIMPLE_BLOCK)
            .onEveryLayer() //put a point on every layer of the world
            .stencilOneIn4() //select one in for from the stencil for all heights
            .onlyInBiome()
            .findSolidFloor(4) //try to find the floor
            .offset(Direction.DOWN)
            .inBasinOf(
                    BlockPredicate.matchesTag(CommonBlockTags.TERRAIN),
                    BlockPredicate.matchesBlocks(Blocks.LAVA)
                      ) //check if this is a Basin

            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LAVA)));

    public static final BCLFeature MAGMA_BLOBS = BCLFeatureBuilder
            .start(BetterNether.makeID("magma_blobs"), Feature.SIMPLE_BLOCK)
            .stencil()
            .onlyInBiome()
            .onEveryLayer()
            .offset(Direction.DOWN)
            .is(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
            .onlyInBiome()
            .extendDown(0, 3)
            .buildAndRegister(new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MAGMA_BLOCK)));
    // Walls //
    public static final BCLFeature WART_CAP_FEATURE = BCLFeatureBuilder
            .start(BetterNether.makeID("wart_cap"), new WartCapFeature())
            .count(32)
            .squarePlacement()
            .randomHeight10FromFloorCeil()
            .findSolidSurface(List.of(Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH), 12)
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

    // BONES //
    public static final BCLFeature JUNGLE_BONES = TemplateFeature.createAndRegisterRare(
            BetterNether.makeID("jungle_bones"),
            new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("jungle_bones_1"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("jungle_bones_2"), -1, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("jungle_bones_3"), -1, StructurePlacementType.FLOOR, 1.0f)
                                             )), 4);

    public static final BCLFeature BONES = TemplateFeature.createAndRegisterRare(
            BetterNether.makeID("bones"),
            new TemplateFeatureConfig(List.of(
                    cfg(BetterNether.makeID("bone_01"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("bone_02"), 0, StructurePlacementType.FLOOR, 1.0f),
                    cfg(BetterNether.makeID("bone_03"), 0, StructurePlacementType.FLOOR, 1.0f)
                                             )), 3);


    // MANAGE DEFAULT FEATTURES //


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
        LifeCycleAPI.onLevelLoad(NetherFeatures::onWorldLoad);
    }

    public static void onWorldLoad(ServerLevel level, long seed, Registry<Biome> registry) {
        CavesFeature.onLoad(seed);
        PathsFeature.onLoad(seed);

        CityFeature.initGenerator();
    }

}
