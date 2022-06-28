package org.betterx.betternether.registry;

import org.betterx.bclib.api.v2.LifeCycleAPI;
import org.betterx.bclib.api.v2.levelgen.biomes.BCLBiomeBuilder;
import org.betterx.bclib.api.v2.levelgen.biomes.BiomeAPI;
import org.betterx.bclib.api.v2.levelgen.features.features.ScatterFeature;
import org.betterx.bclib.api.v2.levelgen.structures.StructurePlacementType;
import org.betterx.bclib.api.v2.levelgen.structures.StructureWorldNBT;
import org.betterx.bclib.api.v3.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v3.levelgen.features.BCLPlacedFeatureBuilder;
import org.betterx.bclib.api.v3.levelgen.features.config.TemplateFeatureConfig;
import org.betterx.betternether.BN;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.config.Configs;
import org.betterx.betternether.registry.features.configured.*;
import org.betterx.betternether.registry.features.placed.*;
import org.betterx.betternether.world.features.*;
import org.betterx.betternether.world.structures.city.CityStructure;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import com.google.common.collect.Lists;

import java.util.List;

public class NetherFeatures {
    public static final Feature<NoneFeatureConfiguration> JELLYFISH_MUSHROOM = BCLFeature.register(
            BN.id("jellyfish_mushroom"),
            new JellyfishMushroomFeature()
    );
    public static final Feature<NoneFeatureConfiguration> OBSIDIAN_CRYSTAL = BCLFeature.register(
            BN.id("obsidian_crystal"),
            new CrystalFeature()
    );
    public static final Feature<NoneFeatureConfiguration> WART_BUSH = BCLFeature.register(
            BN.id("wart_bush"),
            new WartBushFeature()
    );
    public static final RubeusTreeFeature RUBEUS_TREE = BCLFeature.register(
            BN.id("rubeus_tree"),
            new RubeusTreeFeature()
    );
    public static final MushroomFirFeature MUSHROOM_FIR = BCLFeature.register(
            BN.id("mushroom_fir"),
            new MushroomFirFeature()
    );
    public static final BigBrownMushroomFeature BIG_BROWN_MUSHROOM = BCLFeature.register(
            BN.id("big_brown_mushroom"),
            new BigBrownMushroomFeature()
    );
    public static final Feature<NoneFeatureConfiguration> RUBEUS_BUSH = BCLFeature.register(
            BN.id("rubeus_bush"),
            new RubeusBushFeature()
    );
    public static final Feature<NoneFeatureConfiguration> LUCIS = BCLFeature.register(
            BN.id("lucis"),
            new LucisFeature()
    );
    public static final SoulLilyFeature SOUL_LILY = BCLFeature.register(
            BN.id("soul_lily"),
            new SoulLilyFeature()
    );
    public static final WartTreeFeature WART_TREE = BCLFeature.register(
            BN.id("wart_tree"),
            new WartTreeFeature()
    );
    public static final WillowBushFeature WILLOW_BUSH = BCLFeature.register(
            BN.id("willow_bush"),
            new WillowBushFeature()
    );
    public static final WillowTreeFeature WILLOW_TREE = BCLFeature.register(
            BN.id("willow_tree"),
            new WillowTreeFeature()
    );
    public static final OldWillowTree OLD_WILLOW_TREE = BCLFeature.register(
            BN.id("old_willow_tree"),
            new OldWillowTree()
    );
    public static final NetherSakuraFeature SAKURA_TREE = BCLFeature.register(
            BN.id("sakura_tree"),
            new NetherSakuraFeature()
    );
    public static final NetherSakuraBushFeature SAKURA_BUSH = BCLFeature.register(
            BN.id("sakura_bush"),
            new NetherSakuraBushFeature()
    );
    public static final AnchorTreeBranchFeature ANCHOR_TREE_BRANCH = BCLFeature.register(
            BN.id("anchor_tree_branch"),
            new AnchorTreeBranchFeature()
    );
    public static final AnchorTreeFeature ANCHOR_TREE = BCLFeature.register(
            BN.id("anchor_tree"),
            new AnchorTreeFeature()
    );
    public static final AnchorTreeRootFeature ANCHOR_TREE_ROOT = BCLFeature.register(
            BN.id("anchor_tree_root"),
            new AnchorTreeRootFeature()
    );
    public static final WartCapFeature WART_CAP = BCLFeature.register(
            BN.id("wart_cap"),
            new WartCapFeature()
    );
    public static final TwistedVinesFeature TWISTING_VINES = BCLFeature.register(
            BN.id("twisting_vines"),
            new TwistedVinesFeature()
    );
    private static final List<BCLFeature> defaultFeatures = Lists.newArrayList();

