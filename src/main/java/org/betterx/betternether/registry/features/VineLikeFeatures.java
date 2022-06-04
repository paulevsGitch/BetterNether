package org.betterx.betternether.registry.features;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;

import org.betterx.bclib.api.features.BCLFeature;
import org.betterx.bclib.api.features.FastFeatures;
import org.betterx.bclib.api.features.config.ScatterFeatureConfig;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BlockLumabusVine;
import org.betterx.betternether.blocks.BlockNetherCactus;
import org.betterx.betternether.blocks.BlockNetherReed;
import org.betterx.betternether.blocks.BlockProperties;
import org.betterx.betternether.registry.NetherBlocks;
import org.betterx.betternether.registry.NetherFeatures;
import org.betterx.betternether.world.features.ScatterFeatureConfigs;

public class VineLikeFeatures {
    public static final BCLFeature GOLDEN_VINE
            = FastFeatures.vine(
            BetterNether.makeID("golden_vine"), false, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .block(NetherBlocks.GOLDEN_VINE.defaultBlockState()
                                                   .setValue(BlockProperties.BOTTOM, false))
                    .tipBlock(NetherBlocks.GOLDEN_VINE.defaultBlockState()
                                                      .setValue(BlockProperties.BOTTOM, true))
                    .heightRange(2, 12)
                    .spread(3, 0.75f)
                    .growWhileFree()
    );
    public static final BCLFeature LUMBUS_VINE
            = FastFeatures.vine(
            BetterNether.makeID("lumbus_vine"), false, true,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .block(NetherBlocks.LUMABUS_VINE.defaultBlockState().setValue(BlockLumabusVine.SHAPE,
                            BlockProperties.TripleShape.MIDDLE))
                    .bottomBlock(NetherBlocks.LUMABUS_VINE.defaultBlockState())
                    .tipBlock(NetherBlocks.LUMABUS_VINE.defaultBlockState().setValue(BlockLumabusVine.SHAPE,
                            BlockProperties.TripleShape.BOTTOM))
                    .spread(2, 0.75f)
                    .heightRange(2, 8)
                    .growWhileFree()
    );
    public static final BCLFeature SOUL_VEIN
            = FastFeatures.vine(
            BetterNether.makeID("soul_vein"), true, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .singleBlock(NetherBlocks.SOUL_VEIN)
                    .generateBaseBlock(NetherBlocks.VEINED_SAND.defaultBlockState(), 0.4f, 0.6f, 0.2f)
                    .spread(2, 0.75f)
    );

    public static final BCLFeature NETHER_REED
            = FastFeatures.vine(
            BetterNether.makeID("nether_reed"), true, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .block(NetherBlocks.MAT_REED.getStem().defaultBlockState().setValue(BlockNetherReed.TOP, false))
                    .tipBlock(NetherBlocks.MAT_REED.getStem().defaultBlockState())
                    .spread(4, 0f, ConstantInt.of(64))
                    .heightRange(1, 3)
    );

    public static final BCLFeature NETHER_CACTUS
            = FastFeatures.vine(
            BetterNether.makeID("nether_cactus"), true, false,
            ScatterFeatureConfig.OnSolid
                    .startOnSolid()
                    .block(NetherBlocks.NETHER_CACTUS.defaultBlockState().setValue(BlockNetherCactus.TOP, false))
                    .tipBlock(NetherBlocks.NETHER_CACTUS.defaultBlockState().setValue(BlockNetherCactus.TOP, true))
                    .heightRange(2, 5)
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

    public static void ensureStaticInitialization() {
    }

    static BCLFeature sizedCluster(ResourceLocation location,
                                   boolean onFloor,
                                   boolean sparse,
                                   ScatterFeatureConfig.Builder builder) {
        return FastFeatures.scatter(location, onFloor, sparse, builder, NetherFeatures.SCATTER_WITH_SIZE);
    }
}
