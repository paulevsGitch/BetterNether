package org.betterx.betternether.registry.features;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import org.betterx.bclib.api.v2.levelgen.features.BCLFeature;
import org.betterx.bclib.api.v2.levelgen.features.BCLFeatureBuilder;
import org.betterx.bclib.api.v2.levelgen.features.FastFeatures;
import org.betterx.bclib.api.v2.levelgen.features.config.ScatterFeatureConfig;
import org.betterx.bclib.api.v2.tag.CommonBlockTags;
import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNBlockProperties;
import org.betterx.betternether.blocks.BlockNetherCactus;
import org.betterx.betternether.blocks.BlockNetherReed;
import org.betterx.betternether.blocks.BlockWhisperingGourdVine;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.ScatterFeatureConfigs;
import org.betterx.betternether.world.features.TwistedVinesFeature;
import org.betterx.betternether.world.features.configs.NaturalTreeConfiguration;

public class VineLikeFeatures {
    public static final BCLFeature GOLDEN_VINE
            = FastFeatures.vine(
            BetterNether.makeID("golden_vine"), false, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .block(NetherBlocks.GOLDEN_VINE.defaultBlockState()
                                                   .setValue(BNBlockProperties.BOTTOM, false))
                    .tipBlock(NetherBlocks.GOLDEN_VINE.defaultBlockState()
                                                      .setValue(BNBlockProperties.BOTTOM, true))
                    .heightRange(2, 12)
                    .spread(3, 0.75f)
                    .growWhileFree()
    );
    public static final BCLFeature LUMBUS_VINE
            = FastFeatures.vine(
            BetterNether.makeID("lumbus_vine"), false, true,
            ScatterFeatureConfig
                    .startOnSolid()
                    .tripleShape(NetherBlocks.LUMABUS_VINE)
                    .spread(2, 0.75f)
                    .heightRange(2, 8)
                    .growWhileFree()
    );

    public static final BCLFeature GOLDEN_LUMBUS_VINE
            = FastFeatures.vine(
            BetterNether.makeID("golden_lumbus_vine"), false, true,
            ScatterFeatureConfig
                    .startOnSolid()
                    .tripleShape(NetherBlocks.GOLDEN_LUMABUS_VINE)
                    .spread(2, 0.85f)
                    .heightRange(2, 25)
                    .growWhileFree()
    );
    public static final BCLFeature BLACK_VINE
            = FastFeatures.vine(
            BetterNether.makeID("black_vine"), false, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .singleBlock(NetherBlocks.BLACK_VINE)
                    .spread(4, 0.85f, UniformInt.of(2, 6))
                    .heightRange(2, 18)
                    .growWhileFree()
    );
    public static final BCLFeature BLOOMING_VINE
            = FastFeatures.vine(
            BetterNether.makeID("blooming_vine"), false, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .singleBlock(NetherBlocks.BLOOMING_VINE)
                    .spread(4, 0.9f, UniformInt.of(8, 16))
                    .heightRange(4, 24)
                    .growWhileFree()
    );

    public static final BCLFeature SOUL_VEIN
            = FastFeatures.vine(
            BetterNether.makeID("soul_vein"), true, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .singleBlock(NetherBlocks.SOUL_VEIN)
                    .generateBaseBlock(NetherBlocks.VEINED_SAND.defaultBlockState(), 0.4f, 0.6f, 0.2f)
                    .spread(2, 0.75f)
    );

    public static final BCLFeature NETHER_REED
            = FastFeatures.vine(
            BetterNether.makeID("nether_reed"), true, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .block(NetherBlocks.MAT_REED.getStem().defaultBlockState().setValue(BlockNetherReed.TOP, false))
                    .tipBlock(NetherBlocks.MAT_REED.getStem().defaultBlockState())
                    .spread(4, 0f, ConstantInt.of(64))
                    .heightRange(1, 3)
    );