    // Surface Features //
    public static final Feature<ScatterFeatureConfigs.WithSize> SCATTER_WITH_SIZE = BCLFeature.register(
            BetterNether.makeID("scatter_with_size"),
            new ScatterFeature<>(ScatterFeatureConfigs.WithSize.CODEC)
    );


    // Ores //
    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> CINCINNASITE_ORE =
            registerOre("cincinnasite", NetherBlocks.CINCINNASITE_ORE,
                    10, 8, 0.0f,
                    PlacementUtils.RANGE_10_10,
                    false
            );

    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> NETHER_RUBY_ORE =
            registerOre("nether_ruby", NetherBlocks.NETHER_RUBY_ORE,
                    3, 8, 0, //0.6f,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.belowTop(32)),
                    false
            );

    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> NETHER_RUBY_ORE_SOUL =
            registerOre("nether_ruby_soul", NetherBlocks.NETHER_RUBY_ORE, Blocks.SOUL_SOIL,
                    16, 12, 0, //0.6f,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.top()),
                    false
            );

    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> NETHER_RUBY_ORE_LARGE =
            registerOre("nether_ruby_large", NetherBlocks.NETHER_RUBY_ORE,
                    16, 12, 0, //0.6f,
                    HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(32), VerticalAnchor.top()),
                    false
            );

    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> NETHER_RUBY_ORE_RARE =
            registerOre("nether_ruby_rare", NetherBlocks.NETHER_RUBY_ORE,
                    2, 12, 0.0f,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(70), VerticalAnchor.top()),
                    true
            );

    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> NETHER_LAPIS_ORE =
            registerOre("nether_lapis", NetherBlocks.NETHER_LAPIS_ORE,
                    18, 4, 0.0f,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(32), VerticalAnchor.belowTop(10)),
                    false
            );

    public static final org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> NETHER_REDSTONE_ORE =
            registerOre("nether_redstone", NetherBlocks.NETHER_REDSTONE_ORE,
                    1, 16, 0.3f,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(40)),
                    true
            );


    private static org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> registerOre(
            String name,
            Block blockOre,
            Block baseBlock,
            int veins,
            int veinSize,
            float airDiscardChance,
            PlacementModifier placement,
            boolean rare
    ) {
        int veins1 = Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_count", veins);
        BCLPlacedFeatureBuilder<OreFeature, OreConfiguration> builder = org.betterx.bclib.api.v3.levelgen.features.BCLFeatureBuilder
                .startOre(BetterNether.makeID(name + "_ore"))
                .add(baseBlock, blockOre)
                .veinSize(Configs.GENERATOR.getInt("generator.world.ores." + name, "vein_size", veinSize))
                .discardChanceOnAirExposure(Configs.GENERATOR.getFloat(
                        "generator.world.ores." + name,
                        "air_discard_chance",
                        airDiscardChance
                ))
                .buildAndRegister()
                .place()
                .decoration(Decoration.UNDERGROUND_DECORATION);

        if (rare) {
            builder.onceEvery(veins1);
        } else {
            builder.count(veins1);
        }

        builder.modifier(placement).squarePlacement().onlyInBiome();

        return builder.buildAndRegister();
    }

    private static org.betterx.bclib.api.v3.levelgen.features.BCLFeature<OreFeature, OreConfiguration> registerOre(
            String name,
            Block blockOre,
            int veins,
            int veinSize,
            float airDiscardChance,
            PlacementModifier placement,
            boolean rare
    ) {
        return registerOre(
                name,
                blockOre,
                Blocks.NETHERRACK,
                veins,
                veinSize,
                airDiscardChance,
                placement,
                rare
        );
    }

    // MANAGE DEFAULT FEATURES //
    public static StructureWorldNBT cfg(
            ResourceLocation location,
            int offsetY,
            StructurePlacementType type,
            float chance
    ) {
        return TemplateFeatureConfig.cfg(location, offsetY, type, chance);
    }


    public static BCLBiomeBuilder addDefaultFeatures(BCLBiomeBuilder builder) {
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
        NetherVegetation.ensureStaticInitialization();
        NetherVegetationPlaced.ensureStaticInitialization();
        NetherVines.ensureStaticInitialization();
        NetherVinesPlaced.ensureStaticInitialization();
        NetherTrees.ensureStaticInitialization();
        NetherTreesPlaced.ensureStaticInitialization();
        NetherObjects.ensureStaticInitialization();
        NetherObjectsPlaced.ensureStaticInitialization();
        NetherTerrain.ensureStaticInitialization();
        NetherTerrainPlaced.ensureStaticInitialization();
        LifeCycleAPI.onLevelLoad(NetherFeatures::onWorldLoad);
    }

    public static void onWorldLoad(ServerLevel level, long seed, Registry<Biome> registry) {
        CavesFeature.onLoad(seed);
        PathsFeature.onLoad(seed);

        CityStructure.initGenerator();
    }

}