    public static final BCLFeature NETHER_CACTUS
            = FastFeatures.vine(
            BetterNether.makeID("nether_cactus"), true, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .block(NetherBlocks.NETHER_CACTUS.defaultBlockState().setValue(BlockNetherCactus.TOP, false))
                    .tipBlock(NetherBlocks.NETHER_CACTUS.defaultBlockState().setValue(BlockNetherCactus.TOP, true))
                    .heightRange(2, 5)
    );

    public static final BCLFeature SMOKER
            = FastFeatures.vine(
            BetterNether.makeID("smoker"), true, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .tripleShape(NetherBlocks.SMOKER)
                    .heightRange(1, 5)
                    .spread(2, 0.75f, ClampedNormalInt.of(7, 1.2f, 3, 12))
    );

    public static final BCLFeature EYE
            = FastFeatures.vine(
            BetterNether.makeID("eye"), false, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .block(NetherBlocks.EYE_VINE)
                    .tipBlock(new WeightedStateProvider(SimpleWeightedRandomList
                            .<BlockState>builder()
                            .add(NetherBlocks.EYEBALL.defaultBlockState(), 5)
                            .add(NetherBlocks.EYEBALL_SMALL.defaultBlockState(), 5)))
                    .heightRange(5, 26)
                    .growWhileFree()
                    .spread(3, 0.75f, ClampedNormalInt.of(7, 1.2f, 3, 12))
    );


    public static final BCLFeature NEON_EQUISETUM = FastFeatures.scatter(
            BetterNether.makeID("neon_equisetum"), false, false,
            ScatterFeatureConfig
                    .startExtendBottom()
                    .block(NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE,
                            BlockProperties.TripleShape.MIDDLE))
                    .tipBlock(NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE,
                            BlockProperties.TripleShape.BOTTOM))
                    .bottomBlock(NetherBlocks.NEON_EQUISETUM.defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE,
                            BlockProperties.TripleShape.TOP))
                    .heightRange(3, 21)
                    .spread(2, 0.75f, ClampedNormalInt.of(4, 1.2f, 3, 12)),
            BCLFeature.SCATTER_EXTEND_BOTTOM
    );

    public static final BCLFeature WHISPERING_GOURD = FastFeatures.vine(
            BetterNether.makeID("whispering_gourd"), false, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .block(new WeightedStateProvider(SimpleWeightedRandomList
                            .<BlockState>builder()
                            .add(NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                                   .setValue(BlockWhisperingGourdVine.SHAPE,
                                                                           BlockProperties.TripleShape.TOP), 5)
                            .add(NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                                   .setValue(BlockWhisperingGourdVine.SHAPE,
                                                                           BlockProperties.TripleShape.MIDDLE), 5)))
                    .bottomBlock(NetherBlocks.WHISPERING_GOURD_VINE.defaultBlockState()
                                                                   .setValue(BlockWhisperingGourdVine.SHAPE,
                                                                           BlockProperties.TripleShape.BOTTOM))
                    .heightRange(1, 5)
                    .spread(4, 0.8f, ClampedNormalInt.of(7, 1.2f, 3, 12))
    );

    public static final BCLFeature STALAGNATE_FLOOR
            = FastFeatures.vine(
            BetterNether.makeID("stalagnate"), true, false,
            ScatterFeatureConfig
                    .startOnSolid()
                    .tripleShape(NetherBlocks.MAT_STALAGNATE.getTrunk())
                    .heightRange(3, 27)
                    .growWhileFree()
                    .spread(3, 0, ClampedNormalInt.of(7, 1.2f, 3, 12))
    );
    public static final BCLFeature STALAGNATE_CEIL
            = FastFeatures.vine(
            BetterNether.makeID("stalagnate_ceil"), false, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .tripleShape(NetherBlocks.MAT_STALAGNATE.getTrunk())
                    .heightRange(3, 27)
                    .growWhileFree()
                    .spread(3, 0f, ClampedNormalInt.of(7, 1.2f, 3, 12))
    );

    public static final BCLFeature STALAGMITE_BONE_CLUSTER = sizedCluster(
            BetterNether.makeID("stalagmite_bone_cluster"), true, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BONE_STALACTITE)
                                          .generateBaseBlock(NetherBlocks.BONE_BLOCK.defaultBlockState(),
                                                  0.95f,
                                                  0.3f,
                                                  0.75f,
                                                  0.5f)
                                          .heightRange(2, 7)
                                          .spread(3, 0f, UniformInt.of(3, 16))
    );

    public static final BCLFeature STALAGMITE_BLACKSTONE_CLUSTER = sizedCluster(
            BetterNether.makeID("stalagmite_blackstone_cluster"), true, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BLACKSTONE_STALACTITE)
                                          .generateBaseBlock(
                                                  Blocks.BLACK_GLAZED_TERRACOTTA.defaultBlockState(),
                                                  0.95f, 0.3f, 0.75f, 0.5f
                                          )
                                          .heightRange(2, 7)
                                          .spread(2, 0.2f, UniformInt.of(5, 6))
    );

    public static final BCLFeature STALAGMITE_BASALT_CLUSTER = sizedCluster(
            BetterNether.makeID("stalagmite_basalt_cluster"), true, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BASALT_STALACTITE)
                                          .generateBaseBlock(
                                                  Blocks.DEEPSLATE.defaultBlockState(),
                                                  1, 0.6f, 0.75f, 0.5f
                                          )
                                          .heightRange(2, 7)
                                          .spread(2, 0.2f, UniformInt.of(5, 6))
    );

    public static final BCLFeature STALAGMITE_NETHERRACK_CLUSTER = sizedCluster(
            BetterNether.makeID("stalagmite_netherrack_cluster"), true, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.NETHERRACK_STALACTITE)
                                          .heightRange(2, 7)
                                          .spread(4, 0.3f, ClampedNormalInt.of(8, 1.4f, 4, 10))
    );

    public static final BCLFeature STALACTITE_BLACKSTONE_CLUSTER = sizedCluster(
            BetterNether.makeID("stalactite_blackstone_cluster"), false, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BLACKSTONE_STALACTITE)
                                          .generateBaseBlock(
                                                  Blocks.BLACK_GLAZED_TERRACOTTA.defaultBlockState(),
                                                  0.95f, 0.3f, 0.75f, 0.5f
                                          )
                                          .heightRange(2, 7)
                                          .spread(2, 0.2f, UniformInt.of(5, 6))
    );

    public static final BCLFeature STALACTITE_BASALT_CLUSTER = sizedCluster(
            BetterNether.makeID("stalactite_basalt_cluster"), false, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.BASALT_STALACTITE)
                                          .generateBaseBlock(
                                                  Blocks.DEEPSLATE.defaultBlockState(),
                                                  1, 0.6f, 0.75f, 0.5f
                                          )
                                          .heightRange(2, 7)
                                          .spread(2, 0.2f, UniformInt.of(5, 6))
    );

    public static final BCLFeature STALACTITE_NETHERRACK_CLUSTER = sizedCluster(
            BetterNether.makeID("stalactite_netherrack_cluster"), false, false,
            ScatterFeatureConfigs.WithSize.startWithSize()
                                          .block(NetherBlocks.NETHERRACK_STALACTITE)
                                          .heightRange(2, 7)
                                          .spread(4, 0.3f, ClampedNormalInt.of(8, 1.4f, 4, 10))
    );

    public static final BCLFeature TWISTED_VINES = FastFeatures.patch(BetterNether.makeID("twisted_vines"),
            10, 8, 4,
            BCLFeatureBuilder
                    .start(BetterNether.makeID("twisted_vines"), new TwistedVinesFeature())
                    .isAbove(BlockPredicate.matchesTag(CommonBlockTags.TERRAIN))
                    .buildAndRegister(NaturalTreeConfiguration.naturalLarge()));


    public static void ensureStaticInitialization() {
    }

    static BCLFeature sizedCluster(ResourceLocation location,
                                   boolean onFloor,
                                   boolean sparse,
                                   ScatterFeatureConfig.Builder builder) {
        return FastFeatures.scatter(location, onFloor, sparse, builder, NetherFeatures.SCATTER_WITH_SIZE);
    }
}
